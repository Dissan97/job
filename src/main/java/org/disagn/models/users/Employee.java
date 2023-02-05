package org.disagn.models.users;

import org.disagn.exceptions.InvalidDateException;

public class Employee extends User {

    public Employee() throws InvalidDateException {
        super(null, null, null, null, "1900-01-01", null);

    }

    public Employee(String username) throws InvalidDateException {
        super(username);
    }

    public Employee(User user) throws InvalidDateException {
        super(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getDateOfBirth(), user.getCityOfBirth());
    }
}
