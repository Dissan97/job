package org.agnese_dissan.cli;


import org.agnese_dissan.beans.LoginBean;
import org.agnese_dissan.cli.io.Input;
import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.exceptions.LoginException;
import org.agnese_dissan.graphicControllers.LoginGraphic;
import org.agnese_dissan.interfaces.JobView;

import java.util.ArrayList;
import java.util.List;

public class LoginView implements JobView{


    private final LoginBean bean;
    private final LoginGraphic graphic;
    private final List<String> commandList = new ArrayList<>();

    public LoginView() {
        commandList.add("SING_IN");
        commandList.add("SIGN_UP");
        commandList.add("HELP");
        commandList.add("EXIT");
        this.bean = new LoginBean();
        this.graphic = this.bean.getGraphic();
    }

    @Override
    public void startUi() {

        Output.println("[LOGIN PAGE]");
        Output.pageMessage("LOGIN", "Type help to get list", true);
        String line;

        while (true) {
            Output.print("[LOGIN]:-> ");
            line = Input.getCmd();

            int ret = 1;
            switch (line) {
                case "SIGN_IN" -> ret = this.signIn(true);
                case "SIGN_UP" -> ret = this.signUp(true);
                case "HELP" -> Output.getCommandList("LOGIN", this.commandList);
                case "EXIT" -> {
                    System.out.println("LOGIN: Bye...");
                    System.exit(0);
                }
            }

            if (ret == 0){
                return;
            } else if (ret == -1) {
                Output.pageMessage("LOGIN", "There's some error", true);
            }
        }


    }

    private int signUp(boolean first) {
        String username, password, name, surname;

        if (!first) {
            Output.pageMessage("LOGIN", "Starting SING UP procedure type back to exit", true);
        }
        Output.pageMessage("LOGIN","Username", false);
        username = Input.line();
        if(this.exit(username)){
            return 1;
        }

        if (this.bean.setUsername(username) == -1){
            Output.pageMessage("LOGIN", "Username must contains a letter", true);
            return this.signUp(false);
        }

        Output.pageMessage("LOGIN","Password", false);
        password = Input.line();
        if (this.exit(password)){
            return 1;
        }

        if (this.bean.setPassword(password) == -1){
            Output.pageMessage("LOGIN", "Password must contains at least 8 character", true);
            return this.signUp(false);
        }


        Output.pageMessage("LOGIN","Name", false);
        name = Input.line();
        if(this.exit(name)){
            return 1;
        }

        if (this.bean.setName(name) == -1){
            Output.pageMessage("LOGIN", "Name must non contains a letter", true);
            return this.signUp(false);
        }
        Output.pageMessage("LOGIN","Surname", false);
        surname = Input.line();
        if(this.exit(surname)){
            return 1;
        }

        if (this.bean.setSurname(surname) == -1){
            Output.pageMessage("LOGIN", "Surname must non contains a letter", true);
            return this.signUp(false);
        }

        try {
            this.graphic.signUp(this.bean.getUsername(), this.bean.getPassword(), this.bean.getName(), this.bean.getSurname(), "1900-01-01", "Rome");
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        return 0;
    }

    private int signIn(boolean first){
        String username;
        String password;

        if(first) {
            Output.pageMessage("LOGIN","Starting SING IN procedure type back to exit", true);
        }
        Output.pageMessage("LOGIN","Username", false);
        username = Input.line();
        if(exit(username)){
            return 1;
        }
        if (this.bean.setUsername(username) == -1){
            Output.pageMessage("LOGIN", "Username must non contains a letter", true);
            return this.signIn(false);
        }

        Output.pageMessage("LOGIN","Password", false);
        password = Input.line();
        if(exit(password)){
            return 1;
        }

        if (this.bean.setPassword(password) == -1){
            Output.pageMessage("LOGIN", "Password must have at least 8 characters", true);
            return this.signIn(false);
        }

        try {
            this.graphic.signIn(this.bean.getUsername(), this.bean.getPassword());
        } catch (LoginException e) {
            e.printStackTrace();
            return -1;
        }

        return 0;
    }



    private boolean exit(String s){
        return s.equalsIgnoreCase("BACK") || s.equalsIgnoreCase("EXIT");
    }


}
