package org.disagn.machines.commandline.employee;


import org.disagn.beans.DemiseBean;
import org.disagn.cli.io.Input;
import org.disagn.cli.io.Output;
import org.disagn.decorator.PageContainer;
import org.disagn.graphics.DemiseGraphicController;
import org.disagn.models.job.Demise;
import org.disagn.models.users.User;
import org.disagn.machines.JobAbstractState;
import org.disagn.machines.commandline.CliMachine;
import java.util.ArrayList;
import java.util.List;


public class DemiseManagerStateCli extends JobAbstractState {

    private final User user;
    private final String page;
    private DemiseBean bean;
    private DemiseGraphicController controller;

    public DemiseManagerStateCli(User user) {
        this.user = user;
        PageContainer container = new PageContainer("DEMISE MANAGER", this.user);
        this.page = container.display();
    }

    @Override
    public void entry(CliMachine stateMachine) {
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


        if (demiseList.isEmpty()){
            Output.pageMessage(page, "there is no demises", true);
            return;
        }

        demise(demiseList, pendingDemise);
    }

    private void demise(List<Demise> demiseList, List<String> pendingDemise) {
        String cmd;
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
                return;
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
