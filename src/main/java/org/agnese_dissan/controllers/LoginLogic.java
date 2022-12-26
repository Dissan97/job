package org.agnese_dissan.controllers;

import com.google.common.hash.Hashing;
import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.exceptions.UserAlreadyExistException;
import org.agnese_dissan.exceptions.UserLoginFailedException;
import org.agnese_dissan.factories.DAOFactory;
import org.agnese_dissan.factories.UiFactory;
import org.agnese_dissan.interfaces.DAO;
import org.agnese_dissan.interfaces.JobView;
import org.agnese_dissan.models.User;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.zip.DataFormatException;

public class LoginLogic {

    private User user;
    private String password;
    private final boolean db;

   private DAO dao;

    public LoginLogic(){
        this.db = false;
        dao = DAOFactory.getDAO(false);
        dao = DAOFactory.getDAO(false);
    }

    public void signIn(String username, String password) throws UserLoginFailedException {

        this.password = password;
        int userKind = verify(username, password);

        if (userKind == -1){
            throw new UserLoginFailedException();
        }
        JobView view = UiFactory.getUi(userKind, this.user);
        assert view != null;
        view.startUi();
    }


    public void signUp(String username, String password, String name, String surname, String dateOfBirth, String cityOfBirth) throws UserAlreadyExistException, DataFormatException {
        this.password = password;

        if(verify(username) == -1){
            throw new UserAlreadyExistException(username);
        }
        password = shaPassword(password);
        DAO dao = DAOFactory.getDAO(this.db);
        dao.putUser(new User(username, password, name, surname, dateOfBirth, cityOfBirth));

    }

    public String getPassword() {
        return password;
    }

    public User getUser() {
        return user;
    }


    //TODO use this feature
    /**void changeDAO(){
        this.db = !this.db;
        dao = DAOFactory.getDAO(this.db);
    }*/

    private int verify(String username, String password){

        List<User> users = dao.getUserList();
        password = shaPassword(password);

        for (User user: users
        ) {
            Output.println("username: " + user.getUsername() + " | password: " + user.getPassword());
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                this.user = user;
                return user.getUserType();
            }
        }

        return -1;
    }

    private int verify(String username) {
        List<User> users = dao.getUserList();
        if (user != null) {
            for (User user : users
            ) {
                if (Objects.equals(user.getUsername(), username)) {
                    return -1;
                }
            }
        }
        return 0;
    }

    private String shaPassword(String password){
       return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }

}
