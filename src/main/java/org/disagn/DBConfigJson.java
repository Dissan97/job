package org.disagn;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.disagn.cli.io.Printer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

public class DBConfigJson {
    private String driver;
    private String host;
    private String port;
    private String database;
    private String user;
    private String password;



    public DBConfigJson() {
        this.loadConfig();
    }



    private void loadConfig() {
        String path = Objects.requireNonNull(getClass().getResource("configDataBase.json")).getPath();
        JsonElement reader = null;

        try {
            reader = JsonParser.parseReader(
                    new JsonReader(
                            new FileReader(path)
                    )
            );
        } catch (FileNotFoundException e) {
            Printer.exception(e);
        }

        assert reader != null;
        JsonObject object = reader.getAsJsonObject();
        this.driver = object.get("driver").getAsString();
        this.host = object.get("host").getAsString();
        this.port = object.get("port").getAsString();
        this.database = object.get("database").getAsString();
        this.user = object.get("user").getAsString();
        this.password = object.get("password").getAsString();
    }

    public String getDriver() {
        return driver;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
