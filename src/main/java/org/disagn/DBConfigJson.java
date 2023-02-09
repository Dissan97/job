package org.disagn;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.disagn.cli.io.Output;

import java.io.FileNotFoundException;
import java.io.FileReader;

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
        String path = "src/main/resources/org/disagn/dao/configDataBase.json";
        JsonElement reader = null;

        try {
            reader = JsonParser.parseReader(
                    new JsonReader(
                            new FileReader(path)
                    )
            );
        } catch (FileNotFoundException e) {
            Output.exception(e);
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
