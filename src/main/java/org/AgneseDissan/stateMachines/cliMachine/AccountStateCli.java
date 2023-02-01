package org.AgneseDissan.stateMachines.cliMachine;

import org.AgneseDissan.cli.io.Output;
import org.AgneseDissan.models.users.User;
import org.AgneseDissan.stateMachines.JobStateMachine;
import org.AgneseDissan.stateMachines.JobStates;

import java.util.Objects;

public class AccountStateCli extends JobStateMachine {

    private final User user;
    private final String pageMsg;

    public AccountStateCli(User user) {
        this.user = user;
        this.pageMsg = "Account@Employer{" + user.getUsername() + "}";
    }

    @Override
    public void nextState(JobStates state) {
        if (Objects.requireNonNull(state) == JobStates.ACCOUNT) {
            this.getAccountInfo();
        }
    }

    /**
     * Function that prints all Employer information
     */


    private void getAccountInfo () {
        String page = this.pageMsg;
        Output.println("[ACCOUNT INFORMATION]");
        Output.pageMessage(page, "NAME: " + this.user.getName(), true);
        Output.pageMessage(page, "SURNAME: " + this.user.getSurname(), true);
        Output.pageMessage(page, "DATE OF BIRTH: " + this.user.getDateOfBirth(), true);
        Output.pageMessage(page, "CITY OF BIRTH: " + this.user.getCityOfBirth(), true);
    }
}
