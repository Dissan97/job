package com.agnese_dissan.dao;

import org.disagn.Macros;
import org.disagn.beans.ShiftSchedulerBean;
import org.disagn.cli.io.Output;
import org.disagn.controllers.ApplicationAcceptor;
import org.disagn.controllers.ShiftScheduler;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.exceptions.ShiftAlreadyScheduledException;
import org.disagn.models.job.Shift;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.User;
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
