package org.disagn.cli;



import org.disagn.cli.io.Input;
import org.disagn.cli.io.Printer;
import org.disagn.controllers.Login;
import org.disagn.decorator.PageContainer;
import org.disagn.exceptions.NoInterfaceException;
import org.disagn.models.users.User;
import org.disagn.states.JobAbstractState;
import org.disagn.states.commandline.AccountStateCli;
import org.disagn.states.commandline.CliMachine;
import org.disagn.states.commandline.ViewSchedulingStateCli;
import org.disagn.states.commandline.employer.ViewApplicantsStateCli;
import org.disagn.states.commandline.employer.PublishShiftStateCli;

import java.io.FileNotFoundException;
import java.util.List;

public class EmployerView extends JobAbstractState {

    private List<String> commandList;
    private final String page;
    private final User user;
    public EmployerView(User user) {
        this.user = user;
        PageContainer container = new PageContainer("HOME", this.user);
        this.page = container.display();
        try {
            CommandLoader commandLoader = new CommandLoader("EMPLOYER");
            this.commandList = commandLoader.getCommandList();
        } catch (FileNotFoundException e) {
            Printer.pageMessage(this.page, e.getMessage(), true);
        }
    }
    /**
     * Function common to all view that start the view
     */
    @Override
    public void entry(CliMachine stateMachine) {

        while (true) {
            Printer.pageMessage(this.page, CommandLoader.HELP, false);
            String line = Input.getCmd(this.commandList);
            JobAbstractState newState = null;
            switch (line) {
                case "ACCOUNT" -> newState = new AccountStateCli(this.user);

                case "PUBLISH_SHIFT" -> newState = new PublishShiftStateCli(this.user);

                case "VIEW_SCHEDULING" -> newState = new ViewSchedulingStateCli(this.user);

                case "MANAGE_APPLICANTS" -> newState = new ViewApplicantsStateCli(this.user);

                case "HELP" -> Printer.printList(page + ": HELP", this.commandList);
                case "LOG_OUT" -> {
                    try {
                        Login.logOut();
                    } catch (NoInterfaceException e) {
                        Printer.print(e.getMessage());
                    }
                    return;
                }
                case "EXIT" -> {
                    Printer.pageMessage(page, this.user.getUsername() + " Bye...", true);
                    return;
                }
                case ""->{
                    //Do no op
                }

                case "INVALID_NUMBER"-> Printer.pageMessage(this.page, line + " VALUES ALLOWED 0.." + (this.commandList.size() - 1), true);
                default -> {
                    Printer.pageMessage(this.page, "PLEASE TYPE THIS COMMAND", true);
                    Printer.printList("HELP" + page, this.commandList);
                }
            }

            if (newState != null){
                stateMachine.changeState(newState);
            }
        }


    }
}
