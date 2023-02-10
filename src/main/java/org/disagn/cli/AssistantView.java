package org.disagn.cli;

import org.disagn.cli.io.Printer;
import org.disagn.models.users.User;
import org.disagn.states.JobAbstractState;
import org.disagn.states.commandline.CliMachine;

public class AssistantView extends JobAbstractState {



    public AssistantView(User user) {
        Printer.print(user.getUsername());
        //implements this view
    }

    @Override
    public void entry(CliMachine stateMachine) {
        //Add machine functionality
    }
}
