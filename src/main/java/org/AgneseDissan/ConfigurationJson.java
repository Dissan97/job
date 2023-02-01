package org.AgneseDissan;

import org.AgneseDissan.factories.DAOFactory;
import org.AgneseDissan.interfaces.DAO;
import org.AgneseDissan.models.users.User;

public class ConfigurationJson {
    private boolean config = false;
    private User user;
    DAO dao;

    public ConfigurationJson() {
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
