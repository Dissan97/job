package org.dissan.cli;

import org.dissan.stateMachines.cliMachine.CliMachine;
import org.dissan.stateMachines.JobStateMachine;
import org.dissan.stateMachines.JobStates;
import org.dissan.cli.io.Input;
import org.dissan.cli.io.Output;
import org.dissan.interfaces.JobView;
import org.dissan.models.users.User;

import java.util.ArrayList;
import java.util.List;

public class AssistantView implements JobView {

    private final String pageMsg;
    private final List<String> commandList = new ArrayList<>();
    private final JobStateMachine stateMachine;

    public AssistantView(User user) {
        this.pageMsg = "@Assistant{" + user.getUsername() + "}";
        commandList.add("ACCOUNT");
        commandList.add("CONTROL_DEMISE");
        commandList.add("HANDLE_DEMISE");
        commandList.add("HELP");
        commandList.add("LOG_OUT");
        commandList.add("EXIT");
        this.stateMachine = new CliMachine(user);
    }

    @Override
    public void startUi() {
        String page = "HOME" + pageMsg;
        Output.pageMessage(page, "type help to command list", true);
        while (true) {
            Output.pageMessage(page, "", false);
            String line = Input.getCmd(this.commandList);
            switch (line) {
                case "ACCOUNT" -> this.stateMachine.nextState(JobStates.ACCOUNT);
                case "CONTROL_DEMISE" -> this.stateMachine.nextState(JobStates.CONTROL_DEMISE);
                case "HANDLE_DEMISE" -> this.stateMachine.nextState(JobStates.HANDLE_CANDIDATE);
                case "HELP" -> Output.printList("HELP" + pageMsg, this.commandList);
                case "EXIT" -> {
                    return;
                }
                default -> {
                    Output.pageMessage(page, "PLEASE TYPE THIS COMMAND", true);
                    Output.printList("HELP" + pageMsg, this.commandList);
                }
            }
        }
    }
}