package org.agnese_dissan.models.job;

import org.agnese_dissan.models.users.Employee;

import java.text.SimpleDateFormat;

public class ShiftApply {

    private final Employee employee;
    private final Shift shift;


    public ShiftApply(Employee employee, Shift shift) {
        this.employee = employee;
        this.shift = shift;
    }

    public String getApplyDate(){
        String[] splitter = this.shift.getDateTime().split("::");
        return splitter[0];
    }

    public String getApplicationCode() {
        return employee.getUsername() +"1"+ shift.getCode();
    }

    @Override
    public String toString() {
        return "EMPLOYEE: " + this.employee.getUsername() + "\nSHIFT DATE: " + this.shift.getDateTime();
    }
}
