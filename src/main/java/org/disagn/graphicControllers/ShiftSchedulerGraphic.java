package org.disagn.graphicControllers;

import org.disagn.beans.ShiftSchedulerBean;
import org.disagn.controllers.ShiftScheduler;
import org.disagn.models.users.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


public class ShiftSchedulerGraphic {
    private final User user;
    private final ShiftSchedulerBean bean;
    private final ShiftScheduler shiftScheduler;

    public ShiftSchedulerGraphic(User user) {
        this.user = user;
        this.bean = new ShiftSchedulerBean();
        this.shiftScheduler = new ShiftScheduler(this.bean);
    }

    public void getScheduling(){
        this.shiftScheduler.getSchedules(this.user);
    }

    public ShiftSchedulerBean getBean() {
        return this.bean;
    }

    public void setUpDates() {
        List<String> dateList = new LinkedList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        for (int i = 0; i < 7; i++) {
            dateList.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }

        this.bean.setDateList(dateList);
    }

    public void pullSchedules(String date, User user) {
        this.bean.setSchedules(new ArrayList<>());
    }
}
