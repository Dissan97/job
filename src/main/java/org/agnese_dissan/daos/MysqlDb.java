package org.agnese_dissan.daos;



import org.agnese_dissan.interfaces.DAO;
import org.agnese_dissan.models.User;

import java.util.List;

public class MysqlDb implements DAO {

    @Override
    public void putUser(User user) {

    }

    @Override
    public void saveConfig(User user) {

    }

    @Override
    public User loadConfig() {
        return null;
    }

    @Override
    public List<User> getUserList() {
        return null;
    }
}
