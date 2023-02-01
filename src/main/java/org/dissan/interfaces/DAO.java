package org.dissan.interfaces;


import org.dissan.exceptions.UserAlreadyExistException;
import org.dissan.models.job.DemiseMessages;
import org.dissan.models.job.Shift;
import org.dissan.models.job.ShiftApply;
import org.dissan.models.users.Employer;
import org.dissan.models.users.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface DAO {
    void putUser(User user) throws UserAlreadyExistException, SQLException;

    void saveConfig(User user);

    User loadConfig();
    //TODO ADJUST TO JSON ARRAY
    List<User> getUserList();

    void pushShift(Shift shift);

    List<Shift> getShiftList();

    List<ShiftApply> getSchedules(Employer employer);

    List<DemiseMessages> checkMessage(User user);

    void pushAppliance(ShiftApply shiftApply) throws IOException;

    List<ShiftApply> pullAppliances(User user) throws FileNotFoundException;

    void removeAppliance(ShiftApply apply) throws IOException;

    void updateAppliance(ShiftApply apply) throws IOException;

    //TODO add exception to all the processes throw ioException
    //TODO add function removeShift candidateShift
    //TODO add some feature that will load some configuration file to handle some state

}
