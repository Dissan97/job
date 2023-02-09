package org.disagn.models.users;

import org.disagn.exceptions.InvalidDateException;
/**
 * In this class must be added reliability that show how this employer is reliable by payment and treatments
 */
public class Employer extends User {
    public Employer() throws InvalidDateException {
        super(null, null, null, null, "1900-01-01", null);
    }
    public Employer(String username, String password, String name, String surname, String dateOfBirth, String cityOfBirth) throws InvalidDateException {
        super(username, password, name, surname, dateOfBirth, cityOfBirth);
    }

    public Employer(User user) throws InvalidDateException {
        super(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getDateOfBirth(), user.getCityOfBirth());
    }
}
