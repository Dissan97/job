package org.disagn.gui.login;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.disagn.exceptions.UserLoginFailedException;
import org.disagn.graphicControllers.LoginGraphic;
import org.disagn.gui.GuiManager;
import org.disagn.interfaces.JobView;

import java.io.IOException;

//TODO ADJUST TO NEW FORMAT
public class SignInGui implements JobView {

    @FXML
    public CheckBox logged;

    @FXML
    TextField username_field;
    @FXML
    PasswordField password_field;
    @FXML
    Label status_label;


    public void signIn() {

        LoginGraphic controller = new LoginGraphic();

        String username = username_field.getText();
        String password = password_field.getText();

        if (controller.isGood(username, false) && controller.isGood(password, true)){
            try {
                controller.signIn(username, password, logged.isSelected());

            } catch (UserLoginFailedException e) {
              GuiManager.exception(e);
            }
        }else{
            GuiManager.popUp("Insert user name and password");
        }



    }


    public void changeView() throws IOException {
        GuiManager.changeScene("sign_up.fxml");
    }

    @Override
    public void startUi() {
        try {
            GuiManager.setUp("sign_in.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getUser(){

    }
}