package org.agnese_dissan.graphicControllers;

import org.agnese_dissan.Macros;
import org.agnese_dissan.beans.LoginBean;
import org.agnese_dissan.controllers.Login;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.exceptions.UserAlreadyExistException;
import org.agnese_dissan.exceptions.UserLoginFailedException;

public class LoginGraphic {

    private final Login login;
    public LoginGraphic(LoginBean bean){
        this.login = new Login(bean);
    }

    public void signIn(String username, String password, boolean store) throws UserLoginFailedException {
            login.signIn(username, password, store);
    }

    public void signUp(String username, String password, String name, String surname, String dateOfBirth, String cityOfBirth, Macros type) throws UserAlreadyExistException, InvalidDateException {
            login.signUp(username, password, name, surname, dateOfBirth, cityOfBirth, type);
    }

}
