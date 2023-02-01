package org.agnese_dissan.factories;


import org.agnese_dissan.Macros;
import org.agnese_dissan.cli.AssistantView;
import org.agnese_dissan.cli.EmployerView;
import org.agnese_dissan.cli.EmployeeView;
import org.agnese_dissan.cli.LoginView;
import org.agnese_dissan.gui.StartGuiUi;
import org.agnese_dissan.gui.GuiStarter;
import org.agnese_dissan.interfaces.JobView;
import org.agnese_dissan.models.users.User;

public class UiFactory {

    private UiFactory(){}
    private static boolean gui = false;


    public static void setGui(boolean g) {
        gui = g;
    }

    public static JobView getUi(Macros type, User user){
        if (gui){
                switch (type){
                    case SIGN_IN, START -> {
                        //START SOME UI
                        return new StartGuiUi();
                    }
                    case EMPLOYEE, EMPLOYER, ASSISTANT -> {
                        return new GuiStarter(user);
                    }
                }
        }else {
            if (type == Macros.START){
                return  new LoginView();
            }else if (type == Macros.EMPLOYER) {
                return new EmployerView(user);
            } else if (type == Macros.EMPLOYEE) {
                return  new EmployeeView(user);
            } else if (type == Macros.ASSISTANT) {
                return new AssistantView(user);
            }
        }
        return null;
    }

}
