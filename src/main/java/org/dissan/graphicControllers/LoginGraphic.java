package org.dissan.graphicControllers;

import org.dissan.Macros;
import org.dissan.beans.AccountBean;
import org.dissan.controllers.Login;
import org.dissan.exceptions.InvalidDateException;
import org.dissan.exceptions.UserAlreadyExistException;
import org.dissan.exceptions.UserLoginFailedException;
import org.dissan.factories.UiFactory;
import org.dissan.interfaces.JobView;
import org.dissan.models.users.User;

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
