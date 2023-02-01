package org.dissan.models.users;

import org.dissan.Macros;
import org.dissan.exceptions.InvalidDateException;

public class Assistant extends User{
    public Assistant(String username, String password, String name, String surname, String dateOfBirth, String cityOfBirth, Macros userType) throws InvalidDateException {
        super(username, password, name, surname, dateOfBirth, cityOfBirth, userType);
    }

    public Assistant(User user) throws InvalidDateException {
        this(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getDateOfBirth(), user.getCityOfBirth(), Macros.ASSISTANT);
    }
}
