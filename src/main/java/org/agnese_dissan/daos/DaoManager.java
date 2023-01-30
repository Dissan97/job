package org.agnese_dissan.daos;

import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.exceptions.UserAlreadyExistException;
import org.agnese_dissan.factories.DAOState;
import org.agnese_dissan.interfaces.DAO;
import org.agnese_dissan.models.job.DemiseMessages;
import org.agnese_dissan.models.job.Shift;
import org.agnese_dissan.models.job.ShiftApply;
import org.agnese_dissan.models.users.Employer;
import org.agnese_dissan.models.users.User;

import java.sql.SQLException;
import java.util.List;

public class DaoManager implements DAO, Runnable {

    private final FileSystem fileSystem;
    private final MariaDbJDBC mariaDbJDBC;

    private final Thread thread;

    private User user;

    private DAOState state = null;
    private static DaoManager daoManager = null;

    private DaoManager() {
        this.fileSystem = new FileSystem();
        this.mariaDbJDBC = new MariaDbJDBC();
        thread = new Thread(this);
    }

    @Override
    public void putUser(User user) throws UserAlreadyExistException {
        //IMPLEMENT
    }

    @Override
    public void saveConfig(User user) {

    }

    @Override
    public User loadConfig() {
        return null;
    }

    @Override
    public List<User> getUserList() {
        return this.fileSystem.getUserList();
    }

    @Override
    public void publishShift(Shift shift) {

    }

    @Override
    public List<Shift> getShiftList() {
        return null;
    }

    @Override
    public List<ShiftApply> getSchedules(Employer employer) {
        return null;
    }

    @Override
    public List<DemiseMessages> checkMessage(User user) {

        return null;
    }

    @Override
    public void run() {
        switch (this.state){
            case PUT_USER -> {
                try {
                    this.mariaDbJDBC.putUser(this.user);
                    Output.pageMessage(thread.getName(), "user inserted", true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                this.state = null;
            }

            case LOAD_CONFIG -> {
                List<User> userList = this.mariaDbJDBC.getUserList();
                Output.println(userList.toString());
            }
        }
    }

    public static DaoManager getDaoManager(){
        if (daoManager == null){
            daoManager = new DaoManager();
        }
        return daoManager;
    }
}
