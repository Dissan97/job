package org.agnese_dissan.interfaces;


import org.agnese_dissan.models.User;

import java.util.List;

public interface DAO {
    void putUser(User user);

    void saveConfig(User user);

    User loadConfig();
    //TODO ADJUST TO JSON ARRAY
    List<User> getUserList();

}
