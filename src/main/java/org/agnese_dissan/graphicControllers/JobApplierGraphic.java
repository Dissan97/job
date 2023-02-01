package org.agnese_dissan.graphicControllers;

import org.agnese_dissan.beans.JobApplierBean;
import org.agnese_dissan.controllers.JobApplier;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.exceptions.ShiftAlreadyApplied;
import org.agnese_dissan.models.job.Shift;
import org.agnese_dissan.models.job.ShiftApply;
import org.agnese_dissan.models.users.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class JobApplierGraphic {

    private final JobApplierBean bean;
    private final JobApplier controller;

    public JobApplierGraphic() {
        this.bean = new JobApplierBean();
        this.controller = new JobApplier(this.bean);
    }

    public void pullShifts(){
        this.controller.pullShifts();
    }

    public JobApplierBean getBean() {
        return this.bean;
    }

    public void pushAppliance(Shift shift, User user) throws ShiftAlreadyApplied, IOException {
        this.controller.pushAppliance(shift, user);
    }

    public void pullAppliances(User user) throws FileNotFoundException {
        this.controller.pullAppliances(user);
    }

    public void removeAppliance(ShiftApply apply, User user) throws InvalidDateException, ParseException {
        this.controller.removeAppliance(apply, user);
    }
}
