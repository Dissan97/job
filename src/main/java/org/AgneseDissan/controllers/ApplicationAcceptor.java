package org.AgneseDissan.controllers;

import org.AgneseDissan.daos.DaoManager;
import org.AgneseDissan.exceptions.ApplyNotExistException;
import org.AgneseDissan.interfaces.DAO;
import org.AgneseDissan.models.job.ShiftApply;
import org.AgneseDissan.models.users.User;

import java.io.IOException;
import java.util.List;

public class ApplicationAcceptor {
    public void manageCandidate(User employee, User employer, ShiftApply apply, boolean accept) throws IOException, ApplyNotExistException {
        DAO dao = DaoManager.getDaoManager();

        List<ShiftApply> employerShiftList = dao.pullAppliances(employer);
        List<ShiftApply> employeeShiftList = dao.pullAppliances(employee);
        if (this.badApply(employerShiftList, apply) || this.badApply(employeeShiftList, apply)){
            throw new ApplyNotExistException();
        }
        if (accept){
            apply.accept();
            dao.updateAppliance(apply);
        }else {
            dao.removeAppliance(apply);
        }



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
