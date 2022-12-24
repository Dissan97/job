package org.agnese_dissan.factories;


import org.agnese_dissan.cli.EmployeeView;
import org.agnese_dissan.cli.EmployerView;
import org.agnese_dissan.interfaces.JobView;
import org.agnese_dissan.models.User;

public class UiFactory {

    private UiFactory(){}
    private static EmployeeView employeeView = null;
    private static EmployerView employerView = null;
    public static JobView getUi(int type, User user){

        if (type == 0){
            if (employerView == null){
                employerView = new EmployerView(user);
            }
            return employerView;
        }

        else if (type == 1) {
            if (employeeView == null) {
                employeeView = new EmployeeView(user);
            }
            return employeeView;
        }
        return null;
    }
}
