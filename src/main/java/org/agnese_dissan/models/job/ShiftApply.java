package org.agnese_dissan.models.job;

import org.agnese_dissan.models.users.Employee;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShiftApply {

    private final String employee;
    private final String shift;
    private final String employer;
    private final String applianceDateTime;
    private boolean accepted = false;

    public ShiftApply(Employee employee, Shift shift) {
            this.employee = employee.getUsername();
            this.shift = shift.getCode();
            this.employer = shift.getEmployer();
            this.applianceDateTime = this.getApplyDate();
    }

    public String getApplyDate(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }


    public String getEmployee() {
        return this.employee;
    }

    public String getShift() {
        return this.shift;
    }

    public String getEmployer(){
        return this.employer;
    }

    public String getApplianceDateTime() {
        return applianceDateTime;
    }

    private void isAccepted(boolean accepted){
        this.accepted = accepted;
    }

    @Override
    public String toString(){
        return this.employer+this.shift+this.employee;
    }
}
