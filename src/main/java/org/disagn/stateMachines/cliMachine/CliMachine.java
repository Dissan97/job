package org.disagn.stateMachines.cliMachine;


import org.disagn.cli.io.Output;
import org.disagn.interfaces.JobStateMachine;
import org.disagn.models.users.User;
import org.disagn.stateMachines.JobAbstractState;
import org.disagn.stateMachines.JobStates;


public class CliMachine implements JobStateMachine {

    private JobAbstractState currentState;
    private JobAbstractState previousState;
    private final User user;
    private JobAbstractState home;

    public CliMachine(User user) {
        this.user = user;
    }

    @Override
    public void goState(JobStates state) {
        this.currentState = JobAbstractState.getInitializer(user);
        this.setHome();
        this.changeState(this.currentState);
    }

    public void setCurrentState(JobAbstractState currentState) {
        this.currentState = currentState;
    }

    /**
       setup home state of the machine
     */



    public void setHome(){
        this.home = this.currentState;
        this.previousState = null;
    }

    /**
     * function to change state
     * @param state the state that we want to go
     */
    public void changeState(JobAbstractState state){
        if (state != this.currentState) {
            Output.println("Machine: changing state " + this.currentState.getClass().getSimpleName() + "  -> " + state.getClass().getSimpleName());
        }

        this.previousState = this.currentState;
        this.currentState.exit();
        this.currentState = state;
        this.currentState.entry(this);
    }

    public void getBack(){
        this.changeState(this.previousState);
    }

    public void goHome() {
        this.previousState = this.home;
        this.changeState(this.home);
    }

}
