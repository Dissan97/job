package org.disagn.stateMachines.cliMachine;

import org.disagn.stateMachines.JobStateMachine;
import org.disagn.stateMachines.JobStates;

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
