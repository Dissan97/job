package org.AgneseDissan.graphicControllers;

import org.AgneseDissan.Macros;
import org.AgneseDissan.beans.AccountBean;
import org.AgneseDissan.controllers.Login;
import org.AgneseDissan.exceptions.InvalidDateException;
import org.AgneseDissan.exceptions.UserAlreadyExistException;
import org.AgneseDissan.exceptions.UserLoginFailedException;
import org.AgneseDissan.factories.UiFactory;
import org.AgneseDissan.interfaces.JobView;
import org.AgneseDissan.models.users.User;

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
