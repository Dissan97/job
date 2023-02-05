package org.disagn.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AccountGui {
    public Label username;
    public Label name;
    public Label surname;
    public Label dateOfBirth;
    public Label cityOfBirth;


    @FXML
    public void initialize(){
        username.setText(username.getText() + GuiStarter.getUser().getUsername()+"]");
        name.setText(name.getText() + GuiStarter.getUser().getName());
        surname.setText(surname.getText() + GuiStarter.getUser().getSurname());
        dateOfBirth.setText(dateOfBirth.getText() + GuiStarter.getUser().getDateOfBirth());
        cityOfBirth.setText(cityOfBirth.getText() + GuiStarter.getUser().getCityOfBirth());
    }


    @FXML
    public void back() {
        GuiManager.changeScene(ViewAction.BACK);
    }

    @FXML
    public void logout() {
        GuiManager.logout();
    }
}
