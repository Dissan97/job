package org.disagn.gui;

import org.disagn.interfaces.JobView;
import org.disagn.models.users.User;

import java.io.IOException;
import java.util.Locale;

public class GuiStarter implements JobView {
    public static User user = null;

    @Override
    public void startUi() {
        try {
            String file = user.getUserType().name().toLowerCase(Locale.ROOT)+".fxml";
            GuiManager.setUp(file);
        } catch (IOException e) {
            GuiManager.exception(e);
        }
    }

}
