package org.disagn.gui;

import org.disagn.cli.io.Printer;
import org.disagn.interfaces.JobView;
import org.disagn.models.users.User;

import java.io.IOException;
import java.util.Locale;

public class GuiStarter implements JobView {
    private static User user = null;

    @Override
    public void startUi() {
        try {
            String file = user.getUserType().name().toLowerCase(Locale.ROOT)+".fxml";
            GuiManager.setUp(file);
        } catch (IOException e) {
            //Agnese Agueli if we are not able to launch a gui interface the user should be notice in the shell that there's a problem
            Printer.print("Some error occurred unable to launch the first interface");
            Printer.exception(e);
        }
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        GuiStarter.user = user;
    }
}
