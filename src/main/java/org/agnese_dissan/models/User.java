package org.agnese_dissan.models;

import org.agnese_dissan.Macros;
import org.agnese_dissan.models.time.Date;

import java.util.zip.DataFormatException;

public class User {
    //ATTRIBUTES
    private String username;
    private String password;
    private String name;
    private String surname;

    private String dateOfBirth;
    private String cityOfBirth;
    private int userType;
    //CONSTRUCTORS
    public User(String username, String password, String name, String surname, String dateOfBirth, String cityOfBirth) throws DataFormatException {
        this(username, password, name, surname, dateOfBirth, cityOfBirth , Macros.EMPLOYEE.ordinal());
    }
    public User(String username, String password, String name, String surname,String dateOfBirth, String cityOfBirth ,int userType) throws DataFormatException {
        this.setUsername(username);
        this.setPassword(password);
        this.setName(name);
        this.setSurname(surname);
        this.setDateOfBirth(dateOfBirth);
        this.setCityOfBirth(cityOfBirth);
        this.setUserType(userType);
    }
    public User() throws DataFormatException {
        this(null, null, null, null, "1900-01-01", null,-1);
    }
    //GETTERS
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public int getUserType() {
        return userType;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCityOfBirth() {
        return cityOfBirth;
    }

    //SETTERS
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setUserType(int userType) {
        this.userType = userType;
    }

    public void setDateOfBirth(String dateOfBirth) throws DataFormatException {

        Date date = new Date(dateOfBirth);
        this.dateOfBirth = date.toString();
    }

    public void setCityOfBirth(String cityOfBirth) {
        this.cityOfBirth = cityOfBirth;
    }
}
