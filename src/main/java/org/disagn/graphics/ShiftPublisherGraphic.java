package org.disagn.graphics;

import org.disagn.controllers.ShiftPublisher;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.exceptions.ShiftAlreadyExists;
import org.disagn.models.time.JobDate;
import org.disagn.models.time.JobDateTime;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class ShiftPublisherGraphic {

    private final ShiftPublisher shiftPublisher;

    public ShiftPublisherGraphic() {
        this.shiftPublisher = new ShiftPublisher();
    }


    public void publishShift(String username, String name, String place, String dateTime, String description) throws ShiftAlreadyExists, SQLException, IOException {
        shiftPublisher.publish(username, name, place, dateTime, description); //can add something that return an error?
    }

    public boolean isGood(String str){
        return (str != null && !str.equals(""));
    }

    public String setDateTime(String date, String time) throws ParseException {
        JobDateTime dateTime;
        if (date.equals("") || time.equals("")) {
            throw new InvalidDateException("Date cannot be empty");
        } else {
             dateTime = new  JobDateTime(date, time);

            //controlling that this is correct it will throw invalid datetimeException otherwise.
            JobDate.controlBadDate(date);

        }
        return dateTime.toString();
    }


}

