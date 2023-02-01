package org.agnese_dissan.graphicControllers;

import org.agnese_dissan.controllers.ShiftPublisher;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.exceptions.ShiftAlreadyExists;
import org.agnese_dissan.models.time.JobDate;
import org.agnese_dissan.models.time.JobDateTime;

import java.text.ParseException;

public class ShiftPublisherGraphic {

    private final ShiftPublisher shiftPublisher;

    public ShiftPublisherGraphic() {
        this.shiftPublisher = new ShiftPublisher();
    }


    public void publishShift(String username, String name, String place, String dateTime, String description) throws ShiftAlreadyExists {
        //TODO can add something that return an error?
        shiftPublisher.publish(username, name, place, dateTime, description);
    }

    public boolean isGood(String str){
        return (str != null && !str.equals(""));
    }

    public String setDateTime(String date, String time) throws InvalidDateException, ParseException {
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

