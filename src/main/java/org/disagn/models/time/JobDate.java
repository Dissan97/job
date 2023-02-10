package org.disagn.models.time;

import org.disagn.exceptions.InvalidDateException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JobDate {

    private String globalDate;

    public JobDate(String d) throws InvalidDateException {
        long length;
        String[] date = d.split("-");
        length = date.length;
        String parser = "-";
        if (length != 3){
            String[] temp = d.split("/");
            length = temp.length;
            parser = "/";

            if (length != 3){
                temp = d.split("\\.");
                length = temp.length;
                parser = "\\.";
            }

            if (length != 3){
                temp = d.split(",");
                length = temp.length;
                parser = ",";
            }

            if (length != 3){
                throw new InvalidDateException("bad date", 0);
            }
        }

        parseDate(d, parser);

    }

    private void parseDate(String date, String parser) throws InvalidDateException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy" + parser +"MM"+parser+"dd");
        try {
            Date d = dateFormat.parse(date);
            this.globalDate = dateFormat.format(d);
        } catch (ParseException e) {
            throw new InvalidDateException();
        }
    }






    @Override
    public String toString() {
        return this.globalDate;
    }


    public static void controlBadDate(String date) throws ParseException {
        controlBadDate(date, 7, false);
    }

    public static void controlBadDate(String date, int days, boolean major) throws  ParseException {


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Date input = formatter.parse(date);

        Calendar todayControl = Calendar.getInstance();
        todayControl.setTime(today);

        Calendar inputControl = Calendar.getInstance();
        inputControl.setTime(input);

        long diff = inputControl.getTimeInMillis() - todayControl.getTimeInMillis();
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (major){
            if ((diffDays >= 0 && diffDays <= days)) {
                throw new InvalidDateException();
            }
        }else {
            if (!(diffDays >= 0 && diffDays <= days)) {
                throw new InvalidDateException();
            }
        }
    }
}
