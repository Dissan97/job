package org.agnese_dissan.daos;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.agnese_dissan.interfaces.DAO;
import org.agnese_dissan.models.Shift;
import org.agnese_dissan.models.users.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileSystem implements DAO {

    private final String USER_PATH = "src/main/resources/org/agnese_dissan/dao/user.json";
    private final String SHIFT_PATH = "src/main/resources/org/agnese_dissan/dao/shifts.json";
    private final String CONFIG_PATH =  "src/main/resources/org/agnese_dissan/dao/config.json";
    @Override
    public void putUser(User user) {
        File file = new File(USER_PATH);

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
        BufferedWriter writer;
        try {
             writer = new BufferedWriter(
                    new FileWriter(this.CONFIG_PATH)
            );
            Gson gson = new Gson();
            gson.toJson(user, writer);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public User loadConfig() {
        JsonElement jsonReader = null;

        try {
            jsonReader = JsonParser.parseReader(
                    new JsonReader(
                            new FileReader(this.CONFIG_PATH)
                    )
            );
        } catch (FileNotFoundException ignored) {
        }

        Type REVIEW_TYPE = new TypeToken<User>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(jsonReader, REVIEW_TYPE);
    }

    @Override
    public List<User> getUserList() {
        JsonElement reader;

        try {
            reader = JsonParser.parseReader(
                    new JsonReader(
                            new FileReader(USER_PATH)
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

    @Override
    public void publishShift(Shift shift) {
        File file = new File(SHIFT_PATH);

        BufferedWriter writer;

        List<Shift> shifts = this.getShiftList();
        if (shifts == null){
            shifts = new ArrayList<>();
        }
        shifts.add(shift);

        try {
            writer = new BufferedWriter(
                    new FileWriter(file, false)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();
        gson.toJson(shifts, writer);
        try {
            //TODO Remove this print
            System.out.println("WRITING IN FILESYSTEM: " + shift.getCode());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Shift> getShiftList() {
        return null;
    }


}
