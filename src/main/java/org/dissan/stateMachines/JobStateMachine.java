package org.dissan.stateMachines;

import org.dissan.cli.io.Output;
import org.dissan.interfaces.Refresh;
import org.dissan.models.users.User;

public abstract class JobStateMachine {

    private String account;
    public abstract void nextState(JobStates state);
    /**
     * Function need to avoid blocking a user into a use case giving him a method to go back
     * @param s passing the string controlling if is #EXIT or #BACK
     * @param bean updating bean
     * @return true if user want to exit by passing the string #EXIT or #BACK
     */


    protected boolean exit(String s, Refresh bean) {

        if (s.equalsIgnoreCase("#BACK") || s.equalsIgnoreCase("#EXIT")) {
            Output.println("Entered Here");
            bean.refresh();
        }
        return false;
    }

    public void setAccountInfo(User user){
        this.account = "{"+user.getUserType().toString().toLowerCase() + "@";
    }

    public String getAccount() {
        return account;
    }
}
