package org.agnese_dissan.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.agnese_dissan.beans.LoginBean;
import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.exceptions.UserLoginFailedException;
import org.agnese_dissan.graphicControllers.LoginGraphic;

public class LogInViewController {

    private LoginBean bean;
    private LoginGraphic graphic;

    public LogInViewController() {
        this.bean = new LoginBean();
        this.graphic = this.bean.getGraphic();

    }

    @FXML
    TextField username_field;
    @FXML
    TextField password_field;
    @FXML
    Label status_label;

    String username = "admin";
    String password = "admin";
    public void loginAction(ActionEvent event) {

        String username_text = username_field.getText();
        String password_text = password_field.getText();
        this.bean.setUsername(username_text);
        this.bean.setPassword(password_text);
        Output.println("username: " + username_text);
        Output.println("password: " + password_text);
        /*try {
            this.graphic.signIn(this.bean.getUsername(), this.bean.getPassword());
        } catch (UserLoginFailedException e) {
            e.printStackTrace();
        }*/
    }


        public void completeRegistration(ActionEvent event) {
    }
}