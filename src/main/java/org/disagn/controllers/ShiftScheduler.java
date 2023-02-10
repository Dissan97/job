package org.disagn.controllers;

import org.disagn.beans.ShiftSchedulerBean;
import org.disagn.daos.DaoManager;
import org.disagn.exceptions.ShiftAlreadyScheduledException;
import org.disagn.interfaces.DAO;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.time.JobDate;
import org.disagn.models.users.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShiftScheduler {
    private final ShiftSchedulerBean bean;

    public ShiftScheduler() {
        this(new ShiftSchedulerBean());
        //Needed void
    }

    public ShiftScheduler(ShiftSchedulerBean bean) {
        this.bean = bean;
    }

    public void getSchedules(User user) throws FileNotFoundException {
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
        this.bean.setSchedules(updatedApplyList);

    }

    public void pushSchedule(ShiftApply apply, User user) throws IOException, ShiftAlreadyScheduledException, SQLException {

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

        dao.pushSchedule(apply, user);

    }

    public ShiftSchedulerBean getBean() {
        return this.bean;
    }
}
