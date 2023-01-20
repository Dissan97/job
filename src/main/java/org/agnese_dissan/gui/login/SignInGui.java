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
import org.agnese_dissan.interfaces.JobView;

//TODO ADJUST TO NEW FORMAT
public class SignInGui implements JobView {

    private final LoginBean bean;
    private final LoginGraphic graphic;

    public SignInGui() {
        this.bean = new LoginBean();
        this.graphic = new LoginGraphic(this.bean);



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


        Output.println("username: " + username);
        Output.println("password: " + password);

    }




        public void completeRegistration(ActionEvent event) {
    }

    //TODO implementare il cambio di pagina modificare la classe SignUpCtrl
    public void changeView(ActionEvent actionEvent) {
    }

    @Override
    public void startUi() {

    }
}