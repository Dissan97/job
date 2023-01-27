package org.agnese_dissan.cli.cliMachine;

import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.models.users.User;

import java.util.Objects;

public class AccountState extends CliStateMachine {

    private final User user;
    private final String pageMsg;

    public AccountState(User user) {
        this.user = user;
        this.pageMsg = "Account@Employer{" + user.getUsername() + "}";
    }

    @Override
    public void nextState(CliStates state) {
        if (Objects.requireNonNull(state) == CliStates.ACCOUNT) {
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
