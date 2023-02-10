package com.disagn.controllers;

import org.disagn.Macros;
import org.disagn.cli.io.Printer;
import org.disagn.controllers.Login;
import org.disagn.controllers.ShiftPublisher;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.exceptions.ShiftAlreadyExists;
import org.disagn.exceptions.UserAlreadyExistException;
import org.disagn.models.users.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

//Test case Junit Agnese Agueli
class ShiftPublisherTest {

    private static User employer;
    //need to test the use case publish shift
    @BeforeAll
    static void testUser(){
        Login controller = new Login();
        //if user doesn't exist it will be created
        try {
            employer = new User("testName", "password", "nameTest", "surnameTest", "2002-02-21", "cityTest", Macros.EMPLOYER);
            controller.signUp(employer.getUsername(), "password", employer.getName(), employer.getSurname(), employer.getDateOfBirth(), employer.getCityOfBirth(), employer.getUserType());
        } catch (UserAlreadyExistException | InvalidDateException | FileNotFoundException e) {
            Printer.exception(e); //ignoring already existing user
        }
    }
    @Test
    void publishTest(){
        ShiftPublisher controller = new ShiftPublisher();
        String jobName = "testJName12345";
        String jobPlace = "testPlace";
        String jobDataTime = "2023-02-16::21:30";
        boolean passed = true;

        try {
            //calling the publish method
            controller.publish(employer.getUsername(), jobName,jobPlace, jobDataTime, "");
        } catch (ShiftAlreadyExists | SQLException | IOException e) {
            //if exception is caught than test fail
            passed = false;
            Printer.exception(e);
        }finally {
            assertTrue(passed);
        }
    }

}
