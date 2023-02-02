package org.dissan.controllers;

import org.dissan.beans.ScheduleBean;
import org.dissan.daos.DaoManager;
import org.dissan.exceptions.ShiftAlreadyScheduledException;
import org.dissan.interfaces.DAO;
import org.dissan.models.job.ShiftApply;
import org.dissan.models.time.JobDate;
import org.dissan.models.users.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShiftScheduler {
    private final ScheduleBean bean;

    public ShiftScheduler() {
        this(new ScheduleBean());
        //Needed void
    }

    public ShiftScheduler(ScheduleBean bean) {
        this.bean = bean;
    }

    public void getSchedules(User user) {
        DAO dao = DaoManager.getDaoManager();
        List<ShiftApply> oldApplyList = dao.pullSchedules(user);
        List<ShiftApply> updatedApplyList = new ArrayList<>();
        for (ShiftApply apply:
             oldApplyList) {
            try {
                JobDate.controlBadDate(apply.getShiftDate());
                updatedApplyList.add(apply);
            } catch (Exception ignored) {
                //Do no op
            }
        }
        //TODO CONTROL IF IS NOW DAY SCHEDULING
        this.bean.addScheduling(updatedApplyList);

    }

    public void pushSchedule(ShiftApply apply, User user) throws IOException, ShiftAlreadyScheduledException {

        if (!apply.getState()){
            throw new ShiftAlreadyScheduledException("SHIFT NOT ACCEPTED");
        }

        DAO dao = DaoManager.getDaoManager();
        List<ShiftApply> applyList = dao.pullSchedules(user);

        for (ShiftApply shiftApply:
             applyList) {
            if (shiftApply.toString().equals(apply.toString())){
                throw new ShiftAlreadyScheduledException();
            }
        }

        //todo verify if the shift already exist
        dao.pushSchedule(apply, user);

    }

    public ScheduleBean getBean() {
        return this.bean;
    }
}
