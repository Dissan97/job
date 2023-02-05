package org.disagn.cli;

import org.disagn.stateMachines.cliMachine.CliMachine;
import org.disagn.stateMachines.JobStateMachine;
import org.disagn.stateMachines.JobStates;
import org.disagn.cli.io.Input;
import org.disagn.cli.io.Output;
import org.disagn.controllers.Login;
import org.disagn.interfaces.JobView;
import org.disagn.models.users.User;

import java.io.FileNotFoundException;
import java.util.List;

public class EmployerView implements JobView {

    private List<String> commandList;
    private final String pageMsg;
    private final JobStateMachine stateMachine;

    public EmployerView(User user) {
        pageMsg = "@EMPLOYER{" + user.getUsername() + "}";
        this.stateMachine = new CliMachine(user);
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
    public void startUi() {
        String page = "HOME" + pageMsg;
        while (true) {
            Output.pageMessage(page, "type help to command list", true);
            String line = Input.getCmd(this.commandList);
            switch (line) {
                case "ACCOUNT":
                    this.stateMachine.nextState(JobStates.ACCOUNT);
                    break;
                case "PUBLISH_SHIFT":
                    this.stateMachine.nextState(JobStates.PUBLISH_SHIFT);
                    break;
                case "VIEW_SCHEDULING":
                    this.stateMachine.nextState(JobStates.VIEW_SCHEDULING);
                    break;
                case "MANAGE_APPLICANTS":
                    this.stateMachine.nextState(JobStates.MANAGE_APPLICANTS);
                    break;
                case "HELP":
                    Output.printList("HELP" + pageMsg, this.commandList);
                    break;
                case "LOG_OUT":
                    Login.LogOut();
                    return;
                case "EXIT":
                        return;
                case "":
                    break;
                case "INVALID_NUMBER":
                    Output.pageMessage(page, line + " VALUES ALLOWED 0.." + (this.commandList.size() - 1), true);
                    break;
                default:
                    Output.pageMessage(page, "PLEASE TYPE THIS COMMAND", true);
                    Output.printList("HELP" + pageMsg, this.commandList);
                    break;
            }
        }
    }
}
