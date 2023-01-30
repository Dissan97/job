package org.agnese_dissan.interfaces;


import org.agnese_dissan.exceptions.ShiftAlreadyApplied;
import org.agnese_dissan.exceptions.UserAlreadyExistException;
import org.agnese_dissan.models.job.DemiseMessages;
import org.agnese_dissan.models.job.Shift;
import org.agnese_dissan.models.job.ShiftApply;
import org.agnese_dissan.models.users.Employer;
import org.agnese_dissan.models.users.User;

import java.io.FileNotFoundException;
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

    void pushAppliance(ShiftApply shiftApply) throws ShiftAlreadyApplied;

    List<ShiftApply> pullAppliances(User user) throws FileNotFoundException;

    //TODO add exception to all the processes throw ioException
    //TODO add function removeShift candidateShift
    //TODO add some feature that will load some configuration file to handle some state

}
