package model;

import java.sql.Date;
import java.time.LocalTime;

public class EmployeeBuilder {
    //private int id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String jobTitle;
    private Date hireDate;
    private double salary;
    private LocalTime clockIn;
    private LocalTime clockOut;
    private String image;
    
    //public EmployeeBuilder withId(int id){;
    //    this.id = id;
    //    return this;
    //}
    
    public EmployeeBuilder withName(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        return this;
    }
    
    public EmployeeBuilder withBirthDate(Date birthDate){
        this.birthDate = birthDate;
        return this;
    }
    
    public EmployeeBuilder withJobTitle(String jobTitle){
        this.jobTitle = jobTitle;
        return this;
    }
    
    public EmployeeBuilder hiredAt(Date hireDate){
        this.hireDate = hireDate;
        return this;
    }
    
    public EmployeeBuilder withSalary(double salary){
        this.salary = salary;
        return this;
    }
    
    public EmployeeBuilder clockInAt(LocalTime clockIn){
        this.clockIn = clockIn;
        return this;
    }
    
    public EmployeeBuilder clockOutAt(LocalTime clockOut){
        this.clockOut = clockOut;
        return this;
    }
    
    public EmployeeBuilder photoAtPath(String image){
        this.image = image;
        return this;
    }
    
    public Employee build(){
        return new Employee(
                this.firstName,
                this.lastName,
                this.birthDate,
                this.jobTitle,
                this.hireDate,
                this.salary,
                this.clockIn,
                this.clockOut,
                this.image
        );
    }
}
