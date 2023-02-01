package org.AgneseDissan.gui.employee;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.AgneseDissan.gui.GuiManager;
import org.AgneseDissan.gui.ViewAction;

public class ViewAppliesGui {

    public VBox vBox;

    @FXML
    public void initialize(){
        this.buildView();
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
    public void buildView() {
        //TODO CHANGE NAME OF THE VIEW FROM APPLY JOB TO APPLIED SHIFTS
        GuiManager.showApplies(this.vBox);
    }
}
