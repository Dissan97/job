package org.agnese_dissan.daos;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.agnese_dissan.interfaces.DAO;
import org.agnese_dissan.models.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileSystem implements DAO {

    private final String path = "src/main/resources/org/agnese_dissan/dao/user.json";
    @Override
    public void putUser(User user) {
        File file = new File(path);

        BufferedWriter writer;

        List<User> users = this.getUserList();
        if (users == null){
            users = new ArrayList<>();
        }
        users.add(user);

        try {
            writer = new BufferedWriter(
                    new FileWriter(file, false)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();
        gson.toJson(users, writer);
        try {
            //TODO Remove this print
            System.out.println("WRITING IN FILESYSTEM: " + user.getUsername());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        JsonElement reader;

        try {
            reader = JsonParser.parseReader(
                    new JsonReader(
                            new FileReader(path)
                    )
            );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Type REVIEW_TYPE = new TypeToken<List<User>>() {
        }.getType();

        Gson gson = new Gson();

        return gson.fromJson(reader, REVIEW_TYPE);
    }

}
