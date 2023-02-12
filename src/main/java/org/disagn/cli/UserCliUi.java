package org.disagn.cli;


import org.disagn.Macros;
import org.disagn.interfaces.JobView;
import org.disagn.models.users.User;
import org.disagn.states.JobStates;
import org.disagn.states.commandline.CliMachine;

/**
 * This is responsible to launch the correct interface by using the cli machine.
 * @param user user.getType will launch the correct machine.
 */

public record UserCliUi(User user) implements JobView {

    @Override
    public void startUi() {
        Macros type = user().getUserType();
        CliMachine cliMachine = new CliMachine(user);
        cliMachine.goState(JobStates.valueOf(type.toString()));
    }
}
