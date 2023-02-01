package com.agnese_dissan.graphicControllers;

import org.AgneseDissan.beans.ShiftBean;
import org.AgneseDissan.graphicControllers.ShiftPublisherGraphic;
import org.AgneseDissan.models.time.JobDate;
import org.AgneseDissan.models.users.Employer;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShiftApplianceTest {

    @Test
    public void shiftAppliance(){
        ShiftBean bean = new ShiftBean();
        ShiftPublisherGraphic publisherGraphic = new ShiftPublisherGraphic();
        Date date = new Date();
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy/MM/dd"), timeFormat = new SimpleDateFormat("HH:mm");
        boolean passed = true;

        bean.setName("Try");
        bean.setDescription("Description");

        try {
            String today = dayFormat.format(date);
            JobDate nowDate = new JobDate(today);
            String now = timeFormat.format(date);
            int day = 6;
            int month = nowDate.getMonth();
            int year = nowDate.getYear();

            if (nowDate.getDay() > 25){
                day = 1;
                if (month != 12){
                    month += 1;
                }else {
                    month = 1;
                    year += 1;
                }
            }

            nowDate.setDay(day);
            nowDate.setMonth(month);
            nowDate.setYear(year);

            today = nowDate.toString();

            bean.setEmployer(new Employer("TrialUser", "password", "Name", "Surname", today, "City"));
            publisherGraphic.setDateTime(today + " " + now, "");
        } catch (Exception e) {
            e.printStackTrace();
            passed = false;
        }

        //publisherGraphic.publishShift(GuiStarter.getUser().getUsername(), name, place, dateTime.toString(), description);

        assertTrue(passed);
    }
}
