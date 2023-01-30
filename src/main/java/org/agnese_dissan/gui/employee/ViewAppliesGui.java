package org.agnese_dissan.gui.employee;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.VBox;
import org.agnese_dissan.beans.JobApplierBean;
import org.agnese_dissan.graphicControllers.JobApplierGraphic;
import org.agnese_dissan.gui.GuiManager;
import org.agnese_dissan.gui.UserGuiStarter;
import org.agnese_dissan.gui.ViewAction;
import org.agnese_dissan.models.job.ShiftApply;

import java.io.FileNotFoundException;
import java.util.List;

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
        JobApplierGraphic graphic = new JobApplierGraphic();
        JobApplierBean bean = graphic.getBean();
        this.vBox.getChildren().clear();
        try {
            graphic.pullAppliances(UserGuiStarter.getUser());
            List<ShiftApply> applyList = bean.getShiftApplyList();
            if (applyList != null){
                Button button[] = new Button[applyList.size()];
                int i = 0;
                for (ShiftApply apply:
                     applyList) {
                    button[i] = new Button("[SHIFT]: " + apply.getShift()+"\n[EMPLOYER]: " + apply.getEmployer());
                    button[i].setPrefHeight(GuiManager.BUTTON_HEIGHT);
                    button[i].setPrefWidth(GuiManager.BUTTON_WIDTH);
                    button[i].setAlignment(Pos.CENTER_LEFT);
                    button[i].setContentDisplay(ContentDisplay.LEFT);
                    this.vBox.getChildren().add(button[i]);
                }

            }
        } catch (FileNotFoundException e) {
            GuiManager.exception(e);
        }
    }
}
