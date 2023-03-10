package org.disagn.daos;

import org.disagn.cli.io.Printer;
import org.disagn.exceptions.UserAlreadyExistException;
import org.disagn.interfaces.DAO;
import org.disagn.models.job.Demise;
import org.disagn.models.job.Shift;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.User;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import static org.disagn.daos.DAOState.PUSH_SCHEDULE;

/**
 * Dao manager that has the responsibilities to choose if to use FileSystem or Database
 * It's a Singleton class
 */

public class DaoManager implements DAO, Runnable {

    private final FileSystem fileSystem;
    private final MariaDbJDBC mariaDbJDBC;

    private final Thread thread;

    private User user;

    private DAOState state = null;
    private static DaoManager daoManager = null;
    private Shift shift;
    private ShiftApply appliance;
    private Demise demise;

    private static boolean pullDatabase = false;

    private DaoManager() {
        this.fileSystem = new FileSystem();
        this.mariaDbJDBC = new MariaDbJDBC();
        thread = new Thread(this);
    }

    public static void setPullDatabase(boolean b) {
        pullDatabase = b;
    }

    @Override
    public void pushUser(User user) throws UserAlreadyExistException, FileNotFoundException {
            this.user = user;
            this.fileSystem.pushUser(this.user);
            this.state = DAOState.POST_USER;
            this.thread.start();
    }

    @Override
    public List<User> pullUsers() throws FileNotFoundException, SQLException {
        if(pullDatabase){
            return this.mariaDbJDBC.pullUsers();
        }
        return this.fileSystem.pullUsers();
    }
    @Override
    public void saveConfig(User user) {
        this.fileSystem.saveConfig(user);
    }

    @Override
    public User loadConfig() {
        return this.fileSystem.loadConfig();
    }



    @Override
    public void pushShift(Shift shift) throws IOException {
        this.fileSystem.pushShift(shift);
        this.shift = shift;
        this.state = DAOState.POST_SHIFT;
        this.thread.start();
    }

    @Override
    public List<Shift> pullShifts() throws FileNotFoundException, SQLException {
        if (pullDatabase){
            return this.mariaDbJDBC.pullShifts();
        }
        return this.fileSystem.pullShifts();
    }


    @Override
    public void pushSchedule(ShiftApply apply, User user) throws IOException {
        this.fileSystem.pushSchedule(apply, user);
        this.state = PUSH_SCHEDULE;
        this.appliance = apply;
        this.thread.start();
    }

    @Override
    public List<ShiftApply> pullSchedules(User user) throws FileNotFoundException {
        //needed to show demises don't know why is empty
        return this.fileSystem.pullSchedules(user);
    }



    @Override
    public void pushAppliance(ShiftApply shiftApply) throws IOException {
        this.fileSystem.pushAppliance(shiftApply);
        this.appliance = shiftApply;
        this.state = DAOState.POST_APPLIANCE;
        this.thread.start();
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
    public List<Demise> pullEmployeeDemise(String employee) throws FileNotFoundException {
        return this.fileSystem.pullEmployeeDemise(employee);
    }

    @Override
    public void pushEmployeeDemise(Demise apply) throws IOException {
        this.fileSystem.pushEmployeeDemise(apply);
        this.state = DAOState.POST_DEMISE;
        this.demise = apply;
        this.thread.start();
    }


    @Override
    public List<Demise> pullDemises() throws FileNotFoundException {
        return this.fileSystem.pullDemises();
    }

    @Override
    public void pushDemise(Demise demise) throws IOException {
        this.fileSystem.pushDemise(demise);
    }

    @Override
    public void updateDemise(Demise demise) throws IOException {
        this.fileSystem.updateDemise(demise);
    }

    @Override
    public void removeDemise(Demise demise) throws IOException {
        this.fileSystem.removeDemise(demise);
    }


    @Override
    public void run() {
        switch (this.state){
            case POST_USER -> {
                try {
                    this.mariaDbJDBC.pushUser(this.user);
                    Printer.pageMessage(thread.getName(), "user inserted", true);
                } catch (SQLException e) {
                    Printer.exception(e);
                }

            }

            case POST_SHIFT -> {
                try {
                    this.mariaDbJDBC.pushShift(this.shift);
                } catch (SQLException e) {
                    Printer.exception(e);
                }
            }

            case POST_APPLIANCE ->{
                try {
                    this.mariaDbJDBC.pushAppliance(this.appliance);
                } catch (SQLException e) {
                    Printer.exception(e);
                }
            }

            case POST_DEMISE -> {
                try {
                    this.mariaDbJDBC.pushEmployeeDemise(this.demise);
                } catch (SQLException e) {
                    Printer.exception(e);
                }
            }

            case PUSH_SCHEDULE -> {
                try {
                    this.mariaDbJDBC.pushSchedule(this.appliance, null);
                }catch (SQLException e){
                    Printer.exception(e);
                }
            }
        }
        this.state = null;
    }

    public static DaoManager getDaoManager(){
        if (daoManager == null){
            daoManager = new DaoManager();
        }
        return daoManager;
    }


}
