package org.agnese_dissan.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.interfaces.Refresh;
import org.agnese_dissan.models.users.Employer;
import org.agnese_dissan.models.time.JobDateTime;

public class ShiftBean implements Refresh {
    private String shiftCode;
    private String name;
    private String jobPlace;
    private String jobDate;
    private String description;
    private Employer employer;
    private String jobTime;

    public ShiftBean() {
        this.shiftCode = null;
        this.name = null;
        this.jobPlace = null;
        this.jobDate = null;
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

    public String getJobDateTime() {
        return this.jobDate + "::" + this.jobTime;
    }


    public void setDateTime(String date, String time) throws InvalidDateException, ParseException {
        if (date.equals("") || time.equals("")) {
            throw new InvalidDateException("Date cannot be empty");
        } else {

            //controlling that this is correct it will throw invalid datetimeException otherwise.
            new JobDateTime(date, time);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            Date input = formatter.parse(date);

            Calendar todayControl = Calendar.getInstance();
            todayControl.setTime(today);

            Calendar inputControl = Calendar.getInstance();
            inputControl.setTime(input);

            long diff = inputControl.getTimeInMillis() - todayControl.getTimeInMillis();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            if (!(diffDays >= 0 && diffDays <= 7)) {
                throw new InvalidDateException("Date is within a week from today");
            }

            this.jobDate = date;
            this.jobTime = time;
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
        this.jobDate = null;
        this.description = null;
    }
}
