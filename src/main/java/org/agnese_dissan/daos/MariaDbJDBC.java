package org.agnese_dissan.daos;



import org.agnese_dissan.exceptions.ShiftAlreadyApplied;
import org.agnese_dissan.interfaces.DAO;
import org.agnese_dissan.models.job.DemiseMessages;
import org.agnese_dissan.models.job.Shift;
import org.agnese_dissan.models.job.ShiftApply;
import org.agnese_dissan.models.users.Employer;
import org.agnese_dissan.models.users.User;

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
    public void pushAppliance(ShiftApply shiftApply) throws ShiftAlreadyApplied {
        //Push appliance to the database
    }

    @Override
    public List<ShiftApply> pullAppliances(User user) {
        return null;
    }

    @Override
    public void removeAppliance(ShiftApply apply) {

    }
}
