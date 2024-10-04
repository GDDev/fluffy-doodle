package model;
import java.io.InputStream;

public class Employee {
    private int id;
    private String name;
    private String image;
    
    public Employee(){
    }
    
    public Employee(String name, String image){
        this.name = name;
        this.image = image;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getImage(){
        return this.image;
    }
    }
