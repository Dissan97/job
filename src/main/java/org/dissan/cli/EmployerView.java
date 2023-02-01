package org.dissan.cli;

import org.dissan.stateMachines.cliMachine.CliMachine;
import org.dissan.stateMachines.JobStateMachine;
import org.dissan.stateMachines.JobStates;
import org.dissan.cli.io.Input;
import org.dissan.cli.io.Output;
import org.dissan.controllers.Login;
import org.dissan.interfaces.JobView;
import org.dissan.models.users.User;

import java.util.ArrayList;
import java.util.List;

public class EmployerView implements JobView {

    private final List<String> commandList = new ArrayList<>();
    private final String pageMsg;
    private final JobStateMachine stateMachine;

    public EmployerView(User user) {
        pageMsg = "@EMPLOYER{" + user.getUsername() + "}";
        commandList.add("ACCOUNT");
        commandList.add("PUBLISH_SHIFT");
        commandList.add("VIEW_SCHEDULING");
        commandList.add("VIEW_CANDIDATES");
        commandList.add("HANDLE_CANDIDATE");
        commandList.add("HELP");
        commandList.add("LOG_OUT");
        commandList.add("EXIT");
        this.stateMachine = new CliMachine(user);
    }
    /**
     * Function common to all view that start the view
     */
    @Override
    public void startUi() {
        String page = "HOME" + pageMsg;
        Output.pageMessage(page, "type help to command list", true);
        while (true) {
            Output.pageMessage(page,"",false);
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
                case "VIEW_CANDIDATES":
                    this.stateMachine.nextState(JobStates.VIEW_CANDIDATES);
                    break;
                case "HANDLE_CANDIDATE":
                    this.stateMachine.nextState(JobStates.HANDLE_CANDIDATE);
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
