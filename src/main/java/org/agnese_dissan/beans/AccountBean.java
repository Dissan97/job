package org.agnese_dissan.beans;

import org.agnese_dissan.models.users.User;

public class AccountBean {

    User user;

    public AccountBean(User user) {
        this.user = user;
    }

    public String getUsername() {
        return this.user.getUsername();
    }
    public String getName() {
        return this.user.getName();
    }

    public String getSurname() {
        return this.user.getSurname();
    }

    public String getDateOfBirth() {
        return this.user.getDateOfBirth();
    }

    public String getCityOfBirth() {
        return this.user.getCityOfBirth();
    }



}
