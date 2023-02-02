package org.dissan.graphicControllers;

import org.dissan.beans.ScheduleBean;
import org.dissan.controllers.ShiftScheduler;
import org.dissan.models.users.User;


public class GraphicScheduler {
    private final User user;
    private final ScheduleBean bean;
    private final ShiftScheduler shiftScheduler;

    public GraphicScheduler(User user) {
        this.user = user;
        this.bean = new ScheduleBean();
        this.shiftScheduler = new ShiftScheduler(this.bean);
    }

    public void getScheduling(){
        this.shiftScheduler.getSchedules(this.user);
    }

    public ScheduleBean getBean() {
        return this.bean;
    }
}
