package model;

import java.time.LocalTime;

public class Shift {
    private int id;
    private int empId;
    private String weekDay;
    private LocalTime clockIn;
    private LocalTime clockOut;
    
    public Shift(int empId, String weekDay, LocalTime clockIn, LocalTime clockOut){
        this.empId = empId;
        this.weekDay = weekDay;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return this.id;
    }
    
    public int getEmployeeId(){
        return this.empId;
    }
    
    public String getWeekDay(){
        return this.weekDay;
    }
    
    public LocalTime getClockIn(){
        return this.clockIn;
    }
    
    public LocalTime getClockOut(){
        return this.clockOut;
    }
}
