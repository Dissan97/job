package org.disagn.exceptions;

public class UserLoginFailedException extends LoginException {

    public UserLoginFailedException() {
        super("USER NAME OR PASSWORD MISS MATCH");
    }
}
