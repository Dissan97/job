package org.agnese_dissan.beans;

import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.graphicControllers.LoginGraphic;
import org.agnese_dissan.models.time.JobDate;


public class LoginBean {

    private final LoginGraphic graphic;
    private int type;
    private String username = null, password = null, name = null, surname = null, dateOfBirth = null, cityOfBirth = null;


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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getPassword() {
        return password;
    }

    public int setPassword(String password) {

        if (isBad(password)){
            return -1;
        }

        if (password.length() < 8){
            return -1;
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
        if (isBad(surname)){
            return -1;
        }
        this.surname = surname;
        return 0;
    }


    public String getCityOfBirth() {
        return cityOfBirth;
    }

    public int setCityOfBirth(String cityOfBirth) {
        if (isBad(cityOfBirth)){
            return -1;
        }
        this.cityOfBirth = cityOfBirth;

        return 0;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) throws InvalidDateException {
        JobDate date = new JobDate(dateOfBirth);
        this.dateOfBirth = date.toString();
    }

    private boolean isBad(String str){
        return (str == null || str.equals(""));
    }

    public LoginGraphic getGraphic() {
        return this.graphic;
    }

    public void refresh() {
        this.username = null;
        this.password = null;
        this.name = null;
        this.surname = null;
        this.dateOfBirth = null;
        this.cityOfBirth = null;

    }
}
