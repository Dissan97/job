package org.dissan.models.time;

import org.dissan.exceptions.InvalidDateException;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JobDate {

    private final String [] MONTHS = {"January","February", "March", "April", "May" ,"June", "July", "August", "September", "October", "November", "December"};
    private int day;
    private int month;
    private int year;

    public JobDate(String d) throws InvalidDateException {
        long length;
        String[] date = d.split("-");
        length = date.length;

        if (length != 3){
            String[] temp = d.split("/");
            length = temp.length;

            if (length != 3){
                temp = d.split("\\.");
                length = temp.length;
            }

            if (length != 3){
                temp = d.split(",");
                length = temp.length;
            }

            if (length != 3){
                throw new InvalidDateException("Error date cannot pass this format: " + d);
            }

            date = temp;
        }

        dateParsing(date);

    }

    private void dateParsing(String @NotNull [] date) throws InvalidDateException {

            if (date[0].length() == 4){

                if (Integer.parseInt(date[0]) < 1900){
                    throw new InvalidDateException("Cannot pass such a date");
                }

                int first = Integer.parseInt(date[1]);
                int second = Integer.parseInt(date[2]);

                if (first > 12 || second > 31 || first < 1 || second < 1){
                    throw new InvalidDateException("Cannot pass this month: " + first + " and this day: " + second);
                }

                if (first == 2){
                    char [] year = date[0].toCharArray();
                    int num = Integer.parseInt(date[0]);

                    if (second > 29){
                        throw new InvalidDateException("February cannot ha more than 29 days");
                    }

                    if (num % 4 != 0){
                        if (second > 28){
                            throw new InvalidDateException("This year is not leap");
                        }
                    }

                    num = Integer.parseInt(String.valueOf(year[2]));
                    int unit = Integer.parseInt(String.valueOf(year[3]));
                    if (num % 2 == 1){
                        num = unit;
                        if (num != 6 &&  num != 2 && second > 28){
                            throw new InvalidDateException("This year is not leap");
                        }
                    }else {
                        num = unit;
                        if (num % 4 != 0 && second > 28){
                            throw new InvalidDateException("BAD FORMAT");
                        }
                    }


                }



                switch (first){

                    case 4, 6, 9, 11 ->{
                        if (second > 30){
                            throw new InvalidDateException(MONTHS[first - 1] + " cannot have more than 30 days");
                        }
                    }

                }

            }   else {
                throw new InvalidDateException("Pass date as yyyy/mm/dd");
            }

            this.year = Integer.parseInt(date[0]);
            this.month = Integer.parseInt(date[1]);
            this.day = Integer.parseInt(date[2]);

    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }


    public String toItalianFormat(){
        return "Date{Italian format}: " + this.getDay() + " " + this.MONTHS[this.getMonth() - 1] + " " + this.getYear();
    }

    public String toBritishFormat(){
        String date = this.MONTHS[this.getMonth() - 1] + " " + this.getDay();
        switch (this.getDay()){
            case 1 ->{
                date += "st";
            }
            case 2 ->{
                date += "nd";
            }
            case 3 ->{
                date += "rd";
            }
            default -> {
                date += "th";
            }
        }

        date += " of " + this.getYear();
        return "Date{British format}: " +  date;
    }

    @Override
    public String toString() {
        return this.getYear() + "-" + this.getMonth() + "-" + this.getDay();
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public static void controlBadDate(String date) throws InvalidDateException, ParseException {
        controlBadDate(date, 7, false);
    }

    public static void controlBadDate(String date, int days, boolean major) throws InvalidDateException, ParseException {


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
                System.out.println("less than a day: " + date );
                throw new InvalidDateException();
            }
        }else {
            if (!(diffDays >= 0 && diffDays <= days)) {
                throw new InvalidDateException("Date is within a week from today");
            }
        }
    }
}
