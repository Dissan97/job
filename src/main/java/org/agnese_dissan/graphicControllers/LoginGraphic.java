package org.agnese_dissan.graphicControllers;

import org.agnese_dissan.Macros;
import org.agnese_dissan.beans.AccountBean;
import org.agnese_dissan.controllers.Login;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.exceptions.UserAlreadyExistException;
import org.agnese_dissan.exceptions.UserLoginFailedException;
import org.agnese_dissan.factories.UiFactory;
import org.agnese_dissan.interfaces.JobView;
import org.agnese_dissan.models.users.User;

import java.sql.SQLException;

public class LoginGraphic {

    private final Login controller;

    public LoginGraphic(){
        this.controller = new Login();
    }

    public void signIn(String username, String password, boolean store) throws UserLoginFailedException {
            controller.signIn(username, password, store);
            User user = this.controller.getUser();
            JobView view = UiFactory.getUi(user.getUserType(), user);
            assert view != null;
            view.startUi();
    }

    public void signUp(String username, String password, String name, String surname, String dateOfBirth, String cityOfBirth, Macros type) throws UserAlreadyExistException, InvalidDateException, SQLException {
            controller.signUp(username, password, name, surname, dateOfBirth, cityOfBirth, type);
    }

    public boolean isGood(String str, boolean pwd){
        return (str != null && !str.equals("")) && (!pwd || str.length() >= 8);
    }

    public void pullEmployee(AccountBean bean){
        this.controller.pullEmployee(bean);
    }

}
