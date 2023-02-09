package org.disagn.daos;

import org.disagn.DBConfigJson;
import org.disagn.interfaces.DAO;
import org.disagn.models.job.Demise;
import org.disagn.models.job.Shift;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.User;

import java.sql.*;
import java.util.ArrayList;
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
        CallableStatement statement = connection.prepareCall("{ call newUser(?,?,?,?,?,?,?) }");
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getName());
        statement.setString(4, user.getSurname());
        statement.setString(5, user.getCityOfBirth());
        statement.setString(6, user.getDateOfBirth());
        statement.setString(7, user.getUserType().name());
        if (statement.execute()){
            throw new SQLException();
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
        return new ArrayList<>();
    }

    @Override
    public void pushShift(Shift shift) throws SQLException {
        Connection connection = this.getConnection();
        CallableStatement statement = connection.prepareCall("{call pushShift(?,?,?,?,?,?)}");
        statement.setString(1, shift.getCode());
        statement.setString(2, shift.getName());
        statement.setString(3, shift.getPlace());
        statement.setString(4, shift.getDateTime());
        statement.setString(5, shift.getDescription());
        statement.setString(6, shift.getEmployer());

        if (statement.execute()){
            throw new SQLException();
        }
        statement.close();
        connection.close();
    }

    @Override
    public List<Shift> pullShifts() {
        return new ArrayList<>();
    }

    @Override
    public List<ShiftApply> pullSchedules(User user) {
        return new ArrayList<>();
    }

    @Override
    public void pushAppliance(ShiftApply shiftApply) throws SQLException {
        Connection connection = this.getConnection();
        CallableStatement statement = connection.prepareCall("{call pushAppliance(?,?,?,?,?,?)}");
        statement.setString(1, shiftApply.toString());
        statement.setString(2, shiftApply.getApplyDate());
        statement.setString(3, shiftApply.getShiftDate());
        statement.setBoolean(4, false);
        statement.setString(5, shiftApply.getEmployer());
        statement.setString(6, shiftApply.getEmployee());

        if (statement.execute()){
            throw new SQLException();
        }
        statement.close();
        connection.close();
    }

    @Override
    public List<ShiftApply> pullAppliances(User user) {
        return new ArrayList<>();
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
    public void pushEmployeeDemise(Demise apply) throws SQLException {

        Connection connection = this.getConnection();
        CallableStatement statement = connection.prepareCall("{call pushDemise(?,?,?,?,?)}");
        statement.setString(1, apply.getApplication());
        statement.setString(2, apply.getMotivation());
        statement.setString(3, apply.getEmployee());
        statement.setBoolean(4, false);
        statement.setBoolean(5, false);

        if (statement.execute()){
            throw new SQLException();
        }
        statement.close();
        connection.close();
    }

    @Override
    public List<Demise> pullEmployeeDemise(String employee) {
        return new ArrayList<>();
    }

    @Override
    public List<Demise> pullDemises() {
        return new ArrayList<>();
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
