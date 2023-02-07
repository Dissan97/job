package org.disagn.cli;


import org.disagn.cli.io.Input;
import org.disagn.cli.io.Output;
import org.disagn.controllers.Login;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.models.users.Employee;
import org.disagn.models.users.User;
import org.disagn.stateMachines.JobAbstractState;
import org.disagn.stateMachines.cliMachine.AccountStateCli;
import org.disagn.stateMachines.cliMachine.CliMachine;
import org.disagn.stateMachines.cliMachine.employee.ApplyShift;
import org.disagn.stateMachines.cliMachine.employee.DemiseManagerStateCli;
import org.disagn.stateMachines.cliMachine.employee.ViewAppliesStateCli;

import java.io.FileNotFoundException;
import java.util.List;

public class EmployeeView extends JobAbstractState {


    private Employee employee;
    private List<String> commandList;
    private final String pageMsg;

    public EmployeeView(User user) {
        try {
            employee = new Employee(user);
        } catch (InvalidDateException e) {
            e.printStackTrace();
        }

        this.pageMsg = "@EMPLOYEE{"+user.getUsername()+"}";
        try {
            CommandLoader commandLoader = new CommandLoader("EMPLOYEE");
            this.commandList = commandLoader.getCommandList();
        } catch (FileNotFoundException e) {
            Output.pageMessage(this.pageMsg, e.getMessage(), true);
        }
    }

    @Override
    public void entry(CliMachine stateMachine) {
        String page = "HOME" + this.pageMsg;

        while (true) {
            Output.pageMessage(page, "Type help to get list", false);
            String line = Input.getCmd(this.commandList);
            JobAbstractState newState = null;
            switch (line) {
                case "ACCOUNT" -> newState = new AccountStateCli(this.employee);
                case "APPLY_SHIFT" -> newState = new ApplyShift(this.employee);
                case "VIEW_APPLIES" -> newState = new ViewAppliesStateCli(this.employee);
                case "MANAGE_DEMISE" -> newState = new DemiseManagerStateCli(this.employee);
                case "HELP" -> Output.printList("HOME", this.commandList);
                case "LOGOUT" ->  {
                    this.exit(stateMachine);
                    Login.LogOut();
                }
                case "EXIT" -> {
                    Output.pageMessage("HOME", employee.getUsername() + " Bye...", true);
                    this.exit(stateMachine);
                    return;
                }
                case "" -> {
                    //no op
                }
                default -> {
                    Output.pageMessage("HOME", "PLEASE TYPE THIS COMMAND", true);
                    Output.printList("HOME", this.commandList);
                }

            }

            if (newState != null){
                stateMachine.changeState(newState);
            }

        }

    }
}