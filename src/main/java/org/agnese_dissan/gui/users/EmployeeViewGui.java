package org.agnese_dissan.gui.users;

import org.agnese_dissan.gui.GuiManager;

import java.io.IOException;

public class EmployeeViewGui {

    public void getAccount() {
        try {
            GuiManager.changeScene("account.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // TODO add param for fxml::Controller
}
