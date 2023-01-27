package org.agnese_dissan.controllers;

import org.agnese_dissan.beans.ScheduleBean;
import org.agnese_dissan.factories.DAOFactory;
import org.agnese_dissan.interfaces.DAO;
import org.agnese_dissan.models.job.ShiftApply;
import org.agnese_dissan.models.job.ShiftScheduling;
import org.agnese_dissan.models.users.Employer;

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
