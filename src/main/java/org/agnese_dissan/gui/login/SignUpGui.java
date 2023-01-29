package org.agnese_dissan.gui.login;

import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.agnese_dissan.Macros;
import org.agnese_dissan.beans.LoginBean;
import org.agnese_dissan.graphicControllers.LoginGraphic;
import org.agnese_dissan.gui.GuiManager;
import org.agnese_dissan.gui.ViewAction;
import org.agnese_dissan.interfaces.JobView;

import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class SignUpGui implements JobView {

    public TextField username;
    public TextField name;
    public TextField surname;
    public TextField cityOfBirth;
    public TextField dateOfBirth;
    public PasswordField pwd;
    public PasswordField pwdTwo;
    public CheckBox checkEmployer;

    @Override
    public void startUi() {
        try {
            GuiManager.setUp("sign_in.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goBack() {
        GuiManager.changeScene(ViewAction.BACK);
    }


    public void goHome() {
        GuiManager.changeScene(ViewAction.HOME);
    }

    public void signUp() {
        LoginGraphic graphic = new LoginGraphic();
        LoginBean bean = new LoginBean();
        String username = this.username.getText();
        String name = this.name.getText();
        String surname = this.surname.getText();
        String cityOfBirth = this.cityOfBirth.getText();
        String dateOfBirth = this.dateOfBirth.getText();
        String pwd = this.pwd.getText();
        if (
                bean.isGood(username, false) &&
                bean.isGood(name, false) &&
                bean.isGood(surname, false) &&
                bean.isGood(cityOfBirth, false) &&
                bean.isGood(dateOfBirth, false) &&
                (bean.isGood(this.pwd.getText(), true) || this.pwd.getText().equals(pwdTwo.getText()))
        ){
            Macros macros = Macros.EMPLOYEE;
            if (checkEmployer.isSelected()){
                macros = Macros.EMPLOYER;
            }
            try {
                graphic.signUp(username, pwd, name, surname, dateOfBirth, cityOfBirth, macros);
                GuiManager.setUp("sign_in.fxml");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

}
