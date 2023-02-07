package org.disagn.controllers;

import org.disagn.beans.DemiseBean;
import org.disagn.beans.JobApplierBean;
import org.disagn.cli.io.Output;
import org.disagn.daos.DaoManager;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.exceptions.ShiftAlreadyApplied;
import org.disagn.graphicControllers.DemiseGraphicController;
import org.disagn.interfaces.DAO;
import org.disagn.models.job.Demise;
import org.disagn.models.job.Shift;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.time.JobDate;
import org.disagn.models.users.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
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
    public void pullShifts(User user) throws FileNotFoundException {
        DAO dao = DaoManager.getDaoManager();
        List<Shift> oldShiftList = dao.pullShifts();
        List<Shift> updatedList = new ArrayList<>();
        for (Shift shift: oldShiftList){
            String dateTime = shift.getDateTime();
            try {
                JobDate.controlBadDate(dateTime);
                updatedList.add(shift);
            } catch (Exception ignored) {
                // need to ignore if the catch condition does not verify
            }
        }

        List<ShiftApply> applyList = dao.pullAppliances(user);

        for (ShiftApply apply:
             applyList) {
            updatedList.removeIf(s -> s.getCode().equals(apply.getShift()));
        }

        this.bean.setShiftList(updatedList);
    }


    public void pushAppliance(Shift shift, User user) throws IOException, ShiftAlreadyApplied, SQLException {
        DAO dao = DaoManager.getDaoManager();
        ShiftApply shiftApply = new ShiftApply(user.getUsername(), shift);
        this.verifyAppliance(shiftApply, user);
        dao.pushAppliance(shiftApply);
    }

    public void pullAppliances(User user) throws FileNotFoundException {
        DAO dao = DaoManager.getDaoManager();
        applyList = dao.pullAppliances(user);
        this.checkList(user);
        this.bean.setAppliances(this.applyList);
    }

    private void checkList(User user) throws FileNotFoundException {
        this.applyList.removeIf(ShiftApply::getState);
        DemiseGraphicController demiseController = new DemiseGraphicController();
        DemiseBean demiseBean = demiseController.getBean();

        demiseController.pullDemises(user);
        List<ShiftApply> applyList = new ArrayList<>();
        List<Demise> demiseList = demiseBean.getDemiseList();

        try{
            for (ShiftApply apply:
                 this.applyList) {
                JobDate.controlBadDate(apply.getApplyDate());
                applyList.add(apply);
            }
        } catch (Exception e) {
            Output.println(e.getMessage());
        }

        for (Demise d:
             demiseList) {
            applyList.removeIf(a -> a.toString().equals(d.getApplication()));
        }

        this.applyList = applyList;

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

    public void removeAppliance(ShiftApply apply) throws InvalidDateException, ParseException, IOException, SQLException {
        DAO dao = DaoManager.getDaoManager();

        try {

            JobDate.controlBadDate(apply.getShiftDate(), 1, true);
            dao.removeAppliance(apply);

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
