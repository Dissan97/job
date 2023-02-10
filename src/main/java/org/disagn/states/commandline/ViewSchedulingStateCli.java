package org.disagn.states.commandline;


import org.disagn.cli.io.Printer;
import org.disagn.decorator.PageContainer;
import org.disagn.models.users.User;
import org.disagn.states.JobAbstractState;

public class ViewSchedulingStateCli extends JobAbstractState {

    private final String page;

    public ViewSchedulingStateCli(User user) {
        PageContainer container = new PageContainer("DEMISE MANAGER", user);
        this.page = container.display();
    }

    @Override
    public void entry(CliMachine stateMachine) {
        //need to be implemented
        Printer.pageMessage(this.page, "Hello from scheduling", true);
    }
}
