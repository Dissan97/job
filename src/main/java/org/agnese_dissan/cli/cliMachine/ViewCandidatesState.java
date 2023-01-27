package org.agnese_dissan.cli.cliMachine;

import org.agnese_dissan.models.users.User;

import java.util.Objects;

public class ViewCandidatesState extends CliStateMachine {
    User user;

    public ViewCandidatesState(User user) {
        this.user = user;
    }

    @Override
    public void nextState(CliStates state) {
        if (Objects.requireNonNull(state) == CliStates.VIEW_CANDIDATES) {
            this.viewCandidates();
        }
    }

    /**
     * Function that print candidates for an appliance
     */
    private void viewCandidates() {
        //TODO IMPLEMENT
    }
}
