package org.agnese_dissan.cli.cliMachine;

import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.interfaces.Refresh;

public abstract class CliStateMachine {

    private String account;
    public abstract void nextState(CliStates state);
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

    public void setAccountInfo(String account){
        this.account = account;
    }

    public String getAccount() {
        return account;
    }
}
