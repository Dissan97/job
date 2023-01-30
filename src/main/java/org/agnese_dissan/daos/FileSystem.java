package org.agnese_dissan.daos;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.agnese_dissan.exceptions.ShiftAlreadyApplied;
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

    private final String DAO_PATH = "src/main/resources/org/agnese_dissan/dao/";
    private final String USER_PATH = DAO_PATH + "user.json";
    private final String CONFIG_PATH = DAO_PATH + "/config.json";
    private final String SHIFT_PATH = DAO_PATH + "/shifts.json";

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
    public void pushShift(Shift shift) {

        File file = new File(this.SHIFT_PATH);
        //todo add a filter to this function
        File localFile = new File(DAO_PATH + "users/employer/" +shift.getEmployer() + "/shifts.json");
        BufferedWriter writer;
        BufferedWriter localWriter;

        List<Shift> shifts = this.getShiftList();
        if (shifts == null){
            shifts = new ArrayList<>();
        }
        shifts.add(shift);

        try {
            writer = new BufferedWriter(
                    new FileWriter(file, false)
            );

            localWriter = new BufferedWriter(
                    new FileWriter(localFile, false)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Gson gson = new Gson();
        gson.toJson(shifts, writer);

        shifts = this.getMineShifts(shifts, shift.getEmployer());

        gson.toJson(shifts, localWriter);
        try {
            writer.close();
            localWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Shift> getShiftList() {
        JsonElement reader;

        try {
            reader = JsonParser.parseReader(
                    new JsonReader(
                            new FileReader(this.SHIFT_PATH)
                    )
            );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Type REVIEW_TYPE = new TypeToken<List<Shift>>() {
        }.getType();

        Gson gson = new Gson();

        return gson.fromJson(reader, REVIEW_TYPE);
    }

    @Override
    public List<ShiftApply> getSchedules(Employer employer) {
        return null;
    }

    @Override
    public List<DemiseMessages> checkMessage(User user) {
        String path = DAO_PATH + user.getUsername()+"/issues.json";

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

    @Override
    public void pushAppliance(ShiftApply shiftApply) throws ShiftAlreadyApplied {

        String filename = "/appliances.json";
        String employerPath = DAO_PATH + "users/employer/" + shiftApply.getEmployer() + filename;
        String employeePath = DAO_PATH + "users/employee/" + shiftApply.getEmployee() + filename;

        List<ShiftApply> shiftApplies;
        try {
            shiftApplies = this.getEmployeeApplication(employeePath);
        } catch (FileNotFoundException e) {
            throw new ShiftAlreadyApplied();
        }

        if (shiftApplies != null) {
            for (ShiftApply apply : shiftApplies
            ) {
                if (apply.toString().equals(shiftApply.toString())) {
                    throw new ShiftAlreadyApplied();
                }
            }
        }  else {
            shiftApplies = new ArrayList<>();
        }

        shiftApplies.add(shiftApply);

        File employee = new File(employeePath);
        File employer = new File(employerPath);

        BufferedWriter employerWriter;
        BufferedWriter employeeWriter;

        Gson gson = new Gson();

        try {
            employeeWriter = new BufferedWriter(
                new FileWriter(employee)
            );

            gson.toJson(shiftApplies, employeeWriter);

            employerWriter = new BufferedWriter(
                    new FileWriter(employer)
            );

            shiftApplies = this.getEmployeeApplication(employerPath);
            if (shiftApplies == null){
                shiftApplies = new ArrayList<>();
            }
                shiftApplies.add(shiftApply);
            gson.toJson(shiftApplies, employerWriter);

            employeeWriter.close();
            employerWriter.close();

        }catch (IOException e){
            throw new ShiftAlreadyApplied();
        }
    }

    @Override
    public List<ShiftApply> pullAppliances(User user) throws FileNotFoundException {
        String employeePath = DAO_PATH + "users/employee/" + user.getUsername() + "/appliances.json";
        return this.getEmployeeApplication(employeePath);
    }

    private List<ShiftApply> getEmployeeApplication(String path) throws FileNotFoundException {
        BufferedReader reader;

        reader = new BufferedReader(
                new FileReader(path)
        );

        Type REVIEW_TYPE = new TypeToken<List<ShiftApply>>() {
        }.getType();

        Gson gson = new Gson();

        return gson.fromJson(reader, REVIEW_TYPE);

    }

    private List<Shift> getMineShifts(List<Shift> shifts, String employer){
        List<Shift> local = new ArrayList<>();
        for (Shift shift: shifts){
            if (shift.getEmployer().equals(employer)){
                local.add(shift);
            }
        }
        return local;
    }


}
