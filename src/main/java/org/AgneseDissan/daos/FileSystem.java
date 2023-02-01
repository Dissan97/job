package org.AgneseDissan.daos;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.AgneseDissan.Macros;
import org.AgneseDissan.exceptions.UserAlreadyExistException;
import org.AgneseDissan.interfaces.DAO;
import org.AgneseDissan.models.job.DemiseMessages;
import org.AgneseDissan.models.job.Shift;
import org.AgneseDissan.models.job.ShiftApply;
import org.AgneseDissan.models.users.Employer;
import org.AgneseDissan.models.users.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileSystem implements DAO {

    private final String daoPath = "src/main/resources/org/agnese_dissan/dao/";
    private final String userPath = daoPath + "user.json";
    private final String configPath = daoPath + "/config.json";
    private final String shiftPath = daoPath + "/shifts.json";

    @Override
    public void putUser(User user) throws UserAlreadyExistException {

        //GIVE THE CONTROL TO THE CONTROLLER

        File usersFile = new File(userPath);

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
            if (user.getUserType() == Macros.EMPLOYEE) {
                path = userPath + "appliances.json";
                neededFiles = new File(path);

                if (!neededFiles.createNewFile()) {
                    throw new IOException();
                }
            } else if (user.getUserType() == Macros.EMPLOYER) {
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
                    new FileWriter(this.configPath)
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
                            new FileReader(this.configPath)
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
                            new FileReader(userPath)
                    )
            );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Type reviewType = new TypeToken<List<User>>() {
        }.getType();

        Gson gson = new Gson();

        return gson.fromJson(reader, reviewType);
    }

    @Override
    public void pushShift(Shift shift) {

        File file = new File(this.shiftPath);
        //todo add a filter to this function
        File localFile = new File(daoPath + "users/employer/" +shift.getEmployer() + "/shifts.json");
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
                            new FileReader(this.shiftPath)
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
        String path = daoPath + user.getUsername()+"/issues.json";

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

        Type reviewType = new TypeToken<List<DemiseMessages>>() {
        }.getType();

        Gson gson = new Gson();

        return gson.fromJson(reader, reviewType);

    }

    @Override
    public void pushAppliance(ShiftApply shiftApply) throws IOException {

        String filename = "/appliances.json";
        String employerPath = daoPath + "users/employer/" + shiftApply.getEmployer() + filename;
        String employeePath = daoPath + "users/employee/" + shiftApply.getEmployee() + filename;

        List<ShiftApply> shiftApplies;

        shiftApplies = this.getEmployeeApplication(employeePath);


        shiftApplies.add(shiftApply);

        File employee = new File(employeePath);
        File employer = new File(employerPath);

        BufferedWriter employerWriter;
        BufferedWriter employeeWriter;

        Gson gson = new Gson();

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


    }

    @Override
    public List<ShiftApply> pullAppliances(User user) throws FileNotFoundException {
        String applyPath = "";

        if (user.getUserType() == Macros.EMPLOYEE){
            applyPath = daoPath + "users/employee/" + user.getUsername() + "/appliances.json";
        }else if (user.getUserType() == Macros.EMPLOYER){
            applyPath = daoPath + "users/employer/" + user.getUsername() + "/appliances.json";
        }
        return this.getEmployeeApplication(applyPath);
    }

    @Override
    public void removeAppliance(ShiftApply apply) throws IOException {
        String employeePath = daoPath + "users/employee/" + apply.getEmployee() + "/appliances.json";
        String employerPath = daoPath + "users/employer/" + apply.getEmployer() + "/appliances.json";

        List<ShiftApply> applyList = this.getEmployeeApplication(employeePath);


        applyList.removeIf(a->
            a.toString().equals(apply.toString())
         );

        Gson gson = new Gson();

        BufferedWriter writer = new BufferedWriter(
                new FileWriter(employeePath)
        );

        gson.toJson(applyList, writer);

        writer.close();

        applyList = this.getEmployeeApplication(employerPath);

        applyList.removeIf(a->
                a.toString().equals(apply.toString())
        );

        writer = new BufferedWriter(
                new FileWriter(employerPath)
        );

        gson.toJson(applyList, writer);

        writer.close();
    }

    @Override
    public void updateAppliance(ShiftApply apply) throws IOException {
        this.removeAppliance(apply);
        this.pushAppliance(apply);
    }

    private List<ShiftApply> getEmployeeApplication(String path) throws FileNotFoundException {
        BufferedReader reader;

        reader = new BufferedReader(
                new FileReader(path)
        );

        Type reviewType = new TypeToken<List<ShiftApply>>() {
        }.getType();

        Gson gson = new Gson();

        return gson.fromJson(reader, reviewType);

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
