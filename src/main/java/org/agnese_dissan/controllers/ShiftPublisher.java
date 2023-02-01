package org.agnese_dissan.controllers;

import org.agnese_dissan.exceptions.ShiftAlreadyExists;
import org.agnese_dissan.factories.DAOFactory;
import org.agnese_dissan.interfaces.DAO;
import org.agnese_dissan.models.job.Shift;

import java.util.List;

public class ShiftPublisher {

    //todo control if this shift already exist
    public void publish(String employer, String name, String jobPlace, String jobDateTime,String description) throws ShiftAlreadyExists {
        Shift shift = new Shift(employer, name, jobPlace, jobDateTime, description);
        DAO dao = DAOFactory.getDAO();
        List<Shift> shiftList = dao.getShiftList();
        this.verify(shiftList, shift);
        dao.pushShift(shift);
    }

    private void verify(List<Shift> shiftList, Shift shift) throws ShiftAlreadyExists{
        if (shiftList != null) {
            for (Shift s :
                    shiftList) {
                if (s.getCode().equals(shift.getCode())) {
                    throw new ShiftAlreadyExists();
                }
            }
        }
    }

}
