package org.dissan.daos;

import org.dissan.DBConfigJson;
import org.dissan.interfaces.DAO;
import org.dissan.models.job.Demise;
import org.dissan.models.job.Shift;
import org.dissan.models.job.ShiftApply;
import org.dissan.models.users.User;

import java.sql.*;
import java.util.List;

public class MariaDbJDBC implements DAO {

    private final String driver;
    private final String host;
    private final String port;
    private final String database;
    private final String user;
    private final String password;
    private String url;

    public MariaDbJDBC() {
        DBConfigJson configJson = new DBConfigJson();
        this.driver = configJson.getDriver();
        this.host = configJson.getHost();
        this.port = configJson.getPort();
        this.database = configJson.getDatabase();
        this.user = configJson.getUser();
        this.password = configJson.getPassword();
        this.configUrl();
    }

    private void configUrl() {
        String driverHost = this.driver + "://" + this.host + ":" + this.port +"/";
        String dataBaseUser = this.database + "?user=" + this.user + "&password=" + this.password;
        this.url = driverHost + dataBaseUser;
    }

    public Connection getConnection() throws SQLException {
        this.configUrl();
        return DriverManager.getConnection(this.url);
    }


    @Override
    public void pushUser(User user) throws SQLException {

        Connection connection = this.getConnection();
        CallableStatement statement = connection.prepareCall("{ call new_user(?,?,?,?,?,?,?) }");
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getName());
        statement.setString(4, user.getSurname());
        statement.setString(5, user.getCityOfBirth());
        statement.setString(6, user.getDateOfBirth());
        statement.setString(7, user.getUserType().name());
        if (statement.execute()){
            System.out.println("EXECUTED");
        }
        statement.close();
        connection.close();

    }

    @Override
    public void saveConfig(User user) {
        //comment need to avoid code smell
    }

    @Override
    public User loadConfig() {
        return null;
    }

    @Override
    public List<User> getUserList() {
        return null;
    }

    @Override
    public void pushShift(Shift shift) {
        //Database will be added later
    }

    @Override
    public List<Shift> pullShifts() {
        return null;
    }

    @Override
    public List<ShiftApply> pullSchedules(User user) {
        return null;
    }

    @Override
    public void pushAppliance(ShiftApply shiftApply) {
        //Push appliance to the database
    }

    @Override
    public List<ShiftApply> pullAppliances(User user) {
        return null;
    }

    @Override
    public void removeAppliance(ShiftApply apply) {
        //will remove appliances
    }

    @Override
    public void updateAppliance(ShiftApply apply) {
        //new implementation
    }

    @Override
    public void pushSchedule(ShiftApply apply, User user) {
        //it will push
    }

    @Override
    public void pushEmployeeDemise(Demise apply) {
        //shall be implemented
    }

    @Override
    public List<Demise> pullEmployeeDemise(String employee) {
        return null;
    }

    @Override
    public List<Demise> pullDemises() {
        return null;
    }

    @Override
    public void pushDemise(Demise demise) {
        //It will be handled
    }

    @Override
    public void updateDemise(Demise demise) {
        //Must be coded
    }

    @Override
    public void removeDemise(Demise demise) {
        //Must be applied
    }
}
