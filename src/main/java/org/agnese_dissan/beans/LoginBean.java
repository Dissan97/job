package org.agnese_dissan.beans;

public class LoginBean {

    public boolean isGood(String str, boolean pwd){
            return (str != null && !str.equals("")) && (!pwd || str.length() >= 8);
    }

}
