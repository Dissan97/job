package org.AgneseDissan.models.time;

import org.AgneseDissan.exceptions.InvalidDateException;
import org.jetbrains.annotations.NotNull;

public class JobDateTime extends JobDate {

    int hour;
    int minute;
    public JobDateTime(String d, String time) throws InvalidDateException {
        super(d);
        this.timeParsing(time);
    }


    private void timeParsing(@NotNull String time) throws InvalidDateException {
        long length;
        String[] t = time.split(":");
        length = t.length;

        if (length != 2) {
            String[] temp = time.split(",");
            length = temp.length;

            if (length != 2) {
                temp = time.split("::");
                length = temp.length;
            }

            t = temp;
        }
        if (length != 2){
            throw new InvalidDateException("TIME FORMAT ERROR: " + time);
        }

        int hour = Integer.parseInt(t[0]);
        int minute = Integer.parseInt(t[1]);

        if (hour > 24 || minute > 60){
            throw new InvalidDateException("TIME FORMAT ERROR: " + time);
        }

        this.hour = hour;
        this.minute = minute;

    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    @Override
    public String toString() {
        return super.toString() + "::" + getHour() + ":" + getMinute() ;
    }
}
