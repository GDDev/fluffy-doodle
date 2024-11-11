package model;

public class PhoneBuilder {
    private int empId;
    private String number;
    
    public PhoneBuilder belongsTo(int empId){
        this.empId = empId;
        return this;
    }
    
    public PhoneBuilder withNumber(String number){
        this.number = number;
        return this;
    }
    
    public Phone build(){
        if (this.empId != 0){
            return new Phone(this.empId, this.number);
        }
        return new Phone(this.number);
    }
}
