package com.disagn.controllers;

import org.disagn.Macros;
import org.disagn.graphicControllers.LoginGraphic;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static org.junit.jupiter.api.Assertions.*;

class TestSignUp {

    //Test case Dissan Uddin Ahmed
    private final static Logger LOGGER = LoggerFactory.getLogger(TestSignUp.class);
    /**
     * Test used to view a new user is inserted correctly
     */
    @Test
    void testSignUp(){
        // Calling the controller to make this
        LoginGraphic controller = new LoginGraphic();
        boolean isGone = false;
        try {
            controller.signUp("TestSuper", "password", "name", "surname", "1965-10-16", "testCity", Macros.EMPLOYEE);
            isGone = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }finally {
            assertTrue(isGone);
        }
    }

}
