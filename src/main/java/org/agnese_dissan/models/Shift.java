package org.agnese_dissan.models;

import java.util.Date;

public class Shift {

    private String code;
    private String name;
    private JobPlace jobPlace;
    private Employer employer;

    private Date date;

    public Shift(Employer employer) {
        this.employer = employer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobPlace getPlace() {
        return jobPlace;
    }

    public void setPlace(JobPlace jobPlace) {
        this.jobPlace = jobPlace;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }
}
