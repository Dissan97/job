package org.agnese_dissan.models;

import org.jetbrains.annotations.NotNull;

import java.util.zip.DataFormatException;

public class DateTime extends Date{

    int hour;
    int minute;
    public DateTime(String d, String time) throws DataFormatException {
        super(d);
        this.timeParsing(time);
    }


    private void timeParsing(@NotNull String time) throws DataFormatException {
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
            throw new DataFormatException("TIME FORMAT ERROR: " + time);
        }

        int hour = Integer.parseInt(t[0]);
        int minute = Integer.parseInt(t[1]);

        if (hour > 24 || minute > 60){
            throw new DataFormatException("TIME FORMAT ERROR: " + time);
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
