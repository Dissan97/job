package org.agnese_dissan.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.agnese_dissan.gui.GuiManager;
import org.agnese_dissan.gui.UserGuiStarter;
import org.agnese_dissan.gui.ViewAction;

public class AccountGui {
    public Label username;
    public Label name;
    public Label surname;
    public Label dateOfBirth;
    public Label cityOfBirth;


    @FXML
    public void initialize(){
        username.setText(username.getText() + UserGuiStarter.getUser().getUsername()+"]");
        name.setText(name.getText() + UserGuiStarter.getUser().getName());
        surname.setText(surname.getText() + UserGuiStarter.getUser().getSurname());
        dateOfBirth.setText(dateOfBirth.getText() + UserGuiStarter.getUser().getDateOfBirth());
        cityOfBirth.setText(cityOfBirth.getText() + UserGuiStarter.getUser().getCityOfBirth());
    }


    public void back() {
        GuiManager.changeScene(ViewAction.BACK);
    }
}
