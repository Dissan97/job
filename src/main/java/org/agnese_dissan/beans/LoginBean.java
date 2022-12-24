package org.agnese_dissan.beans;

import org.agnese_dissan.graphicControllers.LoginGraphic;

public class LoginBean {

    private final LoginGraphic graphic;
    private String username, password, name, surname;


//Constructor

    public LoginBean(){
        this.graphic = new LoginGraphic();
    }
    //methods


    public String getUsername() {
        return username;
    }

    public int setUsername(String username) {

        if (isBad(username)){
            return -1;
        }

        this.username = username;

        return 0;
    }

    public String getPassword() {
        return password;
    }

    public int setPassword(String password) {

        if (isBad(password)){
            return -1;
        }

        if (password.length() < 8){
            return -2;
        }

        this.password = password;

        return 0;
    }

    public String getName() {
        return name;
    }

    public int setName(String name) {

        if (isBad(name)){
            return -1;
        }

        this.name = name;

        return 0;
    }

    public String getSurname() {
        return surname;
    }

    public int setSurname(String surname) {
        if (isBad(name)){
            return -1;
        }
        this.surname = surname;
        return 0;
    }

    private boolean isBad(String str){
        return (str == null || str.equals(""));
    }

    public LoginGraphic getGraphic() {
        return this.graphic;
    }
}
