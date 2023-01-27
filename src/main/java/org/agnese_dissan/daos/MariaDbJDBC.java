package org.agnese_dissan.daos;



import org.agnese_dissan.interfaces.DAO;
import org.agnese_dissan.models.job.Shift;
import org.agnese_dissan.models.job.ShiftApply;
import org.agnese_dissan.models.users.Employer;
import org.agnese_dissan.models.users.User;

import java.sql.*;
import java.util.List;

public class MariaDbJDBC implements DAO {

    @Override
    public void putUser(User user) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/Job?user=root&password=password");
        CallableStatement statement = connection.prepareCall("{ call new_user(?,?,?,?,?,?,?) }");
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getName());
        statement.setString(4, user.getSurname());
        statement.setString(5, user.getCityOfBirth());
        statement.setString(6, user.getDateOfBirth());
        statement.setInt(7, user.getUserType().ordinal());
        if (statement.execute()){
            System.out.println("EXECUTED");
        }
        statement.close();
        connection.close();
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
}
