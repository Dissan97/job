package org.disagn.models.users;

import org.disagn.exceptions.InvalidDateException;

/**
 * In this class must be added review that show how this employee works and this changes must be handled
 */

public class Employee extends User {


    public Employee() throws InvalidDateException {
        super(null, null, null, null, "1900-01-01", null);

    }

    public Employee(User user) throws InvalidDateException {
        super(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getDateOfBirth(), user.getCityOfBirth());
    }
}
