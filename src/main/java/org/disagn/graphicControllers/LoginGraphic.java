package org.disagn.graphicControllers;

import org.disagn.Macros;
import org.disagn.beans.AccountBean;
import org.disagn.controllers.Login;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.exceptions.NoInterfaceException;
import org.disagn.exceptions.UserAlreadyExistException;
import org.disagn.exceptions.UserLoginFailedException;
import org.disagn.factories.UiFactory;
import org.disagn.interfaces.JobView;
import org.disagn.models.users.User;

import java.sql.SQLException;

public class LoginGraphic {

    private final Login controller;

    public LoginGraphic(){
        this.controller = new Login();
    }

    public void signIn(String username, String password, boolean store) throws UserLoginFailedException, NoInterfaceException {
            controller.signIn(username, password, store);
            User user = this.controller.getUser();
            JobView view = UiFactory.getUi(user.getUserType(), user);
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
