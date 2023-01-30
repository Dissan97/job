package org.agnese_dissan.gui.employee;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.VBox;
import org.agnese_dissan.beans.AccountBean;
import org.agnese_dissan.beans.JobApplierBean;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.graphicControllers.JobApplierGraphic;
import org.agnese_dissan.gui.GuiManager;
import org.agnese_dissan.gui.UserGuiStarter;
import org.agnese_dissan.gui.UserHomeGui;
import org.agnese_dissan.gui.ViewAction;
import org.agnese_dissan.models.job.Shift;

import java.util.List;

public class JobApplierGui {

    @FXML
    public VBox vBox;

    @FXML
    public void initialize(){
        this.buildView();
    }

    @FXML
    public void buildView() {
        JobApplierGraphic graphic = new JobApplierGraphic();
        JobApplierBean bean = graphic.getBean();
        graphic.pullShifts();
        this.vBox.getChildren().clear();
        Button[] button;
        int length = bean.getShiftList().size();
        button = new Button[length];
        int i = 0;
       for (Shift shift: bean.getShiftList()) {
           button[i] = new Button("[JOB NAME]: " + shift.getName() + "\n[JOB DATE]: " + shift.getDateTime() +"\n[JOB PLACE]: " + shift.getPlace());
           button[i].setPrefWidth(GuiManager.BUTTON_WIDTH);
           button[i].setPrefHeight(GuiManager.BUTTON_HEIGHT);
           button[i].setAlignment(Pos.CENTER_LEFT);
           button[i].setContentDisplay(ContentDisplay.LEFT);
           button[i].setOnAction(actionEvent ->{
               pushAppliance(shift, graphic);
           });
           this.vBox.getChildren().add(button[i]);
           i++;
        }
    }

    void pushAppliance(Shift shift, JobApplierGraphic graphic){
        try {
            graphic.pushAppliance(shift, UserGuiStarter.getUser());
        } catch (Exception e) {
            GuiManager.exception(e);
        }
    }

    @FXML
    public void goBack() {
        GuiManager.changeScene(ViewAction.HOME);
    }

    @FXML
    public void goHome() {
        GuiManager.changeScene(ViewAction.BACK);
    }
}
