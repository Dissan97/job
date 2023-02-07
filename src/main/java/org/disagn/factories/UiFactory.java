package org.disagn.factories;


import org.disagn.Macros;
import org.disagn.cli.LoginView;
import org.disagn.cli.UserCliUi;
import org.disagn.gui.GuiStarter;
import org.disagn.gui.StartGuiUi;
import org.disagn.interfaces.JobView;
import org.disagn.models.users.User;

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
                        return new StartGuiUi();
                    }
                    case EMPLOYEE, EMPLOYER, ASSISTANT -> {
                        return new GuiStarter(user);
                    }
                }
        }else {
            switch (type){
                case START -> {
                    return new LoginView();
                }
                case ASSISTANT, EMPLOYEE, EMPLOYER ->{
                    return new UserCliUi(user);
                }
            }
        }
        return null;
    }

}
