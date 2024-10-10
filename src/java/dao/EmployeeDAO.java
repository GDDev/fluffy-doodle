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
import model.Phone;
import model.WorkingDay;
import model.WorkingDay.WeekDay;
import util.ConnectionFactory;

public class EmployeeDAO {
    private static final String EMPTABLE = "employees";
    
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
    
    public void insertWorkingDays(Employee emp){
        try (Connection conn = getConnection()){
            // Insert Designated Working Days
            String sql = "INSERT INTO working_day (emp_id, week_day) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                for(WorkingDay day : emp.getWorkingDays()){
                    stmt.setInt(1, emp.getId());
                    stmt.setString(2, day.getWeekDay().getEnumName());
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
    
    public void insertPhoneNumbers(Employee emp){
        try (Connection conn = getConnection()){
            // Insert Employee's Phone Numbers
            String sql = "INSERT INTO phones (emp_id, phone_number) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                for(Phone number : emp.getPhones()){
                    stmt.setInt(1, emp.getId());
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
    
    public void insertEmails(Employee emp){
        try (Connection conn = getConnection()){
            // Insert Employee's E-mails
            String sql = "INSERT INTO emails (emp_id, email) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                for(Email email : emp.getEmails()){
                    stmt.setInt(1, emp.getId());
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
    
    public void registerEmployee(Employee emp) throws SQLException, ClassNotFoundException{
        // Insert Employee
        emp = this.insertEmployee(emp);
        if (emp.getId() != 0){
            if (emp.getWorkingDays() != null){
                // Insert Designated Working Days
                this.insertWorkingDays(emp);
            }

            // Insert Employee's Contact Info
            if (emp.getPhones() != null){
                // Insert Employee's Phone Numbers
                this.insertPhoneNumbers(emp);
            }
            if (emp.getEmails() != null){
                // Insert Employee's E-mails
                this.insertEmails(emp); 
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
    
    public boolean deleteEmployeeEmails(Employee emp){
        boolean deleted = false;
        try (Connection conn = getConnection()){
            String sql = "DELETE FROM emails WHERE emp_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, emp.getId());
                deleted = !stmt.execute();
            }
        }
        catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        finally {
            return deleted;
        }
    }
    
    public boolean deleteEmployeePhones(Employee emp){
        boolean deleted = false;
        try (Connection conn = getConnection()){
            String sql = "DELETE FROM phones WHERE emp_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, emp.getId());
                deleted = !stmt.execute();
            }
        }
        catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        finally {
            return deleted;
        }
    }
        
    public boolean deleteEmployeeWorkingDays(Employee emp){
        boolean deleted = false;
        try (Connection conn = getConnection()){
            String sql = "DELETE FROM working_day WHERE emp_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, emp.getId());
                deleted = !stmt.execute();
            }
        }
        catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        finally{
            return deleted;
        }
    }
    
    public boolean deleteEmployee(Employee emp){
        boolean deleted = false;
        try (Connection conn = getConnection()){
            String sql = "DELETE FROM "+EMPTABLE+" WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, emp.getId());
                deleted = !stmt.execute();
            }
        }
        catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        finally {
            return deleted;
        }
    }
    
    public boolean generalizedDelete(Employee emp, String table, String field){
        boolean deleted = false;
        try (Connection conn = getConnection()){
            String sql = "DELETE FROM " + table + " WHERE " + field + " = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, emp.getId());
                deleted = !stmt.execute();
            }
        }
        catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        finally {
            return deleted;
        }
    }
    
    public boolean removeEmployee(Employee emp){
        /*if (this.deleteEmployeeEmails(emp) &&
            this.deleteEmployeePhones(emp) &&
            this.deleteEmployeeWorkingDays(emp)){
            return this.deleteEmployee(emp);
        }
        return false;*/
        
        if (this.generalizedDelete(emp, "working_day", "emp_id") &&
            this.generalizedDelete(emp, "phones", "emp_id") &&
            this.generalizedDelete(emp, "emails", "emp_id")){
            return this.generalizedDelete(emp, EMPTABLE, "id");
        }
        return false;
    }
    
    public List<WorkingDay> getEmployeeWorkingDays(Employee emp){
        List<WorkingDay> workingDays = new ArrayList<>();
        WorkingDay wd = new WorkingDay();
        WeekDay day = WeekDay.MONDAY;
        try (Connection conn = getConnection()){
            String sql = "SELECT * FROM working_day WHERE emp_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, emp.getId());
                try (ResultSet rs = stmt.executeQuery()){
                    while (rs.next()){
                        int id = rs.getInt("id");
                        int empId = rs.getInt("emp_id");
                        String dayStr = rs.getString("week_day");
                        
                        for (WeekDay d : WeekDay.values()){
                            if (dayStr.equals(d.getEnumName())) day = d;
                        }
                        wd = new WorkingDay(empId, day);
                        wd.setId(id);
                        
                        workingDays.add(wd);
                    }
                }
            }
        }
        catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        
        return workingDays;
    }
    
    public List<Phone> getEmployeePhones(Employee emp){
        List<Phone> phones = new ArrayList<>();
        Phone phone = new Phone();
        try (Connection conn = getConnection()){
            String sql = "SELECT * FROM phones WHERE emp_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, emp.getId());
                try (ResultSet rs = stmt.executeQuery()){
                    while (rs.next()){
                        int id = rs.getInt("id");
                        int empId = rs.getInt("emp_id");
                        String num = rs.getString("phone_number");
                        
                        phone = new Phone(empId, num);
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
    
    public List<Email> getEmployeeEmails(Employee emp){
        List<Email> emails = new ArrayList<>();
        Email email = new Email();
        
        try (Connection conn = getConnection()){
            String sql = "SELECT * FROM emails WHERE emp_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, emp.getId());
                try (ResultSet rs = stmt.executeQuery()){
                    while (rs.next()){
                        int id = rs.getInt("id");
                        int empId = rs.getInt("emp_id");
                        String emailStr = rs.getString("email");
                        
                        email = new Email(empId, emailStr);
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

                        emp = new Employee(firstName, lastName, birthDate, jobTitle,
                        hireDate, salary, clockIn, clockout, image);
                        emp.setId(id);  
                        
                        // Get employee's working days
                        List<WorkingDay> wds = this.getEmployeeWorkingDays(emp);
                        if (!wds.isEmpty()) emp.setWorkingDays(wds);
                        
                        // Get employee's phone numbers
                        List<Phone> phones = this.getEmployeePhones(emp);
                        if (!phones.isEmpty()) emp.setPhones(phones);
                        
                        // Get employee's e-mails
                        List<Email> emails = this.getEmployeeEmails(emp);
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

                        Employee emp = new Employee(firstName, lastName, birthDate, jobTitle,
                        hireDate, salary, clockIn, clockout, image);
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
