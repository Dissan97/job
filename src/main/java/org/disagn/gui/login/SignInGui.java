package org.disagn.gui.login;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.disagn.cli.io.Printer;
import org.disagn.exceptions.NoInterfaceException;
import org.disagn.exceptions.UserLoginFailedException;
import org.disagn.graphics.LoginGraphic;
import org.disagn.gui.GuiManager;
import org.disagn.interfaces.JobView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SignInGui implements JobView {

    @FXML
    public CheckBox logged;
    @FXML
    public Button signInButton;

    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Label statusLabel;


    public void signIn() {

        LoginGraphic controller = new LoginGraphic();

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (controller.isGood(username, false) && controller.isGood(password, true)){
            try {
                controller.signIn(username, password, logged.isSelected());

            } catch (UserLoginFailedException e) {
              GuiManager.exception(e);
            } catch (NoInterfaceException e) {
                Printer.print(e.getMessage());
            } catch (FileNotFoundException e) {
                Printer.exception(e);
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
            GuiManager.exception(e);
        }
    }

}