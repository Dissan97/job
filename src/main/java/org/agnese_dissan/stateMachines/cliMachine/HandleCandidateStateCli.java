package org.agnese_dissan.stateMachines.cliMachine;

import org.agnese_dissan.stateMachines.JobStateMachine;
import org.agnese_dissan.stateMachines.JobStates;

import java.util.Objects;

public class HandleCandidateStateCli extends JobStateMachine {


    @Override
    public void nextState(JobStates state) {
        if (Objects.requireNonNull(state) == JobStates.HANDLE_CANDIDATE) {
            this.handleCandidate();
        }
    }

    /**
     * Function implemented to handle all candidates for a job appliance
     */

    private void handleCandidate() {
        //TODO IMPLEMENTS
    }
}
