package org.agnese_dissan.models.job;

import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.models.time.JobDateTime;


public class Shift {

    private String code = null;
    private String name = null;
    private String jobPlace = null;
    private String employer = null;
    private String dateTime = null;
    private String description = null;

    public Shift(String employer, String jobName, String jobPlace, String dateTime, String description) {
        this.name = jobName;
        this.employer = employer;
        this.jobPlace = jobPlace;
        this.dateTime = dateTime;
        this.description = description;
        this.setCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode() {
        this.code = this.employer + this.getName() + getDateTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String jobDate, String jobTime) throws InvalidDateException {

        JobDateTime dateTime = new JobDateTime(jobDate, jobTime);
        this.dateTime = dateTime.toString();
    }

    public String getEmployer() {
        return this.employer;
    }

    public String getPlace() {
        return this.jobPlace;
    }
}
