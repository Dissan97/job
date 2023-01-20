package org.agnese_dissan.beans;

import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.graphicControllers.LoginGraphic;
import org.agnese_dissan.models.time.JobDate;
import org.agnese_dissan.models.users.User;


public class LoginBean {

    private User user = null;
    private int type;



//Constructor

    //methods


    public String getUsername() {
        return this.user.getUsername();
    }




    public int getType() {
        return this.user.getUserType();
    }


    public boolean isBad(String str, boolean pwd){
            return (str == null || str.equals("")) || (pwd && str.length() < 8);
    }



    public void refresh() {

    }
}
