package org.disagn.interfaces;

import org.disagn.stateMachines.JobStates;

public interface JobStateMachine {
    void goState(JobStates state);
}
