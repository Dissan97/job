package org.disagn.cli;


import org.disagn.Macros;
import org.disagn.cli.io.Input;
import org.disagn.cli.io.Printer;
import org.disagn.decorator.PageContainer;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.exceptions.NoInterfaceException;
import org.disagn.exceptions.UserLoginFailedException;
import org.disagn.graphics.LoginGraphic;
import org.disagn.interfaces.JobView;
import org.disagn.models.time.JobDate;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static org.disagn.Macros.*;

public class LoginView implements JobView{

    private final LoginGraphic controller;
    private List<String> commandList;

    private String username = null;
    private String password = null;
    private String  name = null;
    private String surname = null;
    private String dateOfBirth = null;
    private String cityOfBirth = null;

    private static final String LOGIN = "LOGIN";

    public LoginView() {
        try {
            CommandLoader commandLoader = new CommandLoader(LOGIN);
            this.commandList = commandLoader.getCommandList();
        } catch (FileNotFoundException e) {
            Printer.pageMessage(LOGIN, e.getMessage(), true);
        }
        this.controller = new LoginGraphic();
    }

    @Override
    public void startUi() {

        String line;
        PageContainer container = new PageContainer(LOGIN);
        String page = container.display();
        while (true) {

            Printer.pageMessage(page, CommandLoader.HELP, false);
            line = Input.getCmd(this.commandList);

            Macros ret = Macros.START;
            switch (line) {
                case "SIGN_IN" -> {
                    this.refresh();
                    Printer.pageMessage(page, "Starting sign up procedure", true);
                    ret = this.signIn();
                }
                case "SIGN_UP" -> {
                    this.refresh();
                    Printer.pageMessage(page, "Starting sign up procedure", true);
                    ret = this.signUp();
                }
                case "HELP" -> Printer.printList(page, this.commandList);
                case "EXIT" -> {
                    Printer.pageMessage(LOGIN, "bye...", true);
                    return;
                }
                case "" ->{
                    //DO NO OP CONTINUE
                }
                case "INVALID_NUMBER" ->
                    Printer.pageMessage(page, line + " VALUES ALLOWED 0.." + (this.commandList.size() - 1), true);

                default ->
                    Printer.pageMessage(page, "{" + line + "} COMMAND_NOT_FOUND TYPE HELP FOR COMMAND LIST", true);

            }

            if (ret == Macros.SIGN_IN_SUCCESS){
                return;
            } else if (ret == Macros.SIGN_IN_FAILED) {
                Printer.pageMessage(page, "There's some error", true);
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

        setName(page);
        setSurname(page);
        setDateOfBirth(page);
        setCityOfBirth(page);
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
            Printer.exception(e);
            return Macros.ERROR;
        }finally {
            this.refresh();
        }


        return SIGN_UP_SUCCESS;
    }


    private void setName(String page){
        String line;
        if (this.name == null){
            line = Input.getInfo(page, "name");
            if (exit(line)){
                startUi();
            }
            if (this.controller.isGood(line, false)){
                this.name = line;
            }else {
                Printer.pageMessage(page,"name cannot be blank", true);
                signUp();
            }
        }
    }
    private void setSurname(String page) {
        String line;
        if (this.surname == null){
            line = Input.getInfo(page, "surname");
            if (exit(line)){
                startUi();
            }
            if (this.controller.isGood(line, false)){
                this.surname = line;
            }else {
                Printer.pageMessage(page,"surname cannot be blank", true);
                signUp();
            }
        }
    }

    private void setDateOfBirth(String page) {
        String line;
        if (this.dateOfBirth == null){
            line = Input.getInfo(page, "date of birth");
            if (exit(line)){
                startUi();
            }
            if (this.controller.isGood(line, false)){
                try {
                    JobDate date = new JobDate(line);
                    this.dateOfBirth = date.toString();
                } catch (InvalidDateException e) {
                    Printer.pageMessage(page, e.getMessage(), true);
                    signUp();
                }
            }else {
                Printer.pageMessage(page,"date cannot be blank", true);
                signUp();
            }
        }
    }

    private void setCityOfBirth(String page) {
        String line;
        if (this.cityOfBirth == null){
            line = Input.getInfo(page, "city of birth");
            if (exit(line)){
                startUi();
            }
            if (this.controller.isGood(line, false)){
                this.cityOfBirth = line;
            }else {
                Printer.pageMessage(page,"city of birth cannot be blank", true);
                signUp();
            }
        }

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
            Printer.pageMessage(page, e.getMessage(), true);
            return SIGN_IN_FAILED;
        } catch (NoInterfaceException e) {
            Printer.print(e.getMessage());
        } catch (FileNotFoundException | SQLException e) {
            Printer.exception(e);
        }
        return SIGN_IN_SUCCESS;
    }



    private boolean exit(String s){
        boolean ret = false;
        if (s.equalsIgnoreCase("#BACK") || s.equalsIgnoreCase("#EXIT")){
            ret = true;
            this.refresh();
            Printer.pageMessage(LOGIN, "EXITING PROCEDURE", true);
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
                Printer.pageMessage(page,"Cannot insert blank username", true);
                return BACK_CALL;
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
                Printer.pageMessage(page,"error password must have at least 8 character", true);
                return BACK_CALL;
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
