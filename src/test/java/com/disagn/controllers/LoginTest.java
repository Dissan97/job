package com.disagn.controllers;


import org.disagn.Macros;
import org.disagn.cli.io.Printer;
import org.disagn.controllers.Login;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.exceptions.UserAlreadyExistException;
import org.disagn.factories.DAOFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginTest.class);
    @Test
    void testSignUp(){
        Login login = new Login();
        Date date = new Date();
        DAOFactory.setStorageMethod(true);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        boolean isGone = false;
        try {
            login.signUp("test", "testPassword", "test", "test", dateFormat.format(date), "test", Macros.EMPLOYEE);
            LOGGER.info("Login gone");
            isGone = true;
        } catch (UserAlreadyExistException | InvalidDateException  | FileNotFoundException e) {
            Printer.exception(e);
        } finally{
            assertTrue(isGone);
        }
    }
}
