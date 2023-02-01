package org.AgneseDissan.controllers;

import org.AgneseDissan.beans.ScheduleBean;
import org.AgneseDissan.factories.DAOFactory;
import org.AgneseDissan.interfaces.DAO;
import org.AgneseDissan.models.job.ShiftApply;
import org.AgneseDissan.models.job.ShiftScheduling;
import org.AgneseDissan.models.users.Employer;

import java.util.List;

public class ShiftScheduler {
    private final ScheduleBean bean;
    private final DAO dao;

    public ShiftScheduler(ScheduleBean bean) {
        this.bean = bean;
        this.dao = DAOFactory.getDAO();
    }

    public void getSchedules(Employer employer) {
        List<ShiftApply> applyList = dao.getSchedules(employer);
        ShiftScheduling schedule = new ShiftScheduling();
        schedule.addSchedule(applyList);
        //TODO CONTROL IF IS NOW DAY SCHEDULING

        this.bean.addScheduling(schedule);

    }
}
