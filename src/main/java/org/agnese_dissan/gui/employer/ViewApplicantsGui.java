package org.agnese_dissan.gui.employer;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.agnese_dissan.gui.GuiManager;
import org.agnese_dissan.gui.ViewAction;

public class ViewApplicantsGui {

    @FXML
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
            GuiManager.showApplies(this.vBox);
        }


}
