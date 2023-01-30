package org.agnese_dissan.gui;

import java.io.IOException;

public class UserHomeGui {
    public void logout() {
        try {
            GuiManager.setUp("sign_in.fxml");
        } catch (IOException e) {
            GuiManager.exception(e);
        }
    }

    public void getAccount() {
        try {
            GuiManager.changeScene("account.fxml");
        } catch (IOException e) {
            GuiManager.exception(e);
        }
    }

    public void publishShift() {
        try {
            GuiManager.changeScene("publish_shift.fxml");
        } catch (IOException e) {
            GuiManager.exception(e);
        }
    }
}
