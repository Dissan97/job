package org.agnese_dissan.cli;

import org.agnese_dissan.beans.AccountBean;
import org.agnese_dissan.cli.io.Input;
import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.interfaces.JobView;
import org.agnese_dissan.models.users.Employee;
import org.agnese_dissan.models.users.User;

import java.util.ArrayList;
import java.util.List;

public class EmployeeView implements JobView {


    private Employee employee;
    private final AccountBean accountBean;
    private final List<String> commandList = new ArrayList<>();

    public EmployeeView(User user) {
        try {
            employee = new Employee(user);
        } catch (InvalidDateException e) {
            e.printStackTrace();
        }

        accountBean = new AccountBean(user);

        commandList.add("ACCOUNT");
        commandList.add("APPLY_SHIFT");
        commandList.add("VIEW_APPLIES");
        commandList.add("CANCEL_SHIFT");
        commandList.add("HELP");
        commandList.add("EXIT");
    }

    @Override
    public void startUi() {
        Output.println("[HOME{("+ employee.getUsername()+")}]");
        Output.pageMessage("[HOME]", "Type help to get list", true);
        while (true) {

            Output.print("HOME:-> ");
            String line = Input.getCmd(this.commandList);

            switch (line) {
                case "ACCOUNT" ->
                    this.getAccountInfo();
                case "APPLY_SHIFT" ->
                    applyShift();
                case "VIEW_APPLIES" ->
                    viewApplies();
                case "CANCEL_SHIFT" ->
                    this.cancelShift();
                case "HELP" ->
                    Output.getCommandList("HOME", this.commandList);
                case "EXIT" -> {
                    Output.pageMessage("HOME", employee.getUsername() + " Bye...", true);
                    System.exit(0);
                }
                case "" -> {}
                default -> {
                    Output.pageMessage("HOME", "PLEASE TYPE THIS COMMAND", true);
                    Output.getCommandList("HOME", this.commandList);
                }
            }

        }

    }

    private void getAccountInfo(){
        String page = "HOME{" + accountBean.getUsername() + "}";
        Output.pageMessage(page, "NAME: " + accountBean.getName() , true);
        Output.pageMessage(page, "SURNAME: " + accountBean.getSurname(), true);
        Output.pageMessage(page, "DATE OF BIRTH: " + accountBean.getDateOfBirth(), true);
        Output.pageMessage(page, "CITY OF BIRTH: " + accountBean.getCityOfBirth(), true);
    }

    private void applyShift() {

    }
    private void viewApplies() {
    }

    private void cancelShift() {
    }


}
