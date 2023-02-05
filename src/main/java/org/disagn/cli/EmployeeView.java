package org.disagn.cli;

import org.disagn.controllers.Login;
import org.disagn.stateMachines.cliMachine.CliMachine;
import org.disagn.stateMachines.JobStateMachine;
import org.disagn.stateMachines.JobStates;
import org.disagn.cli.io.Input;
import org.disagn.cli.io.Output;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.interfaces.JobView;
import org.disagn.models.users.Employee;
import org.disagn.models.users.User;

import java.io.FileNotFoundException;
import java.util.List;

public class EmployeeView implements JobView {


    private Employee employee;
    private List<String> commandList;
    private final JobStateMachine stateMachine;
    private final String pageMsg;

    public EmployeeView(User user) {
        try {
            employee = new Employee(user);
        } catch (InvalidDateException e) {
            e.printStackTrace();
        }

        this.stateMachine = new CliMachine(user);
        this.pageMsg = "@EMPLOYEE{"+user.getUsername()+"}";
        try {
            CommandLoader commandLoader = new CommandLoader("EMPLOYEE");
            this.commandList = commandLoader.getCommandList();
        } catch (FileNotFoundException e) {
            Output.pageMessage(this.pageMsg, e.getMessage(), true);
        }
    }

    @Override
    public void startUi() {
        String page = "HOME" + this.pageMsg;

        while (true) {
            Output.pageMessage(page, "Type help to get list", false);
            String line = Input.getCmd(this.commandList);
            switch (line) {
                case "ACCOUNT" ->
                    this.stateMachine.nextState(JobStates.ACCOUNT);
                case "APPLY_SHIFT" ->
                    this.stateMachine.nextState(JobStates.APPLY_SHIFT);
                case "VIEW_APPLIES" ->
                    this.stateMachine.nextState(JobStates.VIEW_APPLIES);
                case "MANAGE_DEMISE" ->
                    this.stateMachine.nextState(JobStates.MANAGE_DEMISE);
                case "HELP" ->
                    Output.printList("HOME", this.commandList);
                case "LOGOUT" ->  Login.LogOut();

                case "EXIT" -> {
                    Output.pageMessage("HOME", employee.getUsername() + " Bye...", true);
                    System.exit(0);
                }
                case "" -> {
                    //no op
                }
                default -> {
                    Output.pageMessage("HOME", "PLEASE TYPE THIS COMMAND", true);
                    Output.printList("HOME", this.commandList);
                }
            }

        }

    }
}
