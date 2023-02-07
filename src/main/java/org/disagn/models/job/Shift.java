package org.disagn.models.job;

public class Shift {

    private String code;
    private String name;
    private final String jobPlace;
    private final String employer;
    private final String dateTime;
    private final String description;

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

    public String getEmployer() {
        return this.employer;
    }

    public String getPlace() {
        return this.jobPlace;
    }

    public String getDescription() {
        return description;
    }
}
