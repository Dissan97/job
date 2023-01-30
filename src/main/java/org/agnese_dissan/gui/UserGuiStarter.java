package org.agnese_dissan.gui;

import org.agnese_dissan.interfaces.JobView;
import org.agnese_dissan.models.users.User;

import java.io.IOException;
import java.util.Locale;

public class UserGuiStarter implements JobView {
    private static User user;

    public UserGuiStarter(User u) {
        user = u;
    }

    @Override
    public void startUi() {
        try {
            String file = user.getUserType().name().toLowerCase(Locale.ROOT)+".fxml";
            GuiManager.setUp(file);
        } catch (IOException e) {
            GuiManager.exception(e);
        }
    }

    public static User getUser() {
        return user;
    }
}
