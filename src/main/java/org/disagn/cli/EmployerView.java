package org.disagn.cli;



import org.disagn.cli.io.Input;
import org.disagn.cli.io.Output;
import org.disagn.controllers.Login;
import org.disagn.models.users.User;
import org.disagn.stateMachines.JobAbstractState;
import org.disagn.stateMachines.cliMachine.AccountStateCli;
import org.disagn.stateMachines.cliMachine.CliMachine;
import org.disagn.stateMachines.cliMachine.ViewSchedulingStateCli;
import org.disagn.stateMachines.cliMachine.employer.ManageApplicantsStateCli;
import org.disagn.stateMachines.cliMachine.employer.PublishShiftStateCli;

import java.io.FileNotFoundException;
import java.util.List;

public class EmployerView extends JobAbstractState {

    private List<String> commandList;
    private final String pageMsg;
    private final User user;
    public EmployerView(User user) {
        this.user = user;
        pageMsg = "@EMPLOYER{" + user.getUsername() + "}";
        try {
            CommandLoader commandLoader = new CommandLoader("EMPLOYER");
            this.commandList = commandLoader.getCommandList();
        } catch (FileNotFoundException e) {
            Output.pageMessage(this.pageMsg, e.getMessage(), true);
        }
    }
    /**
     * Function common to all view that start the view
     */
    @Override
    public void entry(CliMachine stateMachine) {

        String page = "HOME" + pageMsg;
        while (true) {
            Output.pageMessage(page, "type help to command list", false);
            String line = Input.getCmd(this.commandList);
            JobAbstractState newState = null;
            switch (line) {
                case "ACCOUNT" -> newState = new AccountStateCli(this.user);


                case "PUBLISH_SHIFT" -> newState = new PublishShiftStateCli(this.user);

                case "VIEW_SCHEDULING" -> newState = new ViewSchedulingStateCli(this.user);

                case "MANAGE_APPLICANTS" -> newState = new ManageApplicantsStateCli(this.user);

                case "HELP" -> Output.printList("HELP" + pageMsg, this.commandList);
                case "LOG_OUT" -> {
                    Login.LogOut();
                    return;
                }
                case "EXIT" -> {
                    return;
                }
                case ""->{}

                case "INVALID_NUMBER"-> Output.pageMessage(page, line + " VALUES ALLOWED 0.." + (this.commandList.size() - 1), true);
                default -> {
                    Output.pageMessage(page, "PLEASE TYPE THIS COMMAND", true);
                    Output.printList("HELP" + pageMsg, this.commandList);
                }
            }

            if (newState != null){
                stateMachine.changeState(newState);
            }
        }


    }
}
