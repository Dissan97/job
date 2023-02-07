package org.disagn.cli;


import org.disagn.cli.io.Output;
import org.disagn.interfaces.JobStateMachine;
import org.disagn.models.users.User;
import org.disagn.stateMachines.JobAbstractState;
import org.disagn.stateMachines.cliMachine.CliMachine;

import java.io.FileNotFoundException;
import java.util.List;

public class AssistantView extends JobAbstractState {

    private final String pageMsg;
    private List<String> commandList;
    private final JobStateMachine stateMachine;

    public AssistantView(User user) {
        this.pageMsg = "@Assistant{" + user.getUsername() + "}";
        this.stateMachine = new CliMachine(user);
        try {
            CommandLoader commandLoader = new CommandLoader("ASSISTANT");
            this.commandList = commandLoader.getCommandList();
        } catch (FileNotFoundException e) {
            Output.pageMessage(this.pageMsg, e.getMessage(), true);
        }
    }

    @Override
    public void entry(CliMachine stateMachine) {

       /* String page = "HOME" + pageMsg;
        Output.pageMessage(page, "type help to command list", true);
        while (true) {
            Output.pageMessage(page, "", false);
            String line = Input.getCmd(this.commandList);
            switch (line) {
                case "ACCOUNT" -> this.stateMachine.goState(JobStates.ACCOUNT);
                case "CONTROL_DEMISE" -> this.stateMachine.goState(JobStates.CONTROL_DEMISE);
                case "HANDLE_DEMISE" -> this.stateMachine.goState(JobStates.HANDLE_CANDIDATE);
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

        */
    }
}