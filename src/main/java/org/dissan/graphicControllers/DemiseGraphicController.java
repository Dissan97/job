package org.dissan.graphicControllers;


import org.dissan.beans.DemiseBean;
import org.dissan.cli.io.Output;
import org.dissan.controllers.DemiseController;
import org.dissan.models.users.Employee;

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
