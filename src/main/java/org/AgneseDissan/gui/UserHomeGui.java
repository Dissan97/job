package org.AgneseDissan.gui;

import javafx.fxml.FXML;

import java.io.IOException;

public class UserHomeGui {

    @FXML
    public void logout() {
        GuiManager.logout();
    }

    @FXML
    public void getAccount() {
        try {
            GuiManager.changeScene("account.fxml");
        } catch (IOException e) {
            GuiManager.exception(e);
        }
    }

    @FXML
    public void publishShift() {
        try {
            GuiManager.changeScene("publish_shift.fxml");
        } catch (IOException e) {
            GuiManager.exception(e);
        }
    }

    @FXML
    public void applyJob() {
        try {
            GuiManager.changeScene("job_applier.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void viewApplies() {
        try {
            GuiManager.changeScene("view_applies.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void viewApplicant() {
        try {
            GuiManager.changeScene("view_applicants.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
