package org.disagn.states;


import org.disagn.cli.AssistantView;
import org.disagn.cli.EmployeeView;
import org.disagn.cli.EmployerView;
import org.disagn.models.users.User;
import org.disagn.states.commandline.CliMachine;

public abstract class JobAbstractState{

    public abstract void entry(CliMachine stateMachine);
    public void exit(){}

    public static JobAbstractState getInitializer(User user){
        JobAbstractState initialState;
        switch (user.getUserType()){

            case EMPLOYEE -> initialState = new EmployeeView(user);
            case EMPLOYER -> initialState = new EmployerView(user);
            case ASSISTANT -> initialState = new AssistantView(user);
            default -> initialState =null;
        }
        return initialState;
    }

    public void home(CliMachine cliMachine){
        cliMachine.goHome();
    }
}
