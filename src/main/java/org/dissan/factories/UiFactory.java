package org.dissan.factories;


import org.dissan.Macros;
import org.dissan.cli.AssistantView;
import org.dissan.cli.EmployerView;
import org.dissan.cli.EmployeeView;
import org.dissan.cli.LoginView;
import org.dissan.gui.GuiManager;
import org.dissan.gui.StartGuiUi;
import org.dissan.gui.GuiStarter;
import org.dissan.interfaces.JobView;
import org.dissan.models.users.User;

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
