package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Employee;
import util.ConnectionFactory;

public class EmployeeDAO {
    private static final String EMPTABLE = "employees";
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        return ConnectionFactory.getConnectionMySQL();
    }
    
public void registerEmployee(Employee emp) throws SQLException, ClassNotFoundException{
        try (Connection conn = getConnection()){
            String sql = "INSERT INTO "+EMPTABLE+" (emp_name, image) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, emp.getName());
            stmt.setString(2, emp.getImage());
            stmt.execute();
        }
        catch(SQLException | ClassNotFoundException e){
            System.out.println("Erro ao conectar com o banco.");
        }
    }
    
    public void updateEmployee(Employee emp) throws SQLException{
        try (Connection conn = getConnection()){
            String sql = "UPDATE "+EMPTABLE+" SET emp_name = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, emp.getName());
            stmt.setInt(2, emp.getId());
            stmt.execute();
        }
        catch(SQLException | ClassNotFoundException e){
            System.out.println("Erro ao conectar com o banco.");
        }
    }
    
    public void deleteEmployee(Employee emp) throws SQLException{
        try (Connection conn = getConnection()){
            String sql = "DELETE FROM "+EMPTABLE+" WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, emp.getId());
            stmt.execute();
        }
        catch(SQLException | ClassNotFoundException e){
            System.out.println("Erro ao conectar com o banco.");
        }
    }
    
    public Employee findEmployeeById(Employee emp) throws SQLException{
        try (Connection conn = getConnection()){
            String sql = "SELECT * FROM "+EMPTABLE+" WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, emp.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("emp_name");
                String image = rs.getString("image");
                
                emp = new Employee(name, image);
                emp.setId(id);     
            } else{
                emp = null;
            }
        }
        catch(SQLException | ClassNotFoundException e){
            System.out.println("Erro ao conectar com o banco.");
        }
        finally{
            return emp;
        }
    }
    
    public List<Employee> findAllEmployees(String order) throws SQLException{
        List<Employee> empList = new ArrayList<>();
        try (Connection conn = getConnection()){
            String sql = "SELECT * FROM "+EMPTABLE+" ORDER BY "+order;
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("emp_name");
                String image = rs.getString("image");
                
                Employee emp = new Employee(name, image);
                emp.setId(id);
                
                empList.add(emp);
            }
        }
        catch(SQLException | ClassNotFoundException e){
            System.out.println("Erro ao conectar com o banco.");
        }
        finally{
            return empList;
        }
    }
}
