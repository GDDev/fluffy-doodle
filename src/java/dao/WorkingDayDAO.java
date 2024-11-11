package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.WorkingDay;
import model.WorkingDayBuilder;
import util.ConnectionFactory;

public class WorkingDayDAO {
    private static final String WDTABLE = "working_day";
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        return ConnectionFactory.getConnectionMySQL();
    }
    
    public void insertWorkingDays(List<WorkingDay> wdays, int empId){
        try (Connection conn = getConnection()){
            // Insert Designated Working Days
            String sql = "INSERT INTO " + WDTABLE + " (emp_id, week_day) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                for(WorkingDay day : wdays){
                    stmt.setInt(1, empId);
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
    
    // TODO change parameters
    public List<WorkingDay> getEmployeeWorkingDays(int empId){
        List<WorkingDay> workingDays = new ArrayList<>();
        WorkingDay wd = new WorkingDay();
        WorkingDay.WeekDay day = WorkingDay.WeekDay.MONDAY;
        try (Connection conn = getConnection()){
            String sql = "SELECT * FROM " + WDTABLE + " WHERE emp_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, empId);
                try (ResultSet rs = stmt.executeQuery()){
                    while (rs.next()){
                        int id = rs.getInt("id");
                        empId = rs.getInt("emp_id");
                        String dayStr = rs.getString("week_day");
                        
                        for (WorkingDay.WeekDay d : WorkingDay.WeekDay.values()){
                            if (dayStr.equals(d.getEnumName())) day = d;
                        }
                        wd = new WorkingDayBuilder()
                                .setDayTo(empId)
                                .addDay(dayStr)
                                .build();
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
    
    public void deleteEmployeeWorkingDays(int empId) throws SQLException{
        boolean deleted = false;
        try (Connection conn = getConnection()){
            String sql = "DELETE FROM " + WDTABLE + " WHERE emp_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, empId);
                deleted = !stmt.execute();
            }
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        finally{
            if (!deleted){
                throw new SQLException("Failed to delete assigned working days");
            }
        }
    }
}
