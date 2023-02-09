package org.disagn.daos;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.disagn.Macros;
import org.disagn.cli.io.Output;
import org.disagn.exceptions.UserAlreadyExistException;
import org.disagn.interfaces.DAO;
import org.disagn.models.job.Demise;
import org.disagn.models.job.Shift;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileSystem implements DAO {

    private final String daoPath = "src/main/resources/org/disagn/dao/";
    private final String userPath = daoPath + "/user.json";
    private final String configPath = daoPath + "/config.json";
    private final String shifts = "/shifts.json";
    private final String demises = "/demises.json";
    private final String schedules = "/schedules.json";
    private final String appliances = "/appliances.json";
    private final String employerPath = "users/employer/";
    private final String employeePath = "users/employee/";
    private final String shiftPath = daoPath + shifts;

    private final String demisePath = daoPath + demises;


    @Override
    public void pushUser(User user) throws UserAlreadyExistException {

        //GIVE THE CONTROL TO THE CONTROLLER

        File usersFile = new File(userPath);

        BufferedWriter writer;

        List<User> users = this.getUserList();

        String folder = user.getUserType().name().toLowerCase(Locale.ROOT) + "/";
        String path = this.daoPath + "users/" + folder + user.getUsername();

        File dir = new File(path);

        Output.println(path);

        if (!dir.mkdir()){
            throw new UserAlreadyExistException();
        }

        try {


            this.initFile(path + schedules);
            this.initFile(path + appliances);
            Macros types = user.getUserType();
            if (types == Macros.EMPLOYER) {
                this.initFile(path + shifts);
            } else if (types == Macros.EMPLOYEE) {
                this.initFile(path + demises);
            }
        }catch (IOException e){
            throw  new UserAlreadyExistException(e.getMessage());
        }

        try {

            users.add(user);

                        writer = new BufferedWriter(
                                new FileWriter(usersFile, false)
                        );

                        Gson gson = new Gson();
                        gson.toJson(users, writer);
            writer.flush();
            writer.close();

            } catch (IOException e) {
                    throw new UserAlreadyExistException();
            }
    }


    private void initFile(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(path)
        );

        writer.write("[]");
        writer.close();
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
        File localFile = new File(daoPath + employerPath +shift.getEmployer() + shifts);
        BufferedWriter writer;
        BufferedWriter localWriter;

        List<Shift> shifts = this.pullShifts();
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
    public List<Shift> pullShifts() {
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
    public List<ShiftApply> pullSchedules(User user) {
        String type = user.getUserType().name().toLowerCase();
        String path = this.daoPath + "users/" + type + "/" + user.getUsername() + "/demises.json";
        System.out.println("I' m here");
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
        Type REVIEW_TYPE = new TypeToken<List<ShiftApply>>() {
        }.getType();

        Gson gson = new Gson();

        return gson.fromJson(reader, REVIEW_TYPE);

    }

    @Override
    public void pushSchedule(ShiftApply apply, User user) throws IOException {
        String employeePath = this.daoPath + this.employeePath + apply.getEmployee();
        String employerPath = this.daoPath + this.employerPath + apply.getEmployer();
        this.setSchedule(apply, employeePath, user);
        this.setSchedule(apply, employerPath, user);
    }

    @Override
    public List<Demise> pullEmployeeDemise(String employee) {

        String path = this.daoPath + this.employeePath + employee + "/demises.json";

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
        Type REVIEW_TYPE = new TypeToken<List<Demise>>() {
        }.getType();

        Gson gson = new Gson();

        return gson.fromJson(reader, REVIEW_TYPE);
    }

    @Override
    public void pushEmployeeDemise(Demise apply) throws IOException {
        String employee = apply.getEmployee();
        String path = this.daoPath + this.employeePath + employee + "/demises.json";
        List<Demise> demiseList = this.pullEmployeeDemise(employee);
        demiseList.add(apply);
        BufferedWriter writer;

        writer = new BufferedWriter(
                new FileWriter(path)
        );

        Gson gson = new Gson();

        gson.toJson(demiseList, writer);
        writer.close();

    }

    @Override
    public List<Demise> pullDemises() throws FileNotFoundException {

        JsonElement reader;


            reader = JsonParser.parseReader(
                    new JsonReader(
                            new FileReader(this.demisePath)
                    )
            );

        Type REVIEW_TYPE = new TypeToken<List<Demise>>() {
        }.getType();

        Gson gson = new Gson();

        return gson.fromJson(reader, REVIEW_TYPE);
    }

    @Override
    public void pushDemise(Demise demise) throws IOException {
        List<Demise> demiseList = this.pullDemises();
        demiseList.add(demise);


        BufferedWriter writer;

        writer = new BufferedWriter(
                new FileWriter(this.demisePath)
        );

        Gson gson = new Gson();

        gson.toJson(demiseList, writer);
        writer.close();

    }

    @Override
    public void updateDemise(Demise demise) throws IOException {
        List <Demise> listDemise = pullEmployeeDemise(demise.getEmployee());
        listDemise.removeIf(d ->
            d.getApplication().equals(demise.getApplication())
        );

        listDemise.add(demise);
        Gson gson = new Gson();

        BufferedWriter writer = new BufferedWriter(
                new FileWriter(daoPath + this.employeePath +demise.getEmployee() + "/demises.json")
        );

        gson.toJson(listDemise, writer);
        writer.close();


    }

    @Override
    public void removeDemise(Demise demise) throws IOException {
        List<Demise> demiseList = this.pullDemises();

        demiseList.removeIf(d ->
           d.getApplication().equals(demise.getApplication())
        );

        BufferedWriter writer;

        writer = new BufferedWriter(
                new FileWriter(this.demisePath)
        );

        Gson gson = new Gson();

        gson.toJson(demiseList, writer);
        writer.close();
    }

    private void setSchedule(ShiftApply apply, String path, User user) throws IOException {
        path += schedules;
        List<ShiftApply> schedules = this.pullSchedules(user);
        schedules.add(apply);

        BufferedWriter writer = new BufferedWriter(
                new FileWriter(path)
        );

        Gson gson = new Gson();

        gson.toJson(schedules, writer);
        writer.close();

    }


    @Override
    public void pushAppliance(ShiftApply shiftApply) throws IOException {

        String filename = appliances;
        String employeePath = daoPath + this.employeePath + shiftApply.getEmployee() + filename;
        String employerPath = daoPath + this.employerPath + shiftApply.getEmployer() + filename;

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
            applyPath = daoPath + this.employeePath + user.getUsername() + appliances;
        }else if (user.getUserType() == Macros.EMPLOYER){
            applyPath = daoPath + this.employerPath + user.getUsername() + appliances;
        }
        return this.getEmployeeApplication(applyPath);
    }

    @Override
    public void removeAppliance(ShiftApply apply) throws IOException {
        String employeePath = daoPath + this.employeePath + apply.getEmployee() + appliances;
        String employerPath = daoPath + this.employerPath + apply.getEmployer() + appliances;

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
