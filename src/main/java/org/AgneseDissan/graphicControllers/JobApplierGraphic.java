package org.AgneseDissan.graphicControllers;

import org.AgneseDissan.beans.JobApplierBean;
import org.AgneseDissan.controllers.JobApplier;
import org.AgneseDissan.exceptions.InvalidDateException;
import org.AgneseDissan.exceptions.ShiftAlreadyApplied;
import org.AgneseDissan.models.job.Shift;
import org.AgneseDissan.models.job.ShiftApply;
import org.AgneseDissan.models.users.User;

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
