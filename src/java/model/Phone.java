package model;

public class Phone {
    private int id;
    private int empId;
    private String number;
    
    public Phone(){}
    
    public Phone(String number){
        this.number = number;
    }
    
    public Phone(int empId, String number){
        this.empId = empId;
        this.number = number;
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
    
    public String getNumber(){
        return this.number;
    }
}
