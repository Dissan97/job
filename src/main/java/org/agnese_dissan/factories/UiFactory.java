package org.agnese_dissan.factories;


import org.agnese_dissan.Macros;
import org.agnese_dissan.cli.EmployerView;
import org.agnese_dissan.cli.EmployeeView;
import org.agnese_dissan.cli.LoginView;
import org.agnese_dissan.gui.StartGuiUi;
import org.agnese_dissan.gui.login.SignInGui;
import org.agnese_dissan.interfaces.JobView;
import org.agnese_dissan.models.users.User;

public class UiFactory {

    private UiFactory(){}
    private static EmployeeView employeeView = null;
    private static EmployerView employerView = null;
    private static LoginView loginView = null;
    private static StartGuiUi startGuiUi = null;
    private static SignInGui signInGui = null;
    private static boolean gui = false;


    public static void setGui(boolean gui) {
        UiFactory.gui = gui;
    }

    public static JobView getUi(Macros type, User user){

        if (gui){
            if (type == Macros.START){
                if (startGuiUi == null){
                    startGuiUi = new StartGuiUi();
                }
                return startGuiUi;
            }else if (type == Macros.SIGN_IN){
                if (signInGui == null){
                    signInGui = new SignInGui();
                }
                return signInGui;
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
            }
        }
        return null;
    }


}
