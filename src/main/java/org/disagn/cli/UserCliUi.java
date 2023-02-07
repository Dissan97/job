package org.disagn.cli;


import org.disagn.Macros;
import org.disagn.interfaces.JobView;
import org.disagn.models.users.User;
import org.disagn.stateMachines.JobStates;
import org.disagn.stateMachines.cliMachine.CliMachine;

public record UserCliUi(User user) implements JobView {

    @Override
    public void startUi() {
        Macros type = user().getUserType();
        CliMachine cliMachine = new CliMachine(user);
        cliMachine.goState(JobStates.valueOf(type.toString()));
    }
}
