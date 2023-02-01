package org.AgneseDissan.graphicControllers;


import org.AgneseDissan.beans.DemiseBean;
import org.AgneseDissan.cli.io.Output;
import org.AgneseDissan.controllers.DemiseController;
import org.AgneseDissan.models.users.Employee;

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
