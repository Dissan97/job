package org.agnese_dissan.controllers;

import org.agnese_dissan.factories.DAOFactory;
import org.agnese_dissan.interfaces.DAO;
import org.agnese_dissan.models.Shift;

public class ShiftPublisher {

    private boolean db = false;

    public ShiftPublisher(){

    }

    public ShiftPublisher(boolean db){
        this.db = db;
    }

    public void publish(String employer, String name, String jobPlace, String jobDateTime,String description) {
        Shift shift = new Shift(employer, name, jobPlace, jobDateTime, description);
        DAO dao = DAOFactory.getDAO(this.db);
        dao.publishShift(shift);

    }
}
