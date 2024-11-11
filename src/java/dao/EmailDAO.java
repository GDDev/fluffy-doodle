package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Email;
import model.EmailBuilder;
import util.ConnectionFactory;

public class EmailDAO {
    private static final String EMAILTABLE = "emails";
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        return ConnectionFactory.getConnectionMySQL();
    }
    
    public void insertEmails(List<Email> emails, int empId){
        try (Connection conn = getConnection()){
            // Insert Employee's E-mails
            String sql = "INSERT INTO " + EMAILTABLE + " (emp_id, email) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                for(Email email : emails){
                    stmt.setInt(1, empId);
                    stmt.setString(2, email.getEmail());
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
    public List<Email> getEmployeeEmails(int empId){
        List<Email> emails = new ArrayList<>();
        Email email;
        
        try (Connection conn = getConnection()){
            String sql = "SELECT * FROM " + EMAILTABLE + " WHERE emp_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, empId);
                try (ResultSet rs = stmt.executeQuery()){
                    while (rs.next()){
                        int id = rs.getInt("id");
                        empId = rs.getInt("emp_id");
                        String emailStr = rs.getString("email");
                        
                        email = new EmailBuilder()
                                .belongsTo(empId)
                                .withEmail(emailStr)
                                .build();
                        email.setId(id);
                        
                        emails.add(email);
                    }
                }
            }
        }
        catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        finally {
            return emails;
        }
    }
    
    public void deleteEmployeeEmails(int empId) throws SQLException{
        boolean deleted = false;
        try (Connection conn = getConnection()){
            String sql = "DELETE FROM " + EMAILTABLE + " WHERE emp_id = ?";
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
                throw new SQLException("Failed to delete e-mails.");
            }
        }
    }
}
