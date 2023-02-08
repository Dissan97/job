package org.disagn.models.job;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShiftApply {

    private final String employee;
    private final String shift;
    private final String employer;
    private final String shiftDate;
    private final String applianceDateTime;
    private boolean accepted = false;

    public ShiftApply(String employee, Shift shift) {
            this.employee = employee;
            this.shift = shift.getCode();
            this.employer = shift.getEmployer();
            this.applianceDateTime = this.getApplyDate();
            this.shiftDate = shift.getDateTime();
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

    public String getShiftDate() {
        return shiftDate;
    }

    @Override
    public String toString(){
        return this.employer+this.shift+this.employee;
    }

    public boolean getState() {
        return this.accepted;
    }

    public void accept() {
        this.accepted = true;
    }

    public boolean isAccepted() {
        return this.accepted;
    }
}
