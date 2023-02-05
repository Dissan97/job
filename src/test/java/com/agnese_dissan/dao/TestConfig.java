package com.agnese_dissan.dao;

import org.disagn.DBConfigJson;
import org.disagn.cli.io.Output;
import org.disagn.daos.MariaDbJDBC;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConfig {

    @Test
    public void getConfig(){
        DBConfigJson configJson = new DBConfigJson();
        assert !configJson.getUser().equals("");
        Output.println("host: " + configJson.getHost() + "\nport: " + configJson.getPort() + "\ndatabase: " + configJson.getDatabase() + "\nuser: " + configJson.getUser());

        MariaDbJDBC jdbc = new MariaDbJDBC();
        try {
            Connection connection = jdbc.getConnection();
            //Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/Job?user=root&password=password");
            assert connection != null;
            connection.close();
        } catch (SQLException e) {
            Output.println(e.getMessage());
        }


    }
}
