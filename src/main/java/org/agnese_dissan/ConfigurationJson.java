package org.agnese_dissan;

import org.agnese_dissan.factories.DAOFactory;
import org.agnese_dissan.interfaces.DAO;
import org.agnese_dissan.models.users.User;

public class ConfigurationJson {
    private boolean config = false;
    private User user;
    DAO dao;

    public ConfigurationJson(boolean dao) {
        this.dao = DAOFactory.getDAO();
    }

    public boolean hasConfig(){
        this.user = dao.loadConfig();
        if (this.user != null){
            this.config = true;
        }
        return config;
    }

    public int getUserType() {
        return user.getUserType();
    }

    public User getUser() {
        return this.user;
    }
}
