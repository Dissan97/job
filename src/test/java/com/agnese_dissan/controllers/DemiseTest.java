package com.agnese_dissan.controllers;

import org.disagn.Macros;
import org.disagn.beans.DemiseBean;
import org.disagn.cli.io.Output;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.graphicControllers.DemiseGraphicController;
import org.disagn.graphicControllers.JobApplierGraphic;
import org.disagn.models.job.Demise;
import org.disagn.models.job.Shift;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.User;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

class DemiseTest {

    @Test
    void pullDemiseTest() throws InvalidDateException, FileNotFoundException {
        DemiseGraphicController controller = new DemiseGraphicController();
        DemiseBean bean = controller.getBean();
        User user = new User("hassan", "test", "test", "test", "1997-12-12","test",Macros.EMPLOYEE);
        controller.pullDemises(user);
        List<Demise> demiseList = bean.getDemiseList();
        assert demiseList != null;

        for (Demise demise:
             demiseList) {
            System.out.println("demise: " + demise.getApplication());
        }
    }

    @Test
    void pushDemise() throws  ParseException, IOException {
        JobApplierGraphic controller = new JobApplierGraphic();
        ShiftApply shiftApply = new ShiftApply("hassan", new Shift("salem", "jobTest", "placeTest", "2023-2-3", "hello world!"));
        try {
            controller.removeAppliance(shiftApply);
        } catch (Exception e) {
            Output.println("System: added to demise");
        }


    }

}
