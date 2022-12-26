package org.agnese_dissan.graphicControllers;


import org.agnese_dissan.controllers.LoginLogic;
import org.agnese_dissan.exceptions.UserAlreadyExistException;
import org.agnese_dissan.exceptions.UserLoginFailedException;

import java.util.zip.DataFormatException;

public class LoginGraphic {

    LoginLogic login;
    public LoginGraphic(){
        this.login = new LoginLogic();
    }

    public void signIn(String username, String password) throws UserLoginFailedException {
            login.signIn(username, password);
    }

    public void signUp(String username, String password, String name, String surname, String dateOfBirth, String cityOfBirth) throws UserAlreadyExistException, DataFormatException {
            login.signUp(username, password, name, surname, dateOfBirth, cityOfBirth);
    }

}
