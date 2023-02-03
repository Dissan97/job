package org.dissan.models.users;

import org.dissan.Macros;
import org.dissan.exceptions.InvalidDateException;
import org.dissan.models.time.JobDate;

public class User {
    //ATTRIBUTES
    private String username;
    private String password;
    private String name;
    private String surname;

    private String dateOfBirth;
    private String cityOfBirth;
    private Macros userType;
    //CONSTRUCTORS
    public User(String username, String password, String name, String surname, String dateOfBirth, String cityOfBirth) throws InvalidDateException {
        this(username, password, name, surname, dateOfBirth, cityOfBirth , Macros.EMPLOYEE);
    }

    public User(String username) throws InvalidDateException {
        this(username, null, null, null,"1900-01-01",null);
    }
    public User(String username, String password, String name, String surname,String dateOfBirth, String cityOfBirth ,Macros userType) throws InvalidDateException {
        this.setUsername(username);
        this.setPassword(password);
        this.setName(name);
        this.setSurname(surname);
        this.setDateOfBirth(dateOfBirth);
        this.setCityOfBirth(cityOfBirth);
        this.setUserType(userType);
    }
    public User() throws InvalidDateException {
        this(null, null, null, null, "1900-01-01", null,Macros.ASSISTANT);
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
    public Macros getUserType() {
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
    public void setUserType(Macros userType) {
        this.userType = userType;
    }

    public void setDateOfBirth(String dateOfBirth) throws InvalidDateException {

        JobDate date = new JobDate(dateOfBirth);
        this.dateOfBirth = date.toString();
    }

    public void setCityOfBirth(String cityOfBirth) {
        this.cityOfBirth = cityOfBirth;
    }
}
