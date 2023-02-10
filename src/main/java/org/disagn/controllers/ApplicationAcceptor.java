package org.disagn.controllers;

import org.disagn.daos.DaoManager;
import org.disagn.exceptions.ApplyNotExistException;
import org.disagn.exceptions.ShiftAlreadyScheduledException;
import org.disagn.interfaces.DAO;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ApplicationAcceptor {
    public void manageCandidate(User employee, User employer, ShiftApply apply, boolean accept) throws IOException, ApplyNotExistException, ShiftAlreadyScheduledException, SQLException {
        DAO dao = DaoManager.getDaoManager();

        List<ShiftApply> employerShiftList = dao.pullAppliances(employer);
        List<ShiftApply> employeeShiftList = dao.pullAppliances(employee);
        if (this.badApply(employerShiftList, apply) || this.badApply(employeeShiftList, apply)){
            throw new ApplyNotExistException();
        }
        if (accept){
            apply.accept();
            this.pushScheduling(apply, employee);
            this.pushScheduling(apply, employer);
            dao.updateAppliance(apply);
        }else {
            dao.removeAppliance(apply);
        }

    }

    public void pushScheduling(ShiftApply apply, User user) throws IOException, ShiftAlreadyScheduledException, SQLException {
        ShiftScheduler schedulerController = new ShiftScheduler();
        schedulerController.pushSchedule(apply, user);
    }

    private boolean badApply(List<ShiftApply> applyList, ShiftApply apply) {
        boolean debugFeature = false;
        for (ShiftApply shiftApply:
             applyList) {
            if (shiftApply.toString().equals(apply.toString())){

                return debugFeature;
            }
        }

        return true;
    }
}
