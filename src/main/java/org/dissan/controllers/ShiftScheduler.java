package org.dissan.controllers;

import org.dissan.beans.ScheduleBean;
import org.dissan.factories.DAOFactory;
import org.dissan.interfaces.DAO;
import org.dissan.models.job.ShiftApply;
import org.dissan.models.job.ShiftScheduling;
import org.dissan.models.users.Employer;

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
