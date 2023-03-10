package org.disagn.models.users;

import org.disagn.Macros;
import org.disagn.exceptions.InvalidDateException;

/**
 * Must be added the attribute assistant contact
 */

public class Assistant extends User{
    public Assistant(String username, String password, String name, String surname, String dateOfBirth, String cityOfBirth, Macros userType) throws InvalidDateException {
        super(username, password, name, surname, dateOfBirth, cityOfBirth, userType);
    }

}
