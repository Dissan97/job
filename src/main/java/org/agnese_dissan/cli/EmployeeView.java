package org.agnese_dissan.cli;

import org.agnese_dissan.cli.cliMachine.CliMachine;
import org.agnese_dissan.cli.cliMachine.CliStateMachine;
import org.agnese_dissan.cli.cliMachine.CliStates;
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
    private final List<String> commandList = new ArrayList<>();
    private final CliStateMachine stateMachine;
    private final String pageMsg;

    public EmployeeView(User user) {
        try {
            employee = new Employee(user);
        } catch (InvalidDateException e) {
            e.printStackTrace();
        }

        this.stateMachine = new CliMachine(user);
        this.pageMsg = "@EMPLOYEE{"+user.getUsername()+"}";
        commandList.add("ACCOUNT");
        commandList.add("APPLY_SHIFT");
        commandList.add("VIEW_APPLIES");
        commandList.add("DEMISE_SHIFT");
        commandList.add("HELP");
        commandList.add("EXIT");
    }

    @Override
    public void startUi() {
        String page = "HOME" + this.pageMsg;
        Output.pageMessage(page, "Type help to get list", false);
        while (true) {

            String line = Input.getCmd(this.commandList);

            switch (line) {
                case "ACCOUNT" ->
                    this.stateMachine.nextState(CliStates.ACCOUNT);
                case "APPLY_SHIFT" ->
                    this.stateMachine.nextState(CliStates.APPLY_SHIFT);
                case "VIEW_APPLIES" ->
                    this.stateMachine.nextState(CliStates.VIEW_APPLIES);
                case "DEMISE_SHIFT" ->
                    this.stateMachine.nextState(CliStates.DEMISE_SHIFT);
                case "HELP" ->
                    Output.printList("HOME", this.commandList);
                case "EXIT" -> {
                    Output.pageMessage("HOME", employee.getUsername() + " Bye...", true);
                    System.exit(0);
                }
                case "" -> {}
                default -> {
                    Output.pageMessage("HOME", "PLEASE TYPE THIS COMMAND", true);
                    Output.printList("HOME", this.commandList);
                }
            }

        }

    }
}
