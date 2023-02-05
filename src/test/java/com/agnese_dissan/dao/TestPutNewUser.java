package com.agnese_dissan.dao;

import org.dissan.Macros;
import org.dissan.beans.JobApplierBean;
import org.dissan.cli.io.Output;
import org.dissan.controllers.JobApplier;
import org.dissan.daos.FileSystem;
import org.dissan.exceptions.InvalidDateException;
import org.dissan.exceptions.UserAlreadyExistException;
import org.dissan.graphicControllers.JobApplierGraphic;
import org.dissan.graphicControllers.LoginGraphic;
import org.dissan.models.job.Shift;
import org.dissan.models.users.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
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
