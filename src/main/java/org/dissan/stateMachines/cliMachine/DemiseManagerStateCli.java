package org.dissan.stateMachines.cliMachine;

import org.dissan.beans.DemiseBean;
import org.dissan.cli.io.Input;
import org.dissan.cli.io.Output;
import org.dissan.graphicControllers.DemiseGraphicController;
import org.dissan.models.job.Demise;
import org.dissan.models.users.User;
import org.dissan.stateMachines.JobStateMachine;
import org.dissan.stateMachines.JobStates;

import java.util.ArrayList;
import java.util.List;

public class DemiseManagerStateCli extends JobStateMachine {

    private final User user;
    private final String page;
    private DemiseBean bean;
    private DemiseGraphicController controller;

    public DemiseManagerStateCli(User user) {
        this.user = user;
        this.setAccountInfo(user);
        this.page = "Demise manager: " + this.getAccount();
    }

    @Override
    public void nextState(JobStates state) {
        this.controller = new DemiseGraphicController();
        this.bean = controller.getBean();

        try{
            this.controller.pullDemises(user);
        }catch (Exception e){
            Output.exception(e);
        }

        List<Demise> demiseList = this.bean.getDemiseList();

        List<String> pendingDemise = new ArrayList<>();

        for (Demise d:
             demiseList) {
            pendingDemise.add(d.getApplication());
        }

        String cmd;

        if (demiseList.size() < 1){
            Output.pageMessage(page, "there is no demises", true);
            return;
        }

        while (true) {
            Output.pageMessage(page, "Demises: ", true);
            Output.printList(page, pendingDemise);
            Output.pageMessage(page, "choose a demise print exit or quit to leave the procedure", false);
            cmd = Input.getCmd(pendingDemise);


            if (pendingDemise.contains(cmd)){

                for (Demise d:
                     demiseList) {
                    if (d.getApplication().equals(cmd)){
                        controller.setDemiseToManage(d);
                        this.setMotivation();
                        return;
                    }
                }
                break;
            }

            if (cmd.equalsIgnoreCase("exit") && cmd.equalsIgnoreCase("quit")){
                break;
            }

            Output.pageMessage(page, "[" +cmd + "] not found in the demise list", true);

        }
    }

    private void setMotivation() {
        String motivation;
        Output.pageMessage(this.page, "write a motivation for demise: " + this.bean.getPendingDemiseApplication(), false);
        motivation = Input.line();
        if (this.controller.setMotivation(this.bean.getPendingDemise(), motivation)){
            Output.pageMessage(this.page, "demise sent", true);
            return;
        }
        Output.pageMessage(this.page, "cannot send demise", true);
    }
}
