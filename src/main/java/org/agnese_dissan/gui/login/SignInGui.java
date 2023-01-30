package org.agnese_dissan.gui.login;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.agnese_dissan.beans.LoginBean;
import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.exceptions.UserLoginFailedException;
import org.agnese_dissan.graphicControllers.LoginGraphic;
import org.agnese_dissan.gui.GuiManager;
import org.agnese_dissan.interfaces.JobView;

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

        LoginGraphic graphic = new LoginGraphic();
        LoginBean bean = new LoginBean();

        String username = username_field.getText();
        String password = password_field.getText();

        if (bean.isGood(username, false) && bean.isGood(password, true)){
            try {
                graphic.signIn(username, password, logged.isSelected());
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


}