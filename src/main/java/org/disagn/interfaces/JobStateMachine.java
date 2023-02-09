package org.disagn.interfaces;

import org.disagn.machines.JobStates;

public interface JobStateMachine {
    void goState(JobStates state);
}
