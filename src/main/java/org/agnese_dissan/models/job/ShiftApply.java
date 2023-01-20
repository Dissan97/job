package org.agnese_dissan.models.job;

import org.agnese_dissan.models.users.Employee;

public class ShiftApply {

    private Employee employee;
    private Shift shift;

    private String applicationCode;

    public String getApplicationCode() {
        return this.applicationCode;
    }
}
