package org.disagn.stateMachines.cliMachine;


import org.disagn.decorator.PageContainer;
import org.disagn.models.users.User;
import org.disagn.stateMachines.JobAbstractState;

public class ViewSchedulingStateCli extends JobAbstractState {

    private String page;

    public ViewSchedulingStateCli(User user) {
        PageContainer container = new PageContainer("DEMISE MANAGER", user);
        this.page = container.display();
    }

    @Override
    public void entry(CliMachine stateMachine) {

    }
}
