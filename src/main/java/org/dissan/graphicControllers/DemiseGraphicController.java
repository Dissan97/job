package org.dissan.graphicControllers;


import org.dissan.beans.DemiseBean;
import org.dissan.controllers.DemiseController;
import org.dissan.exceptions.ElementAlreadyComputedException;
import org.dissan.models.job.Demise;
import org.dissan.models.users.User;

import java.io.FileNotFoundException;
import java.io.IOException;

public class DemiseGraphicController {

    private final DemiseBean demiseBean;
    private final DemiseController controller;
    public DemiseGraphicController(){
        this.demiseBean = new DemiseBean();
        this.controller = new DemiseController(this.demiseBean);
    }


    public DemiseBean getBean() {
        return this.demiseBean;
    }

    public void pullDemises(User user) throws FileNotFoundException {
        this.controller.pullDemises(user);
    }

    public void pushPendingDemise() throws IOException, ElementAlreadyComputedException {
        Demise chooseDemise = this.demiseBean.getPendingDemise();
        if (chooseDemise != null) {
            controller.sendDemise(chooseDemise);
        }
    }

    public boolean setMotivation(Demise chooseDemise, String motivation) {
        if (motivation.equals("")){
            return false;
        }
        chooseDemise.setMotivation(motivation);
        this.demiseBean.setPendingDemise(chooseDemise);

        return true;
    }

    public void setDemiseToManage(Demise demise) {
        if (demise != null){
            this.demiseBean.setPendingDemise(demise);
        }
    }

    public void manageDemise(Demise demise, boolean b) throws Exception {
        if (demise != null) {
            this.demiseBean.pendingDemiseAcceptance(demise, b);
            if (b) {
                this.controller.acceptDemise();
            } else {
                this.controller.refuseDemise();
            }
        }
    }


}
