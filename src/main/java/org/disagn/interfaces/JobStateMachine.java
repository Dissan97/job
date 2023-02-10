package org.disagn.interfaces;

import org.disagn.states.JobStates;

public interface JobStateMachine {
    void goState(JobStates state);
}
