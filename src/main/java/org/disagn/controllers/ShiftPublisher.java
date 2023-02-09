package org.disagn.controllers;

import org.disagn.exceptions.ShiftAlreadyExists;
import org.disagn.factories.DAOFactory;
import org.disagn.interfaces.DAO;
import org.disagn.models.job.Shift;

import java.sql.SQLException;
import java.util.List;

public class ShiftPublisher {

    public void publish(String employer, String name, String jobPlace, String jobDateTime,String description) throws ShiftAlreadyExists, SQLException {
        Shift shift = new Shift(employer, name, jobPlace, jobDateTime, description);
        DAO dao = DAOFactory.getDAO();
        List<Shift> shiftList = dao.pullShifts();
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
