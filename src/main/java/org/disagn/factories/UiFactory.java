package org.disagn.factories;


import org.disagn.Macros;
import org.disagn.cli.LoginView;
import org.disagn.cli.UserCliUi;
import org.disagn.exceptions.NoInterfaceException;
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
    public static JobView getUi(Macros type, User user) throws NoInterfaceException {
        if (gui){
                switch (type){
                    case SIGN_IN, START -> {
                        return new StartGuiUi();
                    }
                    case EMPLOYEE, EMPLOYER, ASSISTANT -> {
                        GuiStarter.user = user;
                        return new GuiStarter();
                    }
                    default -> throw new NoInterfaceException("GUI");
                }
        }else {
            switch (type){
                case START -> {
                    return new LoginView();
                }
                case ASSISTANT, EMPLOYEE, EMPLOYER ->{
                    return new UserCliUi(user);
                }
                default -> throw new NoInterfaceException("CLI");

            }
        }

    }

}
