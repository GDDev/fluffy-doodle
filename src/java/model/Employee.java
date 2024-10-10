package model;
import java.time.LocalTime;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String jobTitle;
    private Date hireDate;
    private double salary;
    private LocalTime clockIn;
    private LocalTime clockOut;
    private String image;
    
    private List<WorkingDay> workingDays = new ArrayList<>();
    private List<Phone> phoneNumbers = new ArrayList<>();
    private List<Email> emails = new ArrayList<>();
    private List<Shift> shifts = new ArrayList<>();
    
    public Employee(){
    }
    
    public Employee(String firstName, String lastName, Date birthDate, 
            String jobTitle, Date hireDate, double salary, LocalTime clockIn,
            LocalTime clockOut, String image){
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.jobTitle = jobTitle;
        this.hireDate = hireDate;
        this.salary = salary;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.image = image;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getFirstName(){
        return this.firstName;
    }
    
    public String getLastName(){
        return this.lastName;
    }
    
    public Date getBirthDate(){
        return this.birthDate;
    }
    
    public String getJobTitle(){
        return this.jobTitle;
    }
    
    public Date getHireDate(){
        return this.hireDate;
    }
    
    public double getSalary(){
        return this.salary;
    }
    
    public LocalTime getClockIn(){
        return this.clockIn;
    }
    
    public LocalTime getClockOut(){
        return this.clockOut;
    }
    
    public void setWorkingDays(List<WorkingDay> workingDays){
        this.workingDays = workingDays;
    }
    
    public List<WorkingDay> getWorkingDays(){
        return this.workingDays;
    }
    
    public String getImage(){
        return this.image;
    }

    public void setPhones(List<Phone> phones){
        this.phoneNumbers = phones;
    }
    
    public List<Phone> getPhones(){
        return this.phoneNumbers;
    }
    
    public void setEmails(List<Email> emails){
        this.emails = emails;
    }
    
    public List<Email> getEmails(){
        return this.emails;
    }
    
    public void setShifts(List<Shift> shifts){
        this.shifts = shifts;
    }
    
    public List<Shift> getShifts(){
        return this.shifts;
    }
}
