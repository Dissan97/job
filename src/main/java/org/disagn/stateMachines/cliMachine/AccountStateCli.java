package org.disagn.stateMachines.cliMachine;


import org.disagn.cli.io.Output;
import org.disagn.models.users.User;
import org.disagn.stateMachines.JobAbstractState;

public class AccountStateCli extends JobAbstractState {

    private final User user;
    private final String pageMsg;

    public AccountStateCli(User user) {
        this.user = user;
        this.pageMsg = "Account@Employer{" + user.getUsername() + "}";
    }

    /**
     * Function that prints all Employer information
     */
    @Override
    public void entry (CliMachine stateMachine) {
        String page = this.pageMsg;
        Output.println("[ACCOUNT INFORMATION]");
        Output.pageMessage(page, "NAME: " + this.user.getName(), true);
        Output.pageMessage(page, "SURNAME: " + this.user.getSurname(), true);
        Output.pageMessage(page, "DATE OF BIRTH: " + this.user.getDateOfBirth(), true);
        Output.pageMessage(page, "CITY OF BIRTH: " + this.user.getCityOfBirth(), true);
        stateMachine.getBack();
    }


}
