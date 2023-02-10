package org.disagn.cli;


import org.disagn.cli.io.Input;
import org.disagn.cli.io.Printer;
import org.disagn.controllers.Login;
import org.disagn.decorator.PageContainer;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.exceptions.NoInterfaceException;
import org.disagn.models.users.Employee;
import org.disagn.models.users.User;
import org.disagn.states.JobAbstractState;
import org.disagn.states.commandline.AccountStateCli;
import org.disagn.states.commandline.CliMachine;
import org.disagn.states.commandline.employee.ApplyShift;
import org.disagn.states.commandline.employee.DemiseManagerStateCli;
import org.disagn.states.commandline.employee.ViewAppliesStateCli;

import java.io.FileNotFoundException;
import java.util.List;

public class EmployeeView extends JobAbstractState {


    private Employee employee;
    private List<String> commandList;
    private final String page;

    public EmployeeView(User user) {
        try {
            employee = new Employee(user);
        } catch (InvalidDateException e) {
            Printer.exception(e);
        }

        PageContainer container = new PageContainer("HOME", user);
        this.page = container.display();
        try {
            CommandLoader commandLoader = new CommandLoader("EMPLOYEE");
            this.commandList = commandLoader.getCommandList();
        } catch (FileNotFoundException e) {
            Printer.pageMessage(this.page, e.getMessage(), true);
        }
    }

    @Override
    public void entry(CliMachine stateMachine) {


        while (true) {
            Printer.pageMessage(page, CommandLoader.HELP, false);
            String line = Input.getCmd(this.commandList);
            JobAbstractState newState = null;
            switch (line) {
                case "ACCOUNT" -> newState = new AccountStateCli(this.employee);
                case "APPLY_SHIFT" -> newState = new ApplyShift(this.employee);
                case "VIEW_APPLIES" -> newState = new ViewAppliesStateCli(this.employee);
                case "MANAGE_DEMISE" -> newState = new DemiseManagerStateCli(this.employee);
                case "HELP" -> Printer.printList(this.page, this.commandList);
                case "LOG_OUT" ->  {
                    try {
                        Login.logOut();
                    } catch (NoInterfaceException e) {
                        Printer.print(e.getMessage());
                    }
                    return;
                }
                case "EXIT" -> {
                    Printer.pageMessage(this.page, employee.getUsername() + " Bye...", true);
                    return;
                }
                case "" -> {
                    //no op
                }
                default -> {
                    Printer.pageMessage(this.page, "PLEASE TYPE THIS COMMAND", true);
                    Printer.printList(this.page, this.commandList);
                }

            }

            if (newState != null){
                stateMachine.changeState(newState);
            }

        }

    }
}
