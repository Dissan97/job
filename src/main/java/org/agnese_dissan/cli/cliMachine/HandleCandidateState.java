package org.agnese_dissan.cli.cliMachine;

import java.util.Objects;

public class HandleCandidateState extends CliStateMachine {


    @Override
    public void nextState(CliStates state) {
        if (Objects.requireNonNull(state) == CliStates.HANDLE_CANDIDATE) {
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
