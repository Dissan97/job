package org.disagn.gui.login;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.disagn.Macros;
import org.disagn.graphics.LoginGraphic;
import org.disagn.gui.GuiManager;
import org.disagn.gui.ViewAction;
import org.disagn.interfaces.JobView;
import java.io.IOException;

public class SignUpGui implements JobView {

    @FXML
    public TextField username;
    @FXML
    public TextField name;
    @FXML
    public TextField surname;
    @FXML
    public TextField cityOfBirth;
    @FXML
    public TextField dateOfBirth;
    @FXML
    public PasswordField pwd;
    @FXML
    public PasswordField pwdTwo;
    @FXML
    public CheckBox checkEmployer;

    @Override
    public void startUi() {
        try {
            GuiManager.setUp("sign_in.fxml");
        } catch (IOException e) {
            GuiManager.exception(e);
        }
    }
    @FXML
    public void goBack() {
        GuiManager.changeScene(ViewAction.BACK);
    }

    @FXML
    public void goHome() {
        GuiManager.changeScene(ViewAction.HOME);
    }
    @FXML
    public void signUp() {
        LoginGraphic controller = new LoginGraphic();
        String usrName = this.username.getText();
        String localName = this.name.getText();
        String localSurname = this.surname.getText();
        String localCityOfBirth = this.cityOfBirth.getText();
        String localDateOfBirth = this.dateOfBirth.getText();
        String localPwd = this.pwd.getText();
        if (
                controller.isGood(usrName, false) &&
                controller.isGood(localName, false) &&
                controller.isGood(localSurname, false) &&
                controller.isGood(localCityOfBirth, false) &&
                controller.isGood(localDateOfBirth, false) &&
                (controller.isGood(this.pwd.getText(), true) || this.pwd.getText().equals(pwdTwo.getText()))
        ){
            Macros macros = Macros.EMPLOYEE;
            if (checkEmployer.isSelected()){
                macros = Macros.EMPLOYER;
            }
            try {
                controller.signUp(usrName, localPwd, localName, localSurname, localDateOfBirth, localCityOfBirth, macros);
                GuiManager.popUp("SIGN UP SUCCESS");
                GuiManager.setUp("sign_in.fxml");
            } catch (Exception e) {
                GuiManager.exception(e);
            }

        }
    }


}
