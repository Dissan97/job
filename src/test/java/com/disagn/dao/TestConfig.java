package com.disagn.dao;

import org.disagn.DBConfigJson;
import org.disagn.cli.io.Printer;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestConfig {

    //Test Dissan Uddin Ahmed
    @Test
    void getConfig(){
        // Loading configuration from file json
        DBConfigJson configJson = new DBConfigJson();
        assert !configJson.getUser().equals("");

        String driver = configJson.getDriver();
        String host = configJson.getHost();
        String port = configJson.getPort();
        String database = configJson.getDatabase();
        String user = configJson.getUser();
        String password = configJson.getPassword();

        String driverHost = driver + "://" + host + ":" + port +"/";
        String dataBaseUser = database + "?user=" + user + "&password=" + password;
        String connectionUrl = driverHost + dataBaseUser;

        try {
            //Try connection to MariaDb driver
            Connection connection = DriverManager.getConnection(connectionUrl);
            assertNotNull(connection);
            Printer.print("Connection success");
            connection.close();
        } catch (SQLException e) {
            Printer.print(e.getMessage());
        }


    }
}
