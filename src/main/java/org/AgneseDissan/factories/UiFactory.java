package org.AgneseDissan.factories;


import org.AgneseDissan.Macros;
import org.AgneseDissan.cli.AssistantView;
import org.AgneseDissan.cli.EmployerView;
import org.AgneseDissan.cli.EmployeeView;
import org.AgneseDissan.cli.LoginView;
import org.AgneseDissan.gui.StartGuiUi;
import org.AgneseDissan.gui.GuiStarter;
import org.AgneseDissan.interfaces.JobView;
import org.AgneseDissan.models.users.User;

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
