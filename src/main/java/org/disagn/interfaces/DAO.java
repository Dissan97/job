package org.disagn.interfaces;


import org.disagn.exceptions.UserAlreadyExistException;
import org.disagn.models.job.Demise;
import org.disagn.models.job.Shift;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface DAO {
    void pushUser(User user) throws UserAlreadyExistException, SQLException;

    void saveConfig(User user);

    User loadConfig();

    List<User> getUserList();

    void pushShift(Shift shift) throws SQLException;

    List<Shift> pullShifts();

    List<ShiftApply> pullSchedules(User employer) throws FileNotFoundException;

    void pushAppliance(ShiftApply shiftApply) throws IOException, SQLException;

    List<ShiftApply> pullAppliances(User user) throws FileNotFoundException;

    void removeAppliance(ShiftApply apply) throws IOException;

    void updateAppliance(ShiftApply apply) throws IOException;

    void pushSchedule(ShiftApply apply, User user) throws IOException;

    void pushEmployeeDemise(Demise apply) throws IOException, SQLException;

    List<Demise> pullEmployeeDemise(String employee) throws FileNotFoundException;

    List<Demise> pullDemises() throws FileNotFoundException;

    void pushDemise(Demise demise) throws IOException;


    void updateDemise(Demise demise) throws IOException;

    void removeDemise(Demise demise) throws IOException;


}
