package org.dissan.stateMachines.cliMachine;

import org.dissan.models.users.User;
import org.dissan.stateMachines.JobStateMachine;
import org.dissan.stateMachines.JobStates;

import java.util.Objects;

public class ViewCandidatesStateCli extends JobStateMachine {
    User user;

    public ViewCandidatesStateCli(User user) {
        this.user = user;
    }

    @Override
    public void nextState(JobStates state) {
        if (Objects.requireNonNull(state) == JobStates.MANAGE_APPLICANTS) {
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
