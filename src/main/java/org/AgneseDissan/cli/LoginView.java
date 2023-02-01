package org.AgneseDissan.cli;


import org.AgneseDissan.Macros;
import org.AgneseDissan.cli.io.Input;
import org.AgneseDissan.cli.io.Output;
import org.AgneseDissan.exceptions.InvalidDateException;
import org.AgneseDissan.exceptions.UserLoginFailedException;
import org.AgneseDissan.graphicControllers.LoginGraphic;
import org.AgneseDissan.interfaces.JobView;
import org.AgneseDissan.models.time.JobDate;

import java.util.ArrayList;
import java.util.List;

import static org.AgneseDissan.Macros.*;

public class LoginView implements JobView{

    //todo change this... make more simpler by eliminating some control
    private final LoginGraphic controller;
    private final List<String> commandList = new ArrayList<>();

    private String username = null;
    private String password = null;
    private String  name = null;
    private String surname = null;
    private String dateOfBirth = null;
    private String cityOfBirth = null;

    public LoginView() {
        commandList.add("SIGN_IN");
        commandList.add("SIGN_UP");
        commandList.add("HELP");
        commandList.add("EXIT");
        this.controller = new LoginGraphic();
    }

    @Override
    public void startUi() {

        String line;
        String page = "LOGIN";
        while (true) {

            Output.pageMessage(page, "insert a command type help to get command list", false);
            line = Input.getCmd(this.commandList);

            Macros ret = Macros.START;
            switch (line) {
                case "SIGN_IN" -> {
                    this.refresh();
                    Output.pageMessage(page, "Starting sign up procedure", true);
                    ret = this.signIn();
                }
                case "SIGN_UP" -> {
                    this.refresh();
                    Output.pageMessage(page, "Starting sign up procedure", true);
                    ret = this.signUp();
                }
                case "HELP" -> Output.printList(page, this.commandList);
                case "EXIT" -> {
                    System.out.println("LOGIN: Bye...");
                    System.exit(0);
                }
                case "" ->{}
                case "INVALID_NUMBER" ->
                    Output.pageMessage(page, line + " VALUES ALLOWED 0.." + (this.commandList.size() - 1), true);

                default ->
                    Output.pageMessage(page, "{" + line + "} COMMAND_NOT_FOUND TYPE HELP FOR COMMAND LIST", true);

            }

            if (ret == Macros.SIGN_IN_SUCCESS){
                return;
            } else if (ret == Macros.SIGN_IN_FAILED) {
                Output.pageMessage(page, "There's some error", true);
            }
        }


    }



    private Macros signUp(){

        Macros type = Macros.EMPLOYEE;
        String line;
        String page = "SING_UP";

        if (this.getUsernameAndPassword(page) == BACK_CALL){
            return BACK_CALL;
        }

        if (this.name == null){
            line = Input.getInfo(page, "name");
            if (exit(line)){
                return BACK_CALL;
            }
            if (this.controller.isGood(line, false)){
                this.name = line;
            }else {
                Output.pageMessage(page,"name cannot be blank", true);
                return signUp();
            }
        }

        if (this.surname == null){
            line = Input.getInfo(page, "surname");
            if (exit(line)){
                return BACK_CALL;
            }
            if (this.controller.isGood(line, false)){
                this.surname = line;
            }else {
                Output.pageMessage(page,"surname cannot be blank", true);
                return signUp();
            }
        }

        if (this.dateOfBirth == null){
            line = Input.getInfo(page, "date of birth");
            if (exit(line)){
                return BACK_CALL;
            }
            if (this.controller.isGood(line, false)){
                try {
                    JobDate date = new JobDate(line);
                    this.dateOfBirth = date.toString();
                } catch (InvalidDateException e) {
                    Output.pageMessage(page, e.getMessage(), true);
                    return signUp();
                }
            }else {
                Output.pageMessage(page,"date cannot be blank", true);
                return signUp();
            }
        }

        if (this.cityOfBirth == null){
            line = Input.getInfo(page, "city of birth");
            if (exit(line)){
                return BACK_CALL;
            }
            if (this.controller.isGood(line, false)){
                this.cityOfBirth = line;
            }else {
                Output.pageMessage(page,"city of birth cannot be blank", true);
                return signUp();
            }
        }

        line = Input.getInfo(page, "Register as {" + Macros.EMPLOYER.name() + "}? y to accept");
        if (this.exit(line)){
            return BACK_CALL;
        }

        if (line.equalsIgnoreCase("y") || line.equalsIgnoreCase("yes")){
            type = Macros.EMPLOYER;
        }

        try {
            this.controller.signUp(this.username, this.password, this.name, this.surname, this.dateOfBirth, this.cityOfBirth, type);
        } catch (Exception e) {
            e.printStackTrace();
            return Macros.ERROR;
        }finally {
            this.refresh();
        }


        return SIGN_UP_SUCCESS;
    }

    private Macros signIn() {
        String line;
        String page = "SIGN_IN";
        boolean store = false;

        if (this.getUsernameAndPassword(page) == BACK_CALL){
            return BACK_CALL;
        }

        line = Input.getInfo(page, "store configuration y to accept");
        if (line.equalsIgnoreCase("y") || line.equalsIgnoreCase("yes")){
            store = true;
        }


        try {
            this.controller.signIn(this.username, this.password, store);
        } catch (UserLoginFailedException e) {
            Output.pageMessage(page, e.getMessage(), true);
            return SIGN_IN_FAILED;
        }
        return SIGN_IN_SUCCESS;
    }



    private boolean exit(String s){
        boolean ret = false;
        if (s.equalsIgnoreCase("#BACK") || s.equalsIgnoreCase("#EXIT")){
            ret = true;
            this.refresh();
            Output.pageMessage("LOGIN", "EXITING PROCEDURE", true);
        }
        return ret;
    }

    private Macros getUsernameAndPassword(String page){
        String line;
        if (this.username == null) {
            line = Input.getInfo(page, "Username");
            if (exit(line)){
                return BACK_CALL;
            }
            if (this.controller.isGood(line, false)) {
                this.username = line;
            }else {
                Output.pageMessage(page,"Cannot insert blank username", true);
                return signUp();
            }
        }

        if (this.password == null){
            line = Input.getInfo(page, "password");
            if (exit(line)){
                return BACK_CALL;
            }
            if (this.controller.isGood(line, true)) {
                this.password = line;
            }else {
                Output.pageMessage(page,"error password must have at least 8 character", true);
                return signUp();
            }
        }
        return START;
    }



    private void refresh(){
        this.username = null;
        this.password = null;
        this.name = null;
        this.cityOfBirth = null;
        this.dateOfBirth = null;
    }


}
