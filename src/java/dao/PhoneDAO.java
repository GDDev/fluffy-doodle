package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Phone;
import model.PhoneBuilder;
import util.ConnectionFactory;

public class PhoneDAO {
    private static final String PHONETABLE = "phones";
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        return ConnectionFactory.getConnectionMySQL();
    }
    
    public void insertPhoneNumbers(List<Phone> phones, int empId){
        try (Connection conn = getConnection()){
            // Insert Employee's Phone Numbers
            String sql = "INSERT INTO " + PHONETABLE + " (emp_id, phone_number) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                for(Phone number : phones){
                    stmt.setInt(1, empId);
                    stmt.setString(2, number.getNumber());
                    stmt.addBatch();
                }
                stmt.executeBatch();
                conn.commit();
            }
        }
        catch(SQLException | ClassNotFoundException e){
            System.out.println("Erro ao conectar com o banco." + e.getMessage());
        }
    }
    
    // TODO change parameters
    public List<Phone> getEmployeePhones(int empId){
        List<Phone> phones = new ArrayList<>();
        Phone phone = new Phone();
        try (Connection conn = getConnection()){
            String sql = "SELECT * FROM " + PHONETABLE + " WHERE emp_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, empId);
                try (ResultSet rs = stmt.executeQuery()){
                    while (rs.next()){
                        int id = rs.getInt("id");
                        empId = rs.getInt("emp_id");
                        String num = rs.getString("phone_number");
                        
                        phone = new PhoneBuilder()
                                .belongsTo(empId)
                                .withNumber(num)
                                .build();
                        phone.setId(id);
                        
                        phones.add(phone);
                    }
                }
            }
        }
        catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        finally {
            return phones;
        }
    }
    
    public void deleteEmployeePhones(int empId) throws SQLException{
        boolean deleted = false;
        try (Connection conn = getConnection()){
            String sql = "DELETE FROM " + PHONETABLE + " WHERE emp_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, empId);
                deleted = !stmt.execute();
            }
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        finally {
            if (!deleted){
                throw new SQLException("Failed to delete phone numbers.");
            }
        }
    }
}
