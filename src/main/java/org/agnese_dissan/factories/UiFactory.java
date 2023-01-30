package org.agnese_dissan.factories;


import org.agnese_dissan.Macros;
import org.agnese_dissan.cli.AssistantView;
import org.agnese_dissan.cli.EmployerView;
import org.agnese_dissan.cli.EmployeeView;
import org.agnese_dissan.cli.LoginView;
import org.agnese_dissan.gui.StartGuiUi;
import org.agnese_dissan.gui.UserGuiStarter;
import org.agnese_dissan.interfaces.JobView;
import org.agnese_dissan.models.users.User;

public class UiFactory {

    private UiFactory(){}
    private static EmployeeView employeeView = null;
    private static EmployerView employerView = null;
    private static LoginView loginView = null;
    private static StartGuiUi startGuiUi = null;
    private static AssistantView assistantView = null;
    private static UserGuiStarter userGuiStarter = null;
    private static boolean gui = false;


    public static void setGui(boolean g) {
        gui = g;
    }

    public static JobView getUi(Macros type, User user){

        if (gui){
                switch (type){
                    case SIGN_IN, START -> {
                        //START SOME UI
                        if (startGuiUi == null){
                            startGuiUi = new StartGuiUi();
                        }
                        return startGuiUi;
                    }

                    case EMPLOYEE, EMPLOYER, ASSISTANT -> {
                        if (userGuiStarter == null){
                            userGuiStarter = new UserGuiStarter(user);
                        }
                        return userGuiStarter;
                    }
                }

        }else {

            if (type == Macros.START){
                if (loginView == null){
                    loginView = new LoginView();
                }
                return loginView;
            }else if (type == Macros.EMPLOYER) {
                if (employerView == null) {
                    employerView = new EmployerView(user);
                }
                return employerView;
            } else if (type == Macros.EMPLOYEE) {
                if (employeeView == null) {
                    employeeView = new EmployeeView(user);
                }
                return employeeView;
            } else if (type == Macros.ASSISTANT) {
                if (assistantView == null){
                    assistantView = new AssistantView(user);
                }
                return assistantView;
            }
        }
        return null;
    }


}
