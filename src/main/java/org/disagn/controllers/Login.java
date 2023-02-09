package org.disagn.controllers;

import com.google.common.hash.Hashing;
import org.disagn.Macros;
import org.disagn.beans.AccountBean;
import org.disagn.daos.DaoManager;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.exceptions.NoInterfaceException;
import org.disagn.exceptions.UserAlreadyExistException;
import org.disagn.exceptions.UserLoginFailedException;
import org.disagn.factories.DAOFactory;
import org.disagn.factories.UiFactory;
import org.disagn.interfaces.DAO;
import org.disagn.interfaces.JobView;
import org.disagn.models.users.User;

import java.io.FileNotFoundException;
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
    public void signIn(String username, String password, boolean store) throws UserLoginFailedException, FileNotFoundException {

        Macros userKind = verify(username, password);

        if (userKind == Macros.ERROR){
            throw new UserLoginFailedException();
        }


        if (store){
            this.dao.saveConfig(this.user);
        }
    }


    public void signUp(String username, String password, String name, String surname, String dateOfBirth, String cityOfBirth, Macros type) throws UserAlreadyExistException, InvalidDateException, SQLException, FileNotFoundException {
        if(verify(username) == Macros.ERROR){
            throw new UserAlreadyExistException(username);
        }
        password = shaPassword(password);
        this.dao.pushUser(new User(username, password, name, surname, dateOfBirth, cityOfBirth, type));

    }

    public static void LogOut() throws NoInterfaceException {
        DAO tempDao = DAOFactory.getDAO();
        tempDao.saveConfig(null);
        JobView view = UiFactory.getUi(Macros.START, null);
        view.startUi();
    }

    public User getUser() {
        return user;
    }


    private Macros verify(String username, String password) throws FileNotFoundException {

        List<User> users = this.dao.getUserList();
        password = shaPassword(password);

        for (User u: users
        ) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                this.user = u;
                return u.getUserType();
            }
        }

        return Macros.ERROR;
    }

    private Macros verify(String username) throws FileNotFoundException {
        List<User> users = this.dao.getUserList();
        if (this.user != null) {
            for (User u : users
            ) {
                if (Objects.equals(u.getUsername(), username)) {
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

    public void pullEmployee(AccountBean bean) throws FileNotFoundException {
        DAO daoManager = DaoManager.getDaoManager();
        List<User> users = daoManager.getUserList();
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
