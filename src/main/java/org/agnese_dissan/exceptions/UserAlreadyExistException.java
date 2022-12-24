package org.agnese_dissan.exceptions;

public class UserAlreadyExistException extends LoginException {

    public UserAlreadyExistException(){
        super("USER ALREADY EXIST");
    }
    public UserAlreadyExistException(String s) {
        super(s + ": USER ALREADY EXISTS");
    }
}
