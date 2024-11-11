package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import model.Email;
import model.Employee;
import model.EmployeeBuilder;
import model.Phone;
import model.WorkingDay;
import util.ConnectionFactory;

public class EmployeeDAO {
    private static final String EMPTABLE = "employees";
    private WorkingDayDAO wdayDAO = new WorkingDayDAO();
    private EmailDAO emailDAO = new EmailDAO();
    private PhoneDAO phoneDAO = new PhoneDAO();
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        return ConnectionFactory.getConnectionMySQL();
    }
    
    public Employee insertEmployee(Employee emp){
        try (Connection conn = getConnection()){
            // Insert Employee
            String sql = "INSERT INTO "+EMPTABLE+" (first_name, last_name, birth_date, "
                    + "job_title, hire_date, salary, clock_in_time, clock_out_time,"
                    + "image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try{
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, emp.getFirstName());
                stmt.setString(2, emp.getLastName());
                stmt.setDate(3, emp.getBirthDate());
                stmt.setString(4, emp.getJobTitle());
                stmt.setDate(5, emp.getHireDate());
                stmt.setDouble(6, emp.getSalary());
                stmt.setTime(7, Time.valueOf(emp.getClockIn()));
                stmt.setTime(8, Time.valueOf(emp.getClockOut()));
                stmt.setString(9, emp.getImage());
                stmt.execute();

                // Retrieve Employee's id
                sql = "SELECT LAST_INSERT_ID()";
                stmt = conn.prepareStatement(sql);
                try (ResultSet rs = stmt.executeQuery()){
                    if (rs.next()){
                        emp.setId(rs.getInt(1));
                    }
                } 
                stmt.close();
            }
            catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        catch(SQLException | ClassNotFoundException e){
            System.out.println("Erro ao conectar com o banco." + e.getMessage());
        }
        return emp;
    }
    
    public void registerEmployee(Employee emp) throws SQLException, ClassNotFoundException{
        // Insert Employee
        emp = this.insertEmployee(emp);
        if (emp.getId() != 0){
            if (emp.getWorkingDays() != null){
                // Insert Designated Working Days
                wdayDAO.insertWorkingDays(emp.getWorkingDays(), emp.getId());
            }

            // Insert Employee's Contact Info
            if (emp.getPhones() != null){
                // Insert Employee's Phone Numbers
                phoneDAO.insertPhoneNumbers(emp.getPhones(), emp.getId());
            }
            if (emp.getEmails() != null){
                // Insert Employee's E-mails
                emailDAO.insertEmails(emp.getEmails(), emp.getId()); 
            }
        }
    }
    
    public void updateEmployee(Employee emp) throws SQLException{
        try (Connection conn = getConnection()){
            String sql = "UPDATE "+EMPTABLE+" "
                    + "SET first_name = ?, last_name = ?, birth_date = ?, job_title = ?, "
                    + "hire_date = ?, salary = ?, clock_in_time = ?, clock_out_time = ?"
                    + "WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, emp.getFirstName());
                stmt.setString(2, emp.getLastName());
                stmt.setDate(3, emp.getBirthDate());
                stmt.setString(4, emp.getJobTitle());
                stmt.setDate(5, emp.getHireDate());
                stmt.setDouble(6, emp.getSalary());
                stmt.setTime(7, Time.valueOf(emp.getClockIn()));
                stmt.setTime(8, Time.valueOf(emp.getClockOut()));
                stmt.setInt(9, emp.getId());
                stmt.execute();
            }
        }
        catch(SQLException | ClassNotFoundException e){
            System.out.println("Erro ao conectar com o banco.");
        }
    }
    
    public void deleteEmployee(Employee emp) throws SQLException{
        boolean deleted = false;
        try (Connection conn = getConnection()){
            String sql = "DELETE FROM "+EMPTABLE+" WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, emp.getId());
                deleted = !stmt.execute();
            }
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        finally {
            if (!deleted){
                throw new SQLException("Failed to delete employee.");
            }
        }
    }
    
    public void removeEmployee(Employee emp){
        int empId = emp.getId();
        try{
            wdayDAO.deleteEmployeeWorkingDays(empId);
            phoneDAO.deleteEmployeePhones(empId);
            emailDAO.deleteEmployeeEmails(empId);
            this.deleteEmployee(emp);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public Employee findEmployeeById(Employee emp) throws SQLException{
        try (Connection conn = getConnection()){
            String sql = "SELECT * FROM "+EMPTABLE+" WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, emp.getId());
                try (ResultSet rs = stmt.executeQuery()){
                    if (rs.next()){
                        int id = rs.getInt("id");
                        String firstName = rs.getString("first_name");
                        String lastName = rs.getString("last_name");
                        
                        Date birthDate = rs.getDate("birth_date");
                        String jobTitle = rs.getString("job_title");
                        Date hireDate = rs.getDate("hire_date");
                        double salary = rs.getDouble("salary");
                        LocalTime clockIn = rs.getTime("clock_in_time").toLocalTime();
                        LocalTime clockout = rs.getTime("clock_out_time").toLocalTime();
                        String image = rs.getString("image");

                        emp = new EmployeeBuilder()
                                .withName(firstName, lastName)
                                .withBirthDate(birthDate)
                                .withJobTitle(jobTitle)
                                .hiredAt(hireDate)
                                .withSalary(salary)
                                .clockInAt(clockIn)
                                .clockOutAt(clockout)
                                .photoAtPath(image)
                                .build();
                        emp.setId(id);  
                        
                        // Get employee's working days
                        List<WorkingDay> wds = wdayDAO.getEmployeeWorkingDays(emp.getId());
                        if (!wds.isEmpty()) emp.setWorkingDays(wds);
                        
                        // Get employee's phone numbers
                        List<Phone> phones = phoneDAO.getEmployeePhones(emp.getId());
                        if (!phones.isEmpty()) emp.setPhones(phones);
                        
                        // Get employee's e-mails
                        List<Email> emails = emailDAO.getEmployeeEmails(emp.getId());
                        if (!emails.isEmpty()) emp.setEmails(emails);
                    } else{
                        emp = null;
                    }
                }
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
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                try(ResultSet rs = stmt.executeQuery()){
                    while(rs.next()){
                        int id = rs.getInt("id");
                        String firstName = rs.getString("first_name");
                        String lastName = rs.getString("last_name");
                        
                        Date birthDate = rs.getDate("birth_date");
                        String jobTitle = rs.getString("job_title");
                        Date hireDate = rs.getDate("hire_date");
                        double salary = rs.getDouble("salary");
                        LocalTime clockIn = rs.getTime("clock_in_time").toLocalTime();
                        LocalTime clockout = rs.getTime("clock_out_time").toLocalTime();
                        String image = rs.getString("image");

                        Employee emp = new EmployeeBuilder()
                                .withName(firstName, lastName)
                                .withBirthDate(birthDate)
                                .withJobTitle(jobTitle)
                                .hiredAt(hireDate)
                                .withSalary(salary)
                                .clockInAt(clockIn)
                                .clockOutAt(clockout)
                                .photoAtPath(image)
                                .build();
                        emp.setId(id);

                        empList.add(emp);
                    }
                }
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
