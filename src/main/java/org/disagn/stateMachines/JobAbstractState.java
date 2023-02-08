package org.disagn.stateMachines;


import org.disagn.cli.AssistantView;
import org.disagn.cli.EmployeeView;
import org.disagn.cli.EmployerView;
import org.disagn.models.users.User;
import org.disagn.stateMachines.cliMachine.CliMachine;

public abstract class JobAbstractState{

    public abstract void entry(CliMachine stateMachine);
    public void exit(){}

    public static JobAbstractState getInitializer(User user){
        JobAbstractState initialState = null;
        switch (user.getUserType()){

            case EMPLOYEE -> initialState = new EmployeeView(user);
            case EMPLOYER -> initialState = new EmployerView(user);
            case ASSISTANT -> initialState = new AssistantView(user);
        }
        return initialState;
    }

    public void home(CliMachine cliMachine){
        cliMachine.goHome();
    }
}
