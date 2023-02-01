package org.agnese_dissan.beans;

import org.agnese_dissan.models.users.User;

import java.util.List;

public class AccountBean {

    List<User> userList = null;

    public void setEmployeeList(List<User> listBean) {
        this.userList = listBean;
    }

    public List<User> getListEmployees() {
        return this.userList;
    }
}
