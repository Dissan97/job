package org.agnese_dissan.models.users;

import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.models.users.User;

public class Employee extends User {

    public Employee() throws InvalidDateException {
        super(null, null, null, null, "1900-01-01", null);

    }

    public Employee(String username, String password, String name, String surname, String dateOfBirth, String cityOfBirth) throws InvalidDateException {
        super(username, password, name, surname, dateOfBirth, cityOfBirth);
    }

    public Employee(User user) throws InvalidDateException {
        super(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getDateOfBirth(), user.getCityOfBirth());
    }
}
