package org.disagn.states.commandline;


import org.disagn.cli.io.Printer;
import org.disagn.decorator.PageContainer;
import org.disagn.models.users.User;
import org.disagn.states.JobAbstractState;

public class AccountStateCli extends JobAbstractState {

    private final User user;
    private final String page;

    public AccountStateCli(User user) {
        this.user = user;
        PageContainer container = new PageContainer("ACCOUNT", this.user);
        this.page = container.display();
    }

    /**
     * Function that prints all Employer information
     */
    @Override
    public void entry (CliMachine stateMachine) {
        Printer.print("[ACCOUNT INFORMATION]");
        Printer.pageMessage(this.page, "NAME: " + this.user.getName(), true);
        Printer.pageMessage(this.page, "SURNAME: " + this.user.getSurname(), true);
        Printer.pageMessage(this.page, "DATE OF BIRTH: " + this.user.getDateOfBirth(), true);
        Printer.pageMessage(this.page, "CITY OF BIRTH: " + this.user.getCityOfBirth(), true);
        stateMachine.getBack();
    }


}
