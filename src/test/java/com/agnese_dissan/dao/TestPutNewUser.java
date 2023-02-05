package com.agnese_dissan.dao;

import org.disagn.Macros;
import org.disagn.beans.JobApplierBean;
import org.disagn.cli.io.Output;
import org.disagn.graphicControllers.JobApplierGraphic;
import org.disagn.graphicControllers.LoginGraphic;
import org.disagn.models.job.Shift;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestPutNewUser {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestPutNewUser.class);
    /**
     * Test used to view a new user is inserted correctly
     */
    @Test
    void testFileSystem(){

        LoginGraphic controller = new LoginGraphic();
        boolean isGone = false;
        try {
            controller.signUp("hassan", "password", "name", "surname", "1965-10-16", "testCity", Macros.EMPLOYEE);
            isGone = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            assertTrue(isGone);
        }
    }

    //todo remove this test
    @Test
    void pullShiftList() throws FileNotFoundException {
        JobApplierGraphic controller = new JobApplierGraphic();
        JobApplierBean bean = controller.getBean();
        controller.pullShifts(null);

        List<Shift> shiftList = bean.getShiftList();
        for (Shift shift:
             shiftList) {
            Output.println(shift.getDateTime());
        }
        assertNotNull(shiftList);

    }
}
