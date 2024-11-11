package model;

import model.WorkingDay.WeekDay;

public class WorkingDayBuilder {
    private int empId;
    private WeekDay weekDay;
    
    public WorkingDayBuilder setDayTo(int empId){
        this.empId = empId;
        return this;
    }
    
    public WorkingDayBuilder addDay(String day){
        for (WeekDay weekDay : WeekDay.values()){
            if (weekDay.getEnumName().equals(day)){
                this.weekDay = weekDay;
            }
        }
        return this;
    }
    
    public WorkingDay build(){
        if (this.empId != 0){
            return new WorkingDay(this.empId, this.weekDay);
        }
        return new WorkingDay(this.weekDay);
    }
}
