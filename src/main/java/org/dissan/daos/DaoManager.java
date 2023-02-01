package org.dissan.daos;

import org.dissan.cli.io.Output;
import org.dissan.exceptions.UserAlreadyExistException;
import org.dissan.factories.DAOState;
import org.dissan.interfaces.DAO;
import org.dissan.models.job.DemiseMessages;
import org.dissan.models.job.Shift;
import org.dissan.models.job.ShiftApply;
import org.dissan.models.users.Employer;
import org.dissan.models.users.User;

import java.io.*;
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
            this.user = user;
            this.fileSystem.putUser(this.user);
            this.state = DAOState.PUT_USER;
            //this.thread.start();
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
    public void pushShift(Shift shift) {
    //needed to show demises should be used to push shift
    }

    @Override
    public List<Shift> getShiftList() {
        return this.fileSystem.getShiftList();
    }

    @Override
    public List<ShiftApply> getSchedules(Employer employer) {
        //needed to show demises don't know why is empty
        return null;
    }

    @Override
    public List<DemiseMessages> checkMessage(User user) {
        //needed to show demises
        return null;
    }

    @Override
    public void pushAppliance(ShiftApply shiftApply) throws IOException {
        this.fileSystem.pushAppliance(shiftApply);
    }

    @Override
    public List<ShiftApply> pullAppliances(User user) throws FileNotFoundException {
        return this.fileSystem.pullAppliances(user);
    }

    @Override
    public void removeAppliance(ShiftApply apply) throws IOException {
        this.fileSystem.removeAppliance(apply);
    }

    @Override
    public void updateAppliance(ShiftApply apply) throws IOException {
        this.fileSystem.updateAppliance(apply);
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