package org.dissan.stateMachines.cliMachine;

import org.dissan.cli.io.Output;
import org.dissan.exceptions.InvalidDateException;
import org.dissan.models.users.User;
import org.dissan.stateMachines.JobStateMachine;
import org.dissan.stateMachines.JobStates;

public class CliMachine extends JobStateMachine {

    private final User user;
    public CliMachine(User user) {
        this.user = user;
        super.setAccountInfo(user) ;
    }

    @Override
    public void nextState(JobStates state) {
        switch (state){
            case ACCOUNT -> {
                AccountStateCli accountState = new AccountStateCli(this.user);
                accountState.nextState(state);
            }
            case PUBLISH_SHIFT ->{
                try {
                    PublishShiftStateCli publishShift = new PublishShiftStateCli(user);
                    publishShift.nextState(state);
                } catch (InvalidDateException e) {
                    Output.exception(e);
                }
            }
            case MANAGE_APPLICANTS -> {
                ViewCandidatesStateCli viewCandidatesState = new ViewCandidatesStateCli(this.user);
                viewCandidatesState.nextState(state);
            }
            case VIEW_SCHEDULING -> {
                try {
                    ViewSchedulingStateCli schedulingState = new ViewSchedulingStateCli(this.user);
                    schedulingState.nextState(state);
                } catch (InvalidDateException e) {
                    e.printStackTrace();
                }
            }

            case MANAGE_DEMISE -> {
                DemiseManagerStateCli demiseManagerStateCli = new DemiseManagerStateCli(this.user);
                demiseManagerStateCli.nextState(state);
            }

            case HANDLE_CANDIDATE -> {
                HandleCandidateStateCli handleCandidateState = new HandleCandidateStateCli();
                handleCandidateState.nextState(state);
            }

            case APPLY_SHIFT, VIEW_APPLIES -> {
                ShiftManagerStateCli shiftManagerState = new ShiftManagerStateCli(user);
                shiftManagerState.nextState(state);
            }

        }
    }

}
