package org.disagn.daos;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.disagn.Macros;
import org.disagn.cli.io.Printer;
import org.disagn.exceptions.UserAlreadyExistException;
import org.disagn.interfaces.DAO;
import org.disagn.models.job.Demise;
import org.disagn.models.job.Shift;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.User;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileSystem implements DAO {

    private static final String DAO_PATH = "src/main/resources/org/disagn/dao/";
    private static final String USER_PATH = DAO_PATH + "/user.json";
    private static final String CONFIG_PATH = DAO_PATH + "/config.json";
    private static final String SHIFTS = "/shifts.json";
    private static final String DEMISES = "/demises.json";
    private static final String SCHEDULES = "/schedules.json";
    private static final String APPLIANCES = "/appliances.json";
    private static final String EMPLOYER_PATH = "users/employer/";
    private static final String EMPLOYEE_PATH = "users/employee/";
    private static final String SHIFT_PATH = DAO_PATH + SHIFTS;

    private static final String DEMISE_PATH = DAO_PATH + DEMISES;


    @Override
    public void pushUser(User user) throws UserAlreadyExistException, FileNotFoundException {

        File usersFile = new File(USER_PATH);

        BufferedWriter writer;

        List<User> users = this.getUserList();

        String folder = user.getUserType().name().toLowerCase(Locale.ROOT) + "/";
        String path = DAO_PATH + "users/" + folder + user.getUsername();

        File dir = new File(path);

        Printer.print(path);

        if (!dir.mkdir()){
            throw new UserAlreadyExistException();
        }

        try {


            this.initFile(path + SCHEDULES);
            this.initFile(path + APPLIANCES);
            Macros types = user.getUserType();
            if (types == Macros.EMPLOYER) {
                this.initFile(path + SHIFTS);
            } else if (types == Macros.EMPLOYEE) {
                this.initFile(path + DEMISES);
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
                    new FileWriter(CONFIG_PATH)
            );
            Gson gson = new Gson();
            gson.toJson(user, writer);
            writer.close();

        } catch (IOException e) {
            Printer.exception(e);
        }

    }

    @Override
    public User loadConfig() {
        JsonElement jsonReader = null;

        try {
            jsonReader = JsonParser.parseReader(
                    new JsonReader(
                            new FileReader(CONFIG_PATH)
                    )
            );
        } catch (FileNotFoundException ignored) {
            //This can be ignored
        }

        Type reviewType = new TypeToken<User>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(jsonReader, reviewType);
    }

    @Override
    public List<User> getUserList() throws FileNotFoundException {
        JsonElement reader;

            reader = JsonParser.parseReader(
                    new JsonReader(
                            new FileReader(USER_PATH)
                    )
            );


        Type reviewType = new TypeToken<List<User>>() {
        }.getType();

        Gson gson = new Gson();

        return gson.fromJson(reader, reviewType);
    }

    @Override
    public void pushShift(Shift shift) throws IOException {

        File file = new File(SHIFT_PATH);
        File localFile = new File(DAO_PATH + EMPLOYER_PATH +shift.getEmployer() + SHIFTS);
        BufferedWriter writer;
        BufferedWriter localWriter;

        List<Shift> localShifts = this.pullShifts();
        if (localShifts == null){
            localShifts = new ArrayList<>();
        }
        localShifts.add(shift);


            writer = new BufferedWriter(
                    new FileWriter(file, false)
            );

            localWriter = new BufferedWriter(
                    new FileWriter(localFile, false)
            );



        Gson gson = new Gson();
        gson.toJson(localShifts, writer);

        localShifts = this.getMineShifts(localShifts, shift.getEmployer());

        gson.toJson(localShifts, localWriter);
        try {
            writer.close();
            localWriter.close();
        } catch (IOException e) {
            Printer.exception(e);
        }
    }

    @Override
    public List<Shift> pullShifts() throws FileNotFoundException {
        JsonElement reader;


        reader = JsonParser.parseReader(
                new JsonReader(
                        new FileReader(SHIFT_PATH)
                )
        );


        Type reviewType = new TypeToken<List<Shift>>() {
        }.getType();

        Gson gson = new Gson();

        return gson.fromJson(reader, reviewType);
    }

    @Override
    public List<ShiftApply> pullSchedules(User user) throws FileNotFoundException {
        String type = user.getUserType().name().toLowerCase();
        String u = "users/";
        type += "/";
        String path = DAO_PATH + u + type + user.getUsername() + DEMISES;
        JsonElement reader;


            reader = JsonParser.parseReader(
                    new JsonReader(
                            new FileReader(path)
                    )
            );

        Type reviewType = new TypeToken<List<ShiftApply>>() {
        }.getType();

        Gson gson = new Gson();

        return gson.fromJson(reader, reviewType);

    }

    @Override
    public void pushSchedule(ShiftApply apply, User user) throws IOException {
        String employer = DAO_PATH + FileSystem.EMPLOYEE_PATH + apply.getEmployee();
        String employee = DAO_PATH + FileSystem.EMPLOYER_PATH + apply.getEmployer();
        this.setSchedule(apply, employer, user);
        this.setSchedule(apply, employee, user);
    }

    @Override
    public List<Demise> pullEmployeeDemise(String employee) throws FileNotFoundException {

        String path = DAO_PATH + EMPLOYEE_PATH + employee + DEMISES;

        return getDemises(path);
    }

    @Nullable
    private List<Demise> getDemises(String path) throws FileNotFoundException {
        JsonElement reader;


        reader = JsonParser.parseReader(
                new JsonReader(
                        new FileReader(path)
                )
        );

        Type reviewType = new TypeToken<List<Demise>>() {
        }.getType();

        Gson gson = new Gson();

        return gson.fromJson(reader, reviewType);
    }

    @Override
    public void pushEmployeeDemise(Demise apply) throws IOException {
        String employee = apply.getEmployee();
        String path = DAO_PATH + EMPLOYEE_PATH + employee + DEMISES;
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

        return getDemises(DEMISE_PATH);
    }

    @Override
    public void pushDemise(Demise demise) throws IOException {
        List<Demise> demiseList = this.pullDemises();
        demiseList.add(demise);


        BufferedWriter writer;

        writer = new BufferedWriter(
                new FileWriter(DEMISE_PATH)
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
                new FileWriter(DAO_PATH + EMPLOYEE_PATH +demise.getEmployee() + DEMISES)
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
                new FileWriter(DEMISE_PATH)
        );

        Gson gson = new Gson();

        gson.toJson(demiseList, writer);
        writer.close();
    }

    private void setSchedule(ShiftApply apply, String path, User user) throws IOException {
        path += SCHEDULES;
        List<ShiftApply> localSchedules = this.pullSchedules(user);
        localSchedules.add(apply);

        BufferedWriter writer = new BufferedWriter(
                new FileWriter(path)
        );

        Gson gson = new Gson();

        gson.toJson(localSchedules, writer);
        writer.close();

    }


    @Override
    public void pushAppliance(ShiftApply shiftApply) throws IOException {

        String filename = APPLIANCES;
        String pathEmployee = DAO_PATH + EMPLOYEE_PATH + shiftApply.getEmployee() + filename;
        String pathEmployer = DAO_PATH + EMPLOYER_PATH + shiftApply.getEmployer() + filename;

        List<ShiftApply> shiftApplies;

        shiftApplies = this.getEmployeeApplication(pathEmployee);


        shiftApplies.add(shiftApply);

        File employee = new File(pathEmployee);
        File employer = new File(pathEmployer);

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

        shiftApplies = this.getEmployeeApplication(pathEmployer);
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
            applyPath = DAO_PATH + EMPLOYEE_PATH + user.getUsername() + APPLIANCES;
        }else if (user.getUserType() == Macros.EMPLOYER){
            applyPath = DAO_PATH + EMPLOYER_PATH + user.getUsername() + APPLIANCES;
        }
        return this.getEmployeeApplication(applyPath);
    }

    @Override
    public void removeAppliance(ShiftApply apply) throws IOException {
        String pathEmployee = DAO_PATH + EMPLOYEE_PATH + apply.getEmployee() + APPLIANCES;
        String pathEmployer = DAO_PATH + EMPLOYER_PATH + apply.getEmployer() + APPLIANCES;

        List<ShiftApply> applyList = this.getEmployeeApplication(pathEmployee);

        applyList.removeIf(a->
            a.toString().equals(apply.toString())
         );

        Gson gson = new Gson();

        BufferedWriter writer = new BufferedWriter(
                new FileWriter(pathEmployee)
        );

        gson.toJson(applyList, writer);

        writer.close();

        applyList = this.getEmployeeApplication(pathEmployer);

        applyList.removeIf(a->
                a.toString().equals(apply.toString())
        );

        writer = new BufferedWriter(
                new FileWriter(pathEmployer)
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
