package org.disagn.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AccountGui {
    @FXML
    public Label username;
    @FXML
    public Label name;
    @FXML
    public Label surname;
    @FXML
    public Label dateOfBirth;
    @FXML
    public Label cityOfBirth;


    @FXML
    public void initialize(){
        username.setText(username.getText() + GuiStarter.user.getUsername()+"]");
        name.setText(name.getText() + GuiStarter.user.getName());
        surname.setText(surname.getText() + GuiStarter.user.getSurname());
        dateOfBirth.setText(dateOfBirth.getText() + GuiStarter.user.getDateOfBirth());
        cityOfBirth.setText(cityOfBirth.getText() + GuiStarter.user.getCityOfBirth());
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
