package org.agnese_dissan.interfaces;


import org.agnese_dissan.models.job.Shift;
import org.agnese_dissan.models.users.User;

import java.util.List;

public interface DAO {
    void putUser(User user);

    void saveConfig(User user);

    User loadConfig();
    //TODO ADJUST TO JSON ARRAY
    List<User> getUserList();

    void publishShift(Shift shift);

    List<Shift> getShiftList();

    //TODO add exception to all the processes throw ioException
    //TODO add function removeShift candidateShift
    //TODO add some feature that will load some configuration file to handle some state

}
