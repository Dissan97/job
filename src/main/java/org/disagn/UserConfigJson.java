package org.disagn;

import org.disagn.daos.DaoManager;
import org.disagn.interfaces.DAO;
import org.disagn.models.users.User;

public class UserConfigJson {
    private boolean config = false;
    private User user;
    DAO dao;

    public UserConfigJson() {
        this.dao = DaoManager.getDaoManager();
    }

    public boolean hasConfig(){
        this.user = dao.loadConfig();
        if (this.user != null){
            this.config = true;
        }
        return config;
    }

    public Macros getUserType() {
        return user.getUserType();
    }

    public User getUser() {
        return this.user;
    }
}
