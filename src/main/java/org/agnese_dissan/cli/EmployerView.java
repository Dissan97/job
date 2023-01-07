package org.agnese_dissan.cli;

import org.agnese_dissan.beans.AccountBean;
import org.agnese_dissan.beans.ShiftBean;
import org.agnese_dissan.cli.io.Input;
import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.graphicControllers.ShiftPublisherGraphic;
import org.agnese_dissan.interfaces.JobView;
import org.agnese_dissan.interfaces.Refresh;
import org.agnese_dissan.models.users.Employer;
import org.agnese_dissan.models.users.User;

import java.util.ArrayList;
import java.util.List;

import static org.agnese_dissan.Macros.BACK_CALL;

public class EmployerView implements JobView {

    private Employer employer;
    private final AccountBean accountBean;
    private final List<String> commandList = new ArrayList<>();

    private final String pageMsg;

    public EmployerView(User user) {

        this.accountBean = new AccountBean(user);

        try {
            employer = new Employer(user);
        } catch (InvalidDateException e) {
            e.printStackTrace();
        }
        pageMsg = "@EMPLOYER{" + this.accountBean.getUsername() + "}";
        commandList.add("ACCOUNT");
        commandList.add("PUBLISH_SHIFT");
        commandList.add("VIEW_CANDIDATES");
        commandList.add("HANDLE_CANDIDATE");
        commandList.add("HELP");
        commandList.add("EXIT");
    }

    @Override
    public void startUi() {
        String page = "HOME" + pageMsg;
        Output.pageMessage(page, "TYPE HELP TO GET COMMAND LIST", true);
        while (true) {

            Output.pageMessage(page,"",false);
            String line = Input.getCmd(this.commandList);

            switch (line) {
                case "ACCOUNT":
                    this.getAccountInfo();
                    break;
                case "PUBLISH_SHIFT":
                    publishShift();

                    break;
                case "VIEW_CANDIDATES":

                    break;
                case "HANDLE_CANDIDATE":

                    break;
                case "HELP":
                    Output.getCommandList("HELP" + pageMsg, this.commandList);
                    break;
                case "EXIT":
                    Output.pageMessage("HOME", this.employer.getUsername() + " Bye...", true);
                    System.exit(0);
                case "":

                    break;
                case "INVALID_NUMBER":
                    Output.pageMessage(page, line + " VALUES ALLOWED 0.." + (this.commandList.size() - 1), true);
                    break;
                default:
                    Output.pageMessage(page, "PLEASE TYPE THIS COMMAND", true);
                    Output.getCommandList("HELP" + pageMsg, this.commandList);
                    break;
            }

        }
    }

    private int publishShift() {
        ShiftBean bean = new ShiftBean(this.employer);
        ShiftPublisherGraphic shiftPublisherGraphic = bean.getGraphic();
        String line;
        String page = "PUBLISH_SHIFT" + this.pageMsg;

        Output.pageMessage(page, "PRESS #BACK TO #EXIT", true);

        if (bean.getName() == null) {
            Output.pageMessage(page, "Job name", false);
            line = Input.line();

            if (this.exit(line, bean)) {
                return BACK_CALL.ordinal();
            }

            if (bean.setName(line) == -1){
                Output.pageMessage(page, "Job name cannot be empty", true);
                return this.publishShift();
            }

        }

        bean.setEmployer(this.employer);

        if (bean.getJobPlace() == null){
            Output.pageMessage(page, "Job place insert address", false);
            line = Input.line();

            if (this.exit(line, bean)) {
                return BACK_CALL.ordinal();
            }
            if (bean.setJobPlace(line) == -1){
                Output.pageMessage(page, "Job place cannot be empty", true);
                return this.publishShift();
            }
        }

        if (bean.getDateTime() == null){
            Output.pageMessage(page, "Insert day", false);
            line = Input.line();

            if (this.exit(line, bean)) {
                return BACK_CALL.ordinal();
            }

            String dateTime = line;
            Output.pageMessage(page, "Insert Time", false);
            line = Input.line();

            if (this.exit(line, bean)){
                return BACK_CALL.ordinal();
            }

            dateTime += " " + line;
            try {
                bean.setDateTime(dateTime);
            } catch (InvalidDateException e) {
                Output.pageMessage(page, e.getMessage(), true);
                Output.pageMessage(page, "INSERTED {" + line + "}", true);
                return this.publishShift();
            }
        }

        if (bean.getDescription() == null){
            Output.pageMessage(page, "Insert description", false);
            line = Input.line();
            bean.setDescription(line);

            if (this.exit(line, bean)){
                return BACK_CALL.ordinal();
            }
        }

        shiftPublisherGraphic.publishShift();

        return 0;
    }
    private void getAccountInfo () {
        String page = "ACCOUNT" + this.pageMsg;
        Output.pageMessage(page, "NAME: " + accountBean.getName(), true);
        Output.pageMessage(page, "SURNAME: " + accountBean.getSurname(), true);
        Output.pageMessage(page, "DATE OF BIRTH: " + accountBean.getDateOfBirth(), true);
        Output.pageMessage(page, "CITY OF BIRTH: " + accountBean.getCityOfBirth(), true);
    }
    private boolean exit(String s, Refresh bean) {

        if (s.equalsIgnoreCase("#BACK") || s.equalsIgnoreCase("#EXIT")) {
            Output.println("Entered Here");
            bean.refresh();
        }
        return false;
    }
}
