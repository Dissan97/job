package org.dissan.controllers;

import com.google.common.hash.Hashing;
import org.dissan.Macros;
import org.dissan.beans.AccountBean;
import org.dissan.daos.DaoManager;
import org.dissan.exceptions.InvalidDateException;
import org.dissan.exceptions.UserAlreadyExistException;
import org.dissan.exceptions.UserLoginFailedException;
import org.dissan.factories.DAOFactory;
import org.dissan.factories.UiFactory;
import org.dissan.interfaces.DAO;
import org.dissan.interfaces.JobView;
import org.dissan.models.users.User;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Login {

    private User user;
    private final DaoManager dao;

    public Login(){
        this.dao = DaoManager.getDaoManager();
    }
    //TODO control store value
    public void signIn(String username, String password, boolean store) throws UserLoginFailedException {

        Macros userKind = verify(username, password);

        if (userKind == Macros.ERROR){
            throw new UserLoginFailedException();
        }


        if (store){
            this.dao.saveConfig(this.user);
        }
    }


    public void signUp(String username, String password, String name, String surname, String dateOfBirth, String cityOfBirth, Macros type) throws UserAlreadyExistException, InvalidDateException, SQLException {
        if(verify(username) == Macros.ERROR){
            throw new UserAlreadyExistException(username);
        }
        password = shaPassword(password);
        this.dao.pushUser(new User(username, password, name, surname, dateOfBirth, cityOfBirth, type));

    }
    //TODO move this method to controller
    public static void LogOut(){
        DAO tempDao = DAOFactory.getDAO();
        tempDao.saveConfig(null);
        JobView view = UiFactory.getUi(Macros.START, null);
        assert view != null;
        view.startUi();
    }

    public User getUser() {
        return user;
    }


    private Macros verify(String username, String password){

        List<User> users = this.dao.getUserList();
        password = shaPassword(password);

        for (User user: users
        ) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                this.user = user;
                return user.getUserType();
            }
        }

        return Macros.ERROR;
    }

    private Macros verify(String username) {
        List<User> users = this.dao.getUserList();
        if (this.user != null) {
            for (User user : users
            ) {
                if (Objects.equals(user.getUsername(), username)) {
                    return Macros.ERROR;
                }
            }
        }
        return Macros.START;
    }

    private String shaPassword(String password){
       return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }

    public void pullEmployee(AccountBean bean) {
        DAO dao = DaoManager.getDaoManager();
        List<User> users = dao.getUserList();
        List<User> listBean = new ArrayList<>();
        if (users != null){
            for (User u:
                 users) {
                if (u.getUserType() == Macros.EMPLOYEE){
                    u.setPassword("");
                    listBean.add(u);
                }
            }
            bean.setEmployeeList(listBean);
        }
    }
}
