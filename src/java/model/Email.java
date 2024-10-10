package model;

public class Email {
    private int id;
    private int empId;
    private String email;
    
    public Email(){}
    
    public Email(String email){
        this.email = email;
    }
    
    public Email(int empId, String email){
        this.empId = empId;
        this.email = email;
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
    
    public String getEmail(){
        return this.email;
    }
}
