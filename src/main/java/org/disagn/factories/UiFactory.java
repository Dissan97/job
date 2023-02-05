package org.disagn.factories;


import org.disagn.Macros;
import org.disagn.cli.AssistantView;
import org.disagn.cli.EmployerView;
import org.disagn.cli.EmployeeView;
import org.disagn.cli.LoginView;
import org.disagn.gui.StartGuiUi;
import org.disagn.gui.GuiStarter;
import org.disagn.interfaces.JobView;
import org.disagn.models.users.User;

public class UiFactory {

    private UiFactory(){}
    private static boolean gui = false;

    private static StartGuiUi startGuiUi = null;
    public static void setGui(boolean g) {
        gui = g;
    }

    public static JobView getUi(Macros type, User user){
        if (gui){
                switch (type){
                    case SIGN_IN, START -> {
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
