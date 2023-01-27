package org.agnese_dissan.cli.cliMachine;

import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.models.users.User;

public class CliMachine extends CliStateMachine {

    private final User user;
    private AccountState accountState = null;
    private PublishShiftState shiftState = null;
    private ViewCandidatesState viewCandidatesState = null;
    private ViewSchedulingState schedulingState = null;
    private HandleCandidateState handleCandidateState = null;
    private ShiftManagerState shiftManagerState = null;
    private DemiseMangerState demiseMangerState = null;
    public CliMachine(User user) {
        this.user = user;
        String type = "{"+user.getUserType().toString().toLowerCase() + "@";
        super.setAccountInfo(type + user.getUsername() + "}") ;
    }

    @Override
    public void nextState(CliStates state) {
        switch (state){
            case ACCOUNT -> {
                if (this.accountState == null) {
                    this.accountState = new AccountState(this.user);
                }
                this.accountState.nextState(state);
            }
            case PUBLISH_SHIFT ->{
                if (this.shiftState == null){
                    try {
                        this.shiftState = new PublishShiftState(user);
                    } catch (InvalidDateException e) {
                        e.printStackTrace();
                    }
                }
                this.shiftState.nextState(state);
            }
            case VIEW_CANDIDATES -> {
                if (this.viewCandidatesState == null){
                    this.viewCandidatesState = new ViewCandidatesState(this.user);
                }
                this.viewCandidatesState.nextState(state);
            }
            case VIEW_SCHEDULING -> {
                if (this.schedulingState == null){
                    try {
                        this.schedulingState = new ViewSchedulingState(this.user);
                    } catch (InvalidDateException e) {
                        e.printStackTrace();
                    }
                }
                this.schedulingState.nextState(state);
            }
            case HANDLE_CANDIDATE -> {
                if (this.handleCandidateState == null){
                    this.handleCandidateState = new HandleCandidateState();
                }
                this.handleCandidateState.nextState(state);
            }
            case APPLY_SHIFT, VIEW_APPLIES -> {
                if (this.shiftManagerState == null){
                    this.shiftManagerState = new ShiftManagerState(user);
                }
                this.shiftManagerState.nextState(state);
            }
            case DEMISE_SHIFT -> {
                if (this.demiseMangerState == null){
                    this.demiseMangerState = new DemiseMangerState(user);
                }
                this.demiseMangerState.nextState(state);
            }
        }
    }

}
