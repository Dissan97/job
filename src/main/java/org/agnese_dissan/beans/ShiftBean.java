package org.agnese_dissan.beans;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.graphicControllers.ShiftPublisherGraphic;
import org.agnese_dissan.interfaces.Refresh;
import org.agnese_dissan.models.users.Employer;
import org.agnese_dissan.models.time.JobDateTime;

public class ShiftBean implements Refresh {
    private String shiftCode;
    private String name;
    private String jobPlace;
    private String dateTime;
    private String description;
    private Employer employer;

    public ShiftBean() {
        this.shiftCode = null;
        this.name = null;
        this.jobPlace = null;
        this.dateTime = null;
        this.description = null;
        this.employer = null;
    }

    public ShiftBean(Employer employer) {
        this();
        this.employer = employer;
    }

    public String getShiftCode() {
        return this.shiftCode;
    }

    private void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public String getName() {
        return this.name;
    }

    public int setName(String name) {
        if (name.equals("")) {
            return -1;
        } else {
            this.name = name;
            return 0;
        }
    }

    public String getJobPlace() {
        return this.jobPlace;
    }

    public int setJobPlace(String jobPlace) {
        if (jobPlace.equals("")) {
            return -1;
        } else {
            this.jobPlace = jobPlace;
            return 0;
        }
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) throws InvalidDateException {
        if (dateTime.equals("")) {
            throw new InvalidDateException("Date cannot be empty");
        } else {
            String[] raw = dateTime.split(" ");
            if (raw.length != 2) {
                throw new InvalidDateException("Date or time passed incorrectly");
            } else {
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                JobDateTime currentTime = new JobDateTime(dateFormat.format(date), timeFormat.format(date));
                JobDateTime jobTime = new JobDateTime(raw[0], raw[1]);
                if (this.badTime(jobTime, currentTime)) {
                    throw new InvalidDateException("Cannot insert a date previous than current date or after a week".toUpperCase());
                } else {
                    this.dateTime = jobTime.toString();
                }
            }
        }
    }

    private boolean badTime(JobDateTime jobTime, JobDateTime currentTime) {
        int jobYear = jobTime.getYear();
        int currentYear = currentTime.getYear();
        int jobMonth = jobTime.getMonth();
        int currentMonth = currentTime.getMonth();
        int jobDay = jobTime.getDay();
        int currentDay = currentTime.getDay();
        int jobHour = jobTime.getHour();
        int currentHour = currentTime.getHour();
        int jobMin = jobTime.getMinute();
        int currentMin = currentTime.getMinute();
        if (jobYear < currentYear) {
            return true;
        } else if (jobMonth > currentMonth && jobYear == currentYear) {
            return true;
        } else if (jobDay < currentDay || jobDay - currentDay > 7 && jobMonth == currentMonth) {
            return true;
        } else if ((jobHour < currentHour || jobHour - currentHour < 4) && jobDay == currentDay) {
            return true;
        } else {
            return jobHour - currentHour == 4 && jobMin < currentMin;
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employer getEmployer() {
        return this.employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public void refresh() {
        this.name = null;
        this.employer = null;
        this.jobPlace = null;
        this.dateTime = null;
        this.description = null;
    }
}
