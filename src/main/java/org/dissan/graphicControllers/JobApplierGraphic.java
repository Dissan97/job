package org.dissan.graphicControllers;

import org.dissan.beans.JobApplierBean;
import org.dissan.controllers.JobApplier;
import org.dissan.exceptions.InvalidDateException;
import org.dissan.exceptions.ShiftAlreadyApplied;
import org.dissan.models.job.Shift;
import org.dissan.models.job.ShiftApply;
import org.dissan.models.users.User;

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

    public void removeAppliance(ShiftApply apply) throws InvalidDateException, ParseException, IOException {
        this.controller.removeAppliance(apply);
    }
}
