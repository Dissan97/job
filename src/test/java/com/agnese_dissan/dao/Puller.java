package com.agnese_dissan.dao;

import org.dissan.Macros;
import org.dissan.beans.ShiftSchedulerBean;
import org.dissan.cli.io.Output;
import org.dissan.controllers.ApplicationAcceptor;
import org.dissan.controllers.ShiftScheduler;
import org.dissan.exceptions.InvalidDateException;
import org.dissan.exceptions.ShiftAlreadyScheduledException;
import org.dissan.models.job.Shift;
import org.dissan.models.job.ShiftApply;
import org.dissan.models.users.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Puller {

    @Test
    void pullScheduling() throws InvalidDateException, IOException, ShiftAlreadyScheduledException {
        User user = new User("dissan", "password", "dissan", "dfgg", "1999-2-3", "sfa", Macros.EMPLOYER);

        ApplicationAcceptor applicationAcceptor = new ApplicationAcceptor();
        Shift shift = new Shift(user.getUsername(), "ciao", "test", "2023-2-3::11:30", "hello my dear");
        ShiftApply shiftApply = new ShiftApply("zio", shift);
        shiftApply.accept();
        applicationAcceptor.pushScheduling(shiftApply, user);
        ShiftScheduler scheduler = new ShiftScheduler();
        ShiftSchedulerBean bean = scheduler.getBean();
        scheduler.getSchedules(user);
        List<ShiftApply> shiftApplies = bean.getSchedules();
        if (shiftApplies != null){
            for (ShiftApply apply:
                 shiftApplies) {
                Output.println(apply.getEmployee());
                Output.println(apply.getEmployer());
                Output.println(apply.getApplyDate());
            }
        }

        assertNotNull(shiftApplies);
        assertNotEquals(0, shiftApplies.size());
    }
}
