package org.agnese_dissan.gui.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.agnese_dissan.beans.LoginBean;
import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.exceptions.UserLoginFailedException;
import org.agnese_dissan.graphicControllers.LoginGraphic;

public class SignInCtrl {

    private final LoginBean bean;
    private final LoginGraphic graphic;

    public SignInCtrl() {
        this.bean = new LoginBean();
        this.graphic = this.bean.getGraphic();


    }

    @FXML
    TextField username_field;
    @FXML
    PasswordField password_field;
    @FXML
    Label status_label;


    public void signUp(ActionEvent event) {

        String username = username_field.getText();
        String password = password_field.getText();

        this.bean.setUsername(username);
        this.bean.setPassword(password);

        Output.println("username: " + username);
        Output.println("password: " + password);
        try {
            this.graphic.signIn(this.bean.getUsername(), this.bean.getPassword());
        } catch (UserLoginFailedException e) {
            e.printStackTrace();
        }
    }




        public void completeRegistration(ActionEvent event) {
    }

    //TODO implementare il cambio di pagina modificare la classe SignUpCtrl
    public void changeView(ActionEvent actionEvent) {
    }
}