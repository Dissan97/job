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
