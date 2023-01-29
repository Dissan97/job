package org.agnese_dissan.stateMachines.cliMachine;

import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.models.users.User;
import org.agnese_dissan.stateMachines.JobStateMachine;
import org.agnese_dissan.stateMachines.JobStates;

public class CliMachine extends JobStateMachine {

    private final User user;
    private AccountStateCli accountState = null;
    private PublishShiftStateCli shiftState = null;
    private ViewCandidatesStateCli viewCandidatesState = null;
    private ViewSchedulingStateCli schedulingState = null;
    private HandleCandidateStateCli handleCandidateState = null;
    private ShiftManagerStateCli shiftManagerState = null;
    private DemiseMangerStateCli demiseMangerState = null;
    public CliMachine(User user) {
        this.user = user;
        String type = "{"+user.getUserType().toString().toLowerCase() + "@";
        super.setAccountInfo(type + user.getUsername() + "}") ;
    }

    @Override
    public void nextState(JobStates state) {
        switch (state){
            case ACCOUNT -> {
                if (this.accountState == null) {
                    this.accountState = new AccountStateCli(this.user);
                }
                this.accountState.nextState(state);
            }
            case PUBLISH_SHIFT ->{
                if (this.shiftState == null){
                    try {
                        this.shiftState = new PublishShiftStateCli(user);
                    } catch (InvalidDateException e) {
                        e.printStackTrace();
                    }
                }
                this.shiftState.nextState(state);
            }
            case VIEW_CANDIDATES -> {
                if (this.viewCandidatesState == null){
                    this.viewCandidatesState = new ViewCandidatesStateCli(this.user);
                }
                this.viewCandidatesState.nextState(state);
            }
            case VIEW_SCHEDULING -> {
                if (this.schedulingState == null){
                    try {
                        this.schedulingState = new ViewSchedulingStateCli(this.user);
                    } catch (InvalidDateException e) {
                        e.printStackTrace();
                    }
                }
                this.schedulingState.nextState(state);
            }
            case HANDLE_CANDIDATE -> {
                if (this.handleCandidateState == null){
                    this.handleCandidateState = new HandleCandidateStateCli();
                }
                this.handleCandidateState.nextState(state);
            }
            case APPLY_SHIFT, VIEW_APPLIES -> {
                if (this.shiftManagerState == null){
                    this.shiftManagerState = new ShiftManagerStateCli(user);
                }
                this.shiftManagerState.nextState(state);
            }
            case DEMISE_SHIFT -> {
                if (this.demiseMangerState == null){
                    this.demiseMangerState = new DemiseMangerStateCli(user);
                }
                this.demiseMangerState.nextState(state);
            }
        }
    }

}
