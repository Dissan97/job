package org.agnese_dissan.cli;


import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.interfaces.JobView;
import org.agnese_dissan.models.User;

public class EmployeeView implements JobView {


    public EmployeeView(User user) {

    }

    @Override
    public void startUi() {

        Output.println("I'M EMPLOYEE");
    }
}
