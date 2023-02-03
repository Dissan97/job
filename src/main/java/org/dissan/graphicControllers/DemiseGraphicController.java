package org.dissan.graphicControllers;


import org.dissan.beans.DemiseBean;
import org.dissan.controllers.DemiseController;
import org.dissan.exceptions.ElementAlreadyComputedException;
import org.dissan.models.job.Demise;
import org.dissan.models.users.User;

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

    public void pullDemises(User user) {
        this.controller.pullDemises(user);
    }

    public void pushDemise(Demise choosenDemise) throws IOException, ElementAlreadyComputedException {
        controller.sendDemise(choosenDemise);
    }
}
