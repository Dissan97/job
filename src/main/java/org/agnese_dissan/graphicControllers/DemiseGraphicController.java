package org.agnese_dissan.graphicControllers;


import org.agnese_dissan.beans.DemiseBean;
import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.controllers.DemiseController;
import org.agnese_dissan.models.users.Employee;

public class DemiseGraphicController {

    public DemiseGraphicController(){

        //TODO Add this parameters
        DemiseBean demiseBean = new DemiseBean();
        Output.println(demiseBean.toString());
        DemiseController controller = new DemiseController(this);
        Output.println(controller.toString());
    }


    public void getApplication(Employee employee) {
        Output.println(employee.toString());
    }

}
