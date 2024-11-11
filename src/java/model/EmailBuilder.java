package model;

public class EmailBuilder {
    private int empId;
    private String email;
    
    public EmailBuilder belongsTo(int empId){
        this.empId = empId;
        return this;
    }
    
    public EmailBuilder withEmail(String email){
        this.email = email;
        return this;
    }
    
    public Email build(){
        if (this.empId != 0){
            return new Email(this.empId, this.email);
        }
        return new Email(this.email);
    }
}
