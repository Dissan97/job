package com.disagn.controllers;

import org.disagn.Macros;
import org.disagn.beans.DemiseBean;
import org.disagn.cli.io.Output;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.graphics.DemiseGraphicController;
import org.disagn.graphics.JobApplierGraphic;
import org.disagn.graphics.ShiftSchedulerGraphic;
import org.disagn.models.job.Demise;
import org.disagn.models.job.Shift;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.User;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DemiseTest {

    @Test
    void pullDemiseTest() throws InvalidDateException, FileNotFoundException {
        DemiseGraphicController controller = new DemiseGraphicController();
        DemiseBean bean = controller.getBean();


        User user = new User("hassan", "test", "test", "test", "1997-12-12","test",Macros.EMPLOYEE);
        controller.pullDemises(user);
        List<Demise> demiseList = bean.getDemiseList();
        assertNotNull(demiseList);

        for (Demise demise:
             demiseList) {
            System.out.println("demise: " + demise.getApplication());
        }
    }

    @Test
    void pushDemise(){
        JobApplierGraphic controller = new JobApplierGraphic();
        ShiftApply shiftApply = new ShiftApply("hassan", new Shift("salem", "jobTest", "placeTest", "2023-2-3", "hello world!"));
        boolean isGone = false;
        try {
            controller.removeAppliance(shiftApply);
            isGone = true;
        } catch (Exception e) {
            Output.println("System: added to demise");
        }finally {
            assertTrue(isGone);
        }
    }

    @Test
    void testFoo() throws FileNotFoundException {
        ShiftSchedulerGraphic controller = new ShiftSchedulerGraphic(null);
        controller.getScheduling();
        assertNotNull(controller);
    }

}
