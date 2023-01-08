package org.agnese_dissan.cli;


import org.agnese_dissan.Macros;
import org.agnese_dissan.beans.LoginBean;
import org.agnese_dissan.cli.io.Input;
import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.exceptions.LoginException;
import org.agnese_dissan.factories.UiFactory;
import org.agnese_dissan.graphicControllers.LoginGraphic;
import org.agnese_dissan.interfaces.JobView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import static org.agnese_dissan.Macros.BACK_CALL;

public class LoginView implements JobView{


    private final LoginBean bean;
    private final LoginGraphic graphic;
    private final List<String> commandList = new ArrayList<>();

    public LoginView() {
        commandList.add("SIGN_IN");
        commandList.add("SIGN_UP");
        commandList.add("HELP");
        commandList.add("EXIT");
        this.bean = new LoginBean();
        this.graphic = this.bean.getGraphic();
    }

    @Override
    public void startUi() {

        Output.pageMessage("LOGIN", "TYPE HELP TO GET COMMAND LIST", true);
        String line;

        while (true) {
            Output.print("[LOGIN]:-> ");
            line = Input.getCmd(this.commandList);

            int ret = -1;
            switch (line) {
                case "SIGN_IN" -> ret = this.signIn(true);
                case "SIGN_UP" -> ret = this.signUp(true);
                case "HELP" -> Output.getCommandList("LOGIN", this.commandList);
                case "EXIT" -> {
                    System.out.println("LOGIN: Bye...");
                    System.exit(0);
                }
                case "" ->{}
                case "INVALID_NUMBER" -> {
                    Output.pageMessage("LOGIN", line + " VALUES ALLOWED 0.." + (this.commandList.size() - 1), true);
                }
                default ->
                    Output.pageMessage("LOGIN", "{" + line + "} COMMAND_NOT_FOUND TYPE HELP FOR COMMAND LIST", true);

            }

            if (ret == Macros.SIGN_IN_SUCCESS.ordinal()){
                this.bean.refresh();
                return;
            } else if (ret == Macros.SIGN_IN_FAILED.ordinal()) {
                Output.pageMessage("LOGIN", "There's some error", true);
            }
        }


    }

    private int signUp(boolean first) {
        String line;
        int type = Macros.EMPLOYEE.ordinal();

        if (first) {
            Output.pageMessage("LOGIN", "Starting SING UP procedure type #back to #exit", true);
        }
        if (this.bean.getUsername() == null) {
            Output.pageMessage("LOGIN","Username", false);
            line = Input.line();

            if(this.exit(line)){
                return BACK_CALL.ordinal();
            }

            if (this.bean.setUsername(line) == -1){
                Output.pageMessage("LOGIN", "Username must contains a letter", true);
                return this.signUp(false);
            }
            Output.println("USERNAME: " + this.bean.getUsername());
        }


        if (this.bean.getPassword() == null){
            Output.pageMessage("LOGIN","Password", false);
            line = Input.line();

            if (this.exit(line)){
                return BACK_CALL.ordinal();
            }

            if (this.bean.setPassword(line) == -1){
                Output.pageMessage("LOGIN", "Password must contains at least 8 character", true);
                return this.signUp(false);
            }
            Output.println(this.bean.getPassword());


        }

        if (this.bean.getName() == null){
            Output.pageMessage("LOGIN","Name", false);
            line = Input.line();

            if(this.exit(line)){
                return BACK_CALL.ordinal();
            }

            if (this.bean.setName(line) == -1){
                Output.pageMessage("LOGIN", "Name must contains at least a letter", true);
                return this.signUp(false);
            }
        }

        if (this.bean.getSurname() == null){
            Output.pageMessage("LOGIN","Surname", false);
            line = Input.line();
            if(this.exit(line)){
                return BACK_CALL.ordinal();
            }

            if (this.bean.setSurname(line) == -1){
                Output.pageMessage("LOGIN", "Surname must contains at least a letter", true);
                return this.signUp(false);
            }
        }

        if (this.bean.getDateOfBirth() == null){
            Output.pageMessage("LOGIN", "Date of Birth", false);
            line = Input.line();

            if (this.exit(line)){
                return BACK_CALL.ordinal();
            }

            try {
                this.bean.setDateOfBirth(line);
            } catch (InvalidDateException e) {
                Output.pageMessage("LOGIN", "Date error: " +line + "\n" +e, true);
                return this.signUp(false);
            }
        }

        if (this.bean.getCityOfBirth() == null){
            Output.pageMessage("LOGIN", "City Of Birth", false);
            line = Input.line();

            if (this.exit(line)){
                return BACK_CALL.ordinal();
            }

            if (this.bean.setCityOfBirth(line) == -1){
                Output.pageMessage("LOGIN", "City contains at least a letter", true);
                return BACK_CALL.ordinal();
            }
        }


        Output.pageMessage("LOGIN", "Register as {" + Macros.EMPLOYER.name() + "}? y to accept", false);
        line = Input.line();

        if (this.exit(line)){
            return BACK_CALL.ordinal();
        }

        if (line.equalsIgnoreCase("y") || line.equalsIgnoreCase("yes")){
            type = Macros.EMPLOYER.ordinal();
        }


        this.bean.setType(type);


        try {
            this.graphic.signUp(this.bean.getUsername(), this.bean.getPassword(), this.bean.getName(), this.bean.getSurname(), this.bean.getDateOfBirth(), bean.getCityOfBirth(), this.bean.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return Macros.ERROR.ordinal();
        }

        return Macros.SIGN_UP_SUCCESS.ordinal();
    }

    private int signIn(boolean first){
        String username;
        String password;

        if(first) {
            Output.pageMessage("LOGIN","Starting SING IN procedure type #back to #exit", true);
        }
        Output.pageMessage("LOGIN","Username", false);
        username = Input.line();
        if(exit(username)){
            return BACK_CALL.ordinal();
        }
        if (this.bean.setUsername(username) == -1){
            Output.pageMessage("LOGIN", "Username must non contains a letter", true);
            return this.signIn(false);
        }

        Output.pageMessage("LOGIN","Password", false);
        password = Input.line();
        if(exit(password)){
            return BACK_CALL.ordinal();
        }

        if (this.bean.setPassword(password) == -1){
            Output.pageMessage("LOGIN", "Password must have at least 8 characters", true);
            return this.signIn(false);
        }

        boolean store = false;
        Output.pageMessage("LOGIN", "Want to store configuration? y to approve", false);
        String line = Input.line();
        if (line.equalsIgnoreCase("yes") || line.equalsIgnoreCase("y")){
            store = true;
        }

        try {
            this.graphic.signIn(this.bean.getUsername(), this.bean.getPassword(), store);
        } catch (LoginException e) {
            e.printStackTrace();
            return Macros.ERROR.ordinal();
        }

        return Macros.SIGN_IN_SUCCESS.ordinal();
    }



    private boolean exit(String s){
        boolean ret = false;
        if (s.equalsIgnoreCase("#BACK") || s.equalsIgnoreCase("#EXIT")){
            ret = true;
            this.bean.refresh();
            Output.pageMessage("LOGIN", "EXITING PROCEDURE", true);
        }
        return ret;
    }


}
