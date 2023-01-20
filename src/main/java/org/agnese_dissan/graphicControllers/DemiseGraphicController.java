package org.agnese_dissan.graphicControllers;

import org.agnese_dissan.Macros;
import org.agnese_dissan.beans.DemiseBean;
import org.agnese_dissan.controllers.DemiseController;
import org.agnese_dissan.models.users.Employee;

public class DemiseGraphicController {
    private final DemiseBean demiseBean;

    private DemiseController controller;
    public DemiseGraphicController(){
        this.demiseBean = new DemiseBean();
        this.controller = DemiseController.getController(this);
    }


    public DemiseBean getDemiseBean() {
        return this.demiseBean;
    }


    public void notifyDemise(Macros which) {
    }

    public void getApplication(Employee employee) {
    }

    public String newDemise(String line) {
        return line;
    }
}
