package com.disagn.controllers;


import org.disagn.Macros;
import org.disagn.controllers.Login;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.exceptions.UserAlreadyExistException;
import org.disagn.exceptions.UserLoginFailedException;
import org.disagn.factories.DAOFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
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
        try {
            login.signUp("test", "testPassword", "test", "test", dateFormat.format(date), "test", Macros.EMPLOYEE);
        } catch (UserAlreadyExistException | InvalidDateException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSignIn(){
        Login login = new Login();
        try {
            DAOFactory.setStorageMethod(true);
            login.signIn("test", "testPassword", false);
        } catch (UserLoginFailedException e) {
            assertNull(e);
        }
        LOGGER.info("LOGIN SUCCESS");
    }
}
