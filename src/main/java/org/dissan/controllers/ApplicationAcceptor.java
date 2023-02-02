package org.dissan.controllers;

import org.dissan.daos.DaoManager;
import org.dissan.exceptions.ApplyNotExistException;
import org.dissan.exceptions.ShiftAlreadyScheduledException;
import org.dissan.interfaces.DAO;
import org.dissan.models.job.ShiftApply;
import org.dissan.models.users.User;

import java.io.IOException;
import java.util.List;

public class ApplicationAcceptor {
    public void manageCandidate(User employee, User employer, ShiftApply apply, boolean accept) throws IOException, ApplyNotExistException, ShiftAlreadyScheduledException {
        DAO dao = DaoManager.getDaoManager();

        List<ShiftApply> employerShiftList = dao.pullAppliances(employer);
        List<ShiftApply> employeeShiftList = dao.pullAppliances(employee);
        if (this.badApply(employerShiftList, apply) || this.badApply(employeeShiftList, apply)){
            throw new ApplyNotExistException();
        }
        if (accept){
            this.pushScheduling(apply, employee);
            this.pushScheduling(apply, employer);
            apply.accept();
            dao.updateAppliance(apply);
        }else {
            dao.removeAppliance(apply);
        }

    }

    public void pushScheduling(ShiftApply apply, User user) throws IOException, ShiftAlreadyScheduledException {
        ShiftScheduler schedulerController = new ShiftScheduler();
        schedulerController.pushSchedule(apply, user);
    }

    private boolean badApply(List<ShiftApply> applyList, ShiftApply apply) {

        for (ShiftApply shiftApply:
             applyList) {
            if (shiftApply.toString().equals(apply.toString())){
                return false;
            }
        }
        return true;
    }
}
