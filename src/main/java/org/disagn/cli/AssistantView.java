package org.disagn.cli;

import org.disagn.cli.io.Output;
import org.disagn.models.users.User;
import org.disagn.machines.JobAbstractState;
import org.disagn.machines.cliMachine.CliMachine;

public class AssistantView extends JobAbstractState {



    public AssistantView(User user) {
        Output.print(user.getUsername());
        //implements this view
    }

    @Override
    public void entry(CliMachine stateMachine) {
        //Add machine functionality
    }
}
