package org.agnese_dissan.models;

import java.util.zip.DataFormatException;

public class Employer extends User{
    public Employer() throws DataFormatException {
        super(null, null, null, null, "1900-01-01", null);
    }
    public Employer(String username, String password, String name, String surname, String dateOfBirth, String cityOfBirth) throws DataFormatException {
        super(username, password, name, surname, dateOfBirth, cityOfBirth);
    }

    public Employer(User user) throws DataFormatException {
        super(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getDateOfBirth(), user.getCityOfBirth());
    }
}
