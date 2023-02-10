package com.disagn.graphics;

import org.disagn.Macros;
import org.disagn.beans.JobApplierBean;
import org.disagn.cli.io.Printer;
import org.disagn.controllers.Login;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.exceptions.UserAlreadyExistException;
import org.disagn.graphics.JobApplierGraphic;
import org.disagn.models.job.Shift;
import org.disagn.models.users.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShiftPullerTest {

    //Test case Junit Agnese Agueli
    private static User employee;

    @BeforeAll
    static void initUser(){
        Login controller = new Login();
        //if user doesn't exist it will be created
        try {
            employee = new User("testName", "password", "nameTest", "surnameTest", "2002-02-21", "cityTest", Macros.EMPLOYER);
            controller.signUp(employee.getUsername(), "password", employee.getName(), employee.getSurname(), employee.getDateOfBirth(), employee.getCityOfBirth(), employee.getUserType());
        } catch (UserAlreadyExistException | InvalidDateException | FileNotFoundException e) {
            Printer.exception(e); //ignoring already existing user
        }
    }

    @Test
    void shiftPullTest(){
        JobApplierGraphic controller = new JobApplierGraphic();
        JobApplierBean bean = controller.getBean();
        //calling the publish method
        try {
            controller.pullShifts(employee);
        } catch (FileNotFoundException e) {
            assertNull(e);
        }

        List<Shift> shiftList;
        shiftList = bean.getShiftList();

        assertFalse(shiftList.isEmpty());

        printShifts(shiftList);

    }

    private void printShifts(List<Shift> shiftList) {
        String[][] table = new String[shiftList.size()][5];
        String[] columns ={"Job name", "Job Place", "Job time", "Employer", "Description"};
        int i = 0;
        for (Shift s:
             shiftList) {
            table[i][0] = s.getName();
            table[i][1] = s.getPlace();
            table[i][2] = s.getDateTime();
            table[i][3] = s.getEmployer();
            table[i][4] = s.getDescription();
            i++;
        }

        Printer.printTable("Test", "Shifts", columns, table);
    }

}
