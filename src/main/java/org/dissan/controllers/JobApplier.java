package org.dissan.controllers;

import org.dissan.beans.JobApplierBean;
import org.dissan.daos.DaoManager;
import org.dissan.exceptions.InvalidDateException;
import org.dissan.exceptions.ShiftAlreadyApplied;
import org.dissan.interfaces.DAO;
import org.dissan.models.job.Demise;
import org.dissan.models.job.Shift;
import org.dissan.models.job.ShiftApply;
import org.dissan.models.time.JobDate;
import org.dissan.models.users.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class JobApplier {

    private final JobApplierBean bean;
    private List<ShiftApply> applyList;

    public JobApplier(JobApplierBean bean) {
        this.bean = bean;

    }

    /**
     * pull shift list that is available...
     */
    public void pullShifts(){
        DAO dao = DaoManager.getDaoManager();
        List<Shift> oldShiftList = dao.pullShifts();
        List<Shift> updatedList = new ArrayList<>();
        for (Shift shift: oldShiftList){
            Shift old = null;
            String dateTime = shift.getDateTime();
            try {
                JobDate.controlBadDate(dateTime);
                updatedList.add(shift);
            } catch (Exception ignored) {
                // need to ignore if the catch condition does not verify
            }
        }

        this.bean.setShiftList(updatedList);
    }

    public void pushAppliance(Shift shift, User user) throws IOException, ShiftAlreadyApplied {
        DAO dao = DaoManager.getDaoManager();
        ShiftApply shiftApply = new ShiftApply(user.getUsername(), shift);
        this.verifyAppliance(shiftApply, user);
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

    private void verifyAppliance(ShiftApply apply, User user) throws FileNotFoundException, ShiftAlreadyApplied {
        DAO dao = DaoManager.getDaoManager();
        List<ShiftApply> applyList = dao.pullAppliances(user);
        for (ShiftApply a:
        applyList) {
            if (a.toString().equals(apply.toString())){
                throw new ShiftAlreadyApplied();
            }
        }

    }

    public void removeAppliance(ShiftApply apply) throws InvalidDateException, ParseException, IOException {
        DAO dao = DaoManager.getDaoManager();

        try {
            dao.removeAppliance(apply);
            JobDate.controlBadDate(apply.getShiftDate(), 1, true);

        }catch (InvalidDateException e){
            if (this.checkDemises(apply)){
                Demise demise = new Demise(apply, "");
                dao.pushEmployeeDemise(demise);
            }
            throw new InvalidDateException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check if there is any demise already applied
     * @param apply is the application that want to demise
     * @return true if the application is not already send
     */
    private boolean checkDemises(ShiftApply apply) {
        DAO dao = DaoManager.getDaoManager();
        List<Demise> demiseList = dao.pullEmployeeDemise(apply.getEmployee());

        for (Demise demise:
             demiseList) {
            if (demise.getApplication().equals(apply.toString())){
                return false;
            }
        }
        return true;
    }
}
