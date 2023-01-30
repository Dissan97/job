package org.agnese_dissan.controllers;

import org.agnese_dissan.beans.JobApplierBean;
import org.agnese_dissan.daos.DaoManager;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.exceptions.ShiftAlreadyApplied;
import org.agnese_dissan.interfaces.DAO;
import org.agnese_dissan.models.job.Shift;
import org.agnese_dissan.models.job.ShiftApply;
import org.agnese_dissan.models.users.Employee;
import org.agnese_dissan.models.users.User;

import java.io.FileNotFoundException;
import java.util.List;

public class JobApplier {

    private final JobApplierBean bean;

    public JobApplier(JobApplierBean bean) {
        this.bean = bean;
    }

    public void pullShifts(){
        DAO dao = DaoManager.getDaoManager();
        List<Shift> shiftList = dao.getShiftList();
        this.bean.setShiftList(shiftList);
    }

    public void pushAppliance(Shift shift, User user) throws InvalidDateException, ShiftAlreadyApplied {
        DAO dao = DaoManager.getDaoManager();
        ShiftApply shiftApply = new ShiftApply(new Employee(user), shift);
        dao.pushAppliance(shiftApply);
    }

    public void pullAppliances(User user) throws FileNotFoundException {
        DAO dao = DaoManager.getDaoManager();
        this.bean.setAppliances(dao.pullAppliances(user));
    }
}
