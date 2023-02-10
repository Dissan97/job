package com.disagn.dao;

import org.disagn.Macros;
import org.disagn.beans.ShiftSchedulerBean;
import org.disagn.cli.io.Printer;
import org.disagn.controllers.ApplicationAcceptor;
import org.disagn.controllers.ShiftScheduler;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.exceptions.ShiftAlreadyScheduledException;
import org.disagn.models.job.Shift;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestPuller {

    //Dissan Uddin Ahmed
    @Test
    void pullScheduling() throws InvalidDateException, IOException, ShiftAlreadyScheduledException, SQLException {
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
                Printer.print(apply.getEmployee());
                Printer.print(apply.getEmployer());
                Printer.print(apply.getApplyDate());
            }
        }

        assertNotNull(shiftApplies);
        assertNotEquals(0, shiftApplies.size());
    }
}
