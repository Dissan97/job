package com.disagn.dao;

import org.disagn.cli.io.Printer;
import org.disagn.daos.DaoManager;
import org.disagn.interfaces.DAO;
import org.disagn.models.job.Shift;
import org.disagn.models.users.User;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;


class DatabasePullerTest {

    //TEST CASE Junit Dissan Uddin Ahmed
    @Test
    void pullingTest() throws SQLException, FileNotFoundException {
        boolean useDatabase = true;
        DAO dao = DaoManager.getDaoManager();
        DaoManager.setPullDatabase(useDatabase);
        List<User> userList = dao.pullUsers();
        printUserList(userList);
        assertFalse(userList.isEmpty());
    }

    private void printUserList(List<User> userList) {

        String[] columnsName = new String[6];
        columnsName[0] = "username";
        columnsName[1] = "name";
        columnsName[2] = "surname";
        columnsName[3] = "dateOfBirth";
        columnsName[4] = "cityOfBirth";
        columnsName[5] = "userType";
        String[][] rows = new String[userList.size()][6];
        int i = 0;
        for (User u:
             userList) {
            rows[i][0] = u.getUsername();
            rows[i][1] = u.getName();
            rows[i][2] = u.getSurname();
            rows[i][3] = u.getDateOfBirth();
            rows[i][4] = u.getCityOfBirth();
            rows[i][5] = u.getUserType().name();
            i++;
        }
        Printer.printTable("TEST", "USERS", columnsName, rows);
    }

    //TEST CASE Junit Dissan Uddin Ahmed
    @Test
    void pullShifts() throws SQLException, FileNotFoundException {
        boolean useDatabase = true;
        DAO dao = DaoManager.getDaoManager();
        DaoManager.setPullDatabase(useDatabase);
        List<Shift> shiftList = dao.pullShifts();
        printShiftList(shiftList);
        assertFalse(shiftList.isEmpty());
    }

    private void printShiftList(List<Shift> shiftList) {
        String[] columnsName = new String[6];
        columnsName[0] = "shiftCode";
        columnsName[1] = "job name";
        columnsName[2] = "job place";
        columnsName[3] = "job date time";
        columnsName[4] = "job description";
        columnsName[5] = "employer";
        String[][] rows = new String[shiftList.size()][6];

        int i = 0;
        for (Shift s:
                shiftList) {
            rows[i][0] = s.getCode();
            rows[i][1] = s.getName();
            rows[i][2] = s.getPlace();
            rows[i][3] = s.getDateTime();
            rows[i][4] = s.getDescription();
            rows[i][5] = s.getEmployer();
            i++;
        }
        Printer.printTable("TEST", "USERS", columnsName, rows);
    }
}
