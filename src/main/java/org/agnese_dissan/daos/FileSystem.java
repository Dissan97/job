package org.agnese_dissan.daos;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.agnese_dissan.exceptions.UserAlreadyExistException;
import org.agnese_dissan.interfaces.DAO;
import org.agnese_dissan.models.job.DemiseMessages;
import org.agnese_dissan.models.job.Shift;
import org.agnese_dissan.models.job.ShiftApply;
import org.agnese_dissan.models.users.Employer;
import org.agnese_dissan.models.users.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileSystem implements DAO {

    private final String USER_PATH = "src/main/resources/org/agnese_dissan/dao/user.json";
    private final String CONFIG_PATH =  "src/main/resources/org/agnese_dissan/dao/config.json";
    @Override
    public void putUser(User user) throws UserAlreadyExistException {


        File usersFile = new File(USER_PATH);

        BufferedWriter writer;

        List<User> users = this.getUserList();

        if (users == null){
            users = new ArrayList<>();
        }else {
            for (User u: users
                 ) {
                if (u.getUsername().equals(user.getUsername())){
                    throw new UserAlreadyExistException();
                }
            }
        }

        String folder = user.getUserType().name().toLowerCase(Locale.ROOT) + "/";
        String userPath = "src/main/resources/org/agnese_dissan/dao/users/" + folder + user.getUsername();
        File dir = new File(userPath);

        if (!dir.mkdir()){
            throw new UserAlreadyExistException();
        }

        userPath += "/";
        String path = userPath + "schedules.json";
        File neededFiles = new File(path);

        try {

            users.add(user);

                        writer = new BufferedWriter(
                                new FileWriter(usersFile, false)
                        );

                        Gson gson = new Gson();
                        gson.toJson(users, writer);
            writer.flush();
            writer.close();


            if (!neededFiles.createNewFile()) {
                throw new IOException();
            }
            switch (user.getUserType()) {
                case EMPLOYEE -> {

                    path = userPath + "appliances.json";
                    neededFiles = new File(path);

                    if (!neededFiles.createNewFile()) {
                        throw new IOException();
                    }

                }

                case EMPLOYER -> {
                    path = userPath + "shifts.json";
                    neededFiles = new File(path);
                    if (!neededFiles.createNewFile()) {
                        throw new IOException();
                    }

                    path = userPath + "candidates.json";
                    neededFiles = new File(path);
                    if (!neededFiles.createNewFile()) {
                        throw new IOException();
                    }
                }
                }
            } catch (IOException e) {
                    e.printStackTrace();
                    throw new UserAlreadyExistException(e.getMessage());
            }

            

    }

        //TODO create a directory for new user with following file if is employer or is employee or is assistant

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
        String SHIFT_PATH = "src/main/resources/org/agnese_dissan/dao/shifts.json";
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

    @Override
    public List<ShiftApply> getSchedules(Employer employer) {
        return null;
    }

    @Override
    public List<DemiseMessages> checkMessage(User user) {
        String path = "src/main/resources/org/agnese_dissan/dao/"+user.getUsername()+"/issues.json";

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

        Type REVIEW_TYPE = new TypeToken<List<DemiseMessages>>() {
        }.getType();

        Gson gson = new Gson();

        return gson.fromJson(reader, REVIEW_TYPE);

    }


}
