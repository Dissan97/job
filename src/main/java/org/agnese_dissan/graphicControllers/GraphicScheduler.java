package org.agnese_dissan.graphicControllers;

import org.agnese_dissan.beans.ScheduleBean;
import org.agnese_dissan.controllers.ShiftScheduler;
import org.agnese_dissan.models.users.Employer;

public class GraphicScheduler {
    private final Employer employer;
    private final ScheduleBean bean;
    private final ShiftScheduler shiftScheduler;
    public GraphicScheduler(Employer employer) {
        this.employer = employer;
        this.bean = new ScheduleBean();
        this.shiftScheduler = new ShiftScheduler(this.bean);
    }

    public void getScheduling(){
        this.shiftScheduler.getSchedules(this.employer);
    }

    public ScheduleBean getBean() {
        return this.bean;
    }
}
