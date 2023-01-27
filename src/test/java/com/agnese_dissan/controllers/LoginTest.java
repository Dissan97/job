package com.agnese_dissan.controllers;

import com.agnese_dissan.dao.TestPutNewUser;
import com.google.common.hash.Hashing;
import org.agnese_dissan.Macros;
import org.agnese_dissan.controllers.Login;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.exceptions.UserAlreadyExistException;
import org.agnese_dissan.exceptions.UserLoginFailedException;
import org.agnese_dissan.factories.DAOFactory;
import org.agnese_dissan.graphicControllers.LoginGraphic;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

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
