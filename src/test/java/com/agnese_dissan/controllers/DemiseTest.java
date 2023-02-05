package com.agnese_dissan.controllers;

import org.dissan.Macros;
import org.dissan.beans.DemiseBean;
import org.dissan.cli.io.Output;
import org.dissan.exceptions.InvalidDateException;
import org.dissan.graphicControllers.DemiseGraphicController;
import org.dissan.graphicControllers.JobApplierGraphic;
import org.dissan.models.job.Demise;
import org.dissan.models.job.Shift;
import org.dissan.models.job.ShiftApply;
import org.dissan.models.users.User;
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
        } catch (InvalidDateException e) {
            Output.println("System: added to demise");
        }


    }

}
