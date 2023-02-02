package org.dissan;

import org.dissan.factories.DAOFactory;
import org.dissan.interfaces.DAO;
import org.dissan.models.users.User;

public class UserConfigJson {
    private boolean config = false;
    private User user;
    DAO dao;

    public UserConfigJson() {
        this.dao = DAOFactory.getDAO();
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
