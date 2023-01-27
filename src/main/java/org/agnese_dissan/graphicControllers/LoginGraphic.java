package org.agnese_dissan.graphicControllers;

import org.agnese_dissan.Macros;
import org.agnese_dissan.controllers.Login;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.exceptions.UserAlreadyExistException;
import org.agnese_dissan.exceptions.UserLoginFailedException;
import org.agnese_dissan.factories.UiFactory;
import org.agnese_dissan.interfaces.JobView;
import org.agnese_dissan.models.users.User;

import java.sql.SQLException;

public class LoginGraphic {

    private final Login login;
    public LoginGraphic(){
        this.login = new Login();
    }

    public void signIn(String username, String password, boolean store) throws UserLoginFailedException {
            login.signIn(username, password, store);
            User user = this.login.getUser();
            JobView view = UiFactory.getUi(user.getUserType(), user);
            assert view != null;
            view.startUi();
    }

    public void signUp(String username, String password, String name, String surname, String dateOfBirth, String cityOfBirth, Macros type) throws UserAlreadyExistException, InvalidDateException, SQLException {
            login.signUp(username, password, name, surname, dateOfBirth, cityOfBirth, type);
    }

}
