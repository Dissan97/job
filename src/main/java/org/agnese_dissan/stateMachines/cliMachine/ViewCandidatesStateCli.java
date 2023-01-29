package org.agnese_dissan.stateMachines.cliMachine;

import org.agnese_dissan.models.users.User;
import org.agnese_dissan.stateMachines.JobStateMachine;
import org.agnese_dissan.stateMachines.JobStates;

import java.util.Objects;

public class ViewCandidatesStateCli extends JobStateMachine {
    User user;

    public ViewCandidatesStateCli(User user) {
        this.user = user;
    }

    @Override
    public void nextState(JobStates state) {
        if (Objects.requireNonNull(state) == JobStates.VIEW_CANDIDATES) {
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
