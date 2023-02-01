package org.agnese_dissan.controllers;

import org.agnese_dissan.beans.JobApplierBean;
import org.agnese_dissan.daos.DaoManager;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.exceptions.ShiftAlreadyApplied;
import org.agnese_dissan.interfaces.DAO;
import org.agnese_dissan.models.job.Shift;
import org.agnese_dissan.models.job.ShiftApply;
import org.agnese_dissan.models.time.JobDate;
import org.agnese_dissan.models.users.Employee;
import org.agnese_dissan.models.users.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class JobApplier {

    private final JobApplierBean bean;
    private List<ShiftApply> applyList;

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
        applyList = dao.pullAppliances(user);
        this.checkList();
        this.bean.setAppliances(this.applyList);
    }

    private void checkList() {
        this.applyList.removeIf(ShiftApply::getState);

        ShiftApply finaLI = null;
        try{
            for (ShiftApply apply:
                 this.applyList) {
                finaLI = apply;
                JobDate.controlBadDate(apply.getApplyDate());
            }
        } catch (Exception e) {
           this.applyList.remove(finaLI);
        }

    }

    public void removeAppliance(ShiftApply apply, User user) throws InvalidDateException, ParseException {
        DAO dao = DaoManager.getDaoManager();

        try {
            JobDate.controlBadDate(apply.getShiftDate(), 1, true);
            dao.removeAppliance(apply);
        }catch (InvalidDateException e){
            //todo signal it to the assistant
            throw new InvalidDateException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
