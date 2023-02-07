package org.disagn.graphicControllers;

import org.disagn.beans.JobApplierBean;
import org.disagn.controllers.JobApplier;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.exceptions.ShiftAlreadyApplied;
import org.disagn.models.job.Shift;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class JobApplierGraphic {

    private final JobApplierBean bean;
    private final JobApplier controller;

    public JobApplierGraphic() {
        this.bean = new JobApplierBean();
        this.controller = new JobApplier(this.bean);
    }

    public void pullShifts(User user) throws FileNotFoundException {
        this.controller.pullShifts(user);
    }

    public JobApplierBean getBean() {
        return this.bean;
    }

    public void pushAppliance(Shift shift, User user) throws ShiftAlreadyApplied, IOException, SQLException {
        this.controller.pushAppliance(shift, user);
    }

    public void pullAppliances(User user) throws FileNotFoundException {
        this.controller.pullAppliances(user);
    }

    public void removeAppliance(ShiftApply apply) throws InvalidDateException, ParseException, IOException, SQLException {
        this.controller.removeAppliance(apply);
    }
}
