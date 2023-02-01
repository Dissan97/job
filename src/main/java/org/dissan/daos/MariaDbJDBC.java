package org.dissan.daos;



import org.dissan.interfaces.DAO;
import org.dissan.models.job.DemiseMessages;
import org.dissan.models.job.Shift;
import org.dissan.models.job.ShiftApply;
import org.dissan.models.users.Employer;
import org.dissan.models.users.User;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class MariaDbJDBC implements DAO {

    @Override
    public void putUser(User user) throws SQLException {
       /**
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/Job?user=root&password=password");
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
        */
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
        return null;
    }

    @Override
    public void pushShift(Shift shift) {

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
    public void pushAppliance(ShiftApply shiftApply) throws IOException {
        //Push appliance to the database
    }

    @Override
    public List<ShiftApply> pullAppliances(User user) {
        return null;
    }

    @Override
    public void removeAppliance(ShiftApply apply) {

    }

    @Override
    public void updateAppliance(ShiftApply apply) {

    }
}
