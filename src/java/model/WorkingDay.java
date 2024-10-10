package model;

public class WorkingDay {
    private int id;
    private int empId;
    private WeekDay weekDay;
    
    public enum WeekDay {
        MONDAY("Segunda-feira"),
        TUESDAY("Terca-feira"), 
        WEDNESDAY("Quarta-feira"), 
        THURSDAY("Quinta-feira"), 
        FRIDAY("Sexta-feira"), 
        SATURDAY("Sabado"), 
        SUNDAY("Domingo");
        
        private final String ptName;
        
        WeekDay(String ptName){
            this.ptName = ptName;
        }
        
        public String getEnumName(){
            return this.ptName;
        }
    }
    public WorkingDay(){
    }
    
    public WorkingDay(WeekDay weekDay){
        this.weekDay = weekDay;
    }
    
    public WorkingDay(int empId, WeekDay weekDay){
        this.empId = empId;
        this.weekDay = weekDay;
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
        
    public WeekDay getWeekDay(){
        return this.weekDay;
    }
}
