package org.agnese_dissan.gui.employee;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.agnese_dissan.beans.JobApplierBean;
import org.agnese_dissan.graphicControllers.JobApplierGraphic;
import org.agnese_dissan.gui.GuiManager;
import org.agnese_dissan.gui.GuiStarter;
import org.agnese_dissan.gui.ViewAction;
import org.agnese_dissan.models.job.Shift;

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
        Label[] labels;
        Button[] submit;
        HBox[] hBoxes;
        int length = bean.getShiftList().size();
        labels = new Label[length];
        submit = new Button[length];
        hBoxes = new HBox[length];
        int buttonWidth = 350;
        int i = 0;

        //preparing the view for this use case adding label and submit button that will listen onAction() and send submit
        for (Shift shift: bean.getShiftList()) {
            hBoxes[i] = new HBox();
            //setup labels
            labels[i] = new Label("[JOB NAME]: " + shift.getName() + "\n[JOB DATE]: " + shift.getDateTime() +"\n[JOB PLACE]: " + shift.getPlace());
            labels[i].setPrefWidth(buttonWidth);
            labels[i].setPrefHeight(GuiManager.BUTTON_HEIGHT);
            labels[i].setAlignment(Pos.CENTER_LEFT);
            labels[i].setContentDisplay(ContentDisplay.LEFT);
            labels[i].setStyle("-fx-border-color: #C7C7CC; -fx-border-width: 1px;");
            //setup submit button
            submit[i] = new Button("SUB");
            submit[i].setPrefHeight(GuiManager.BUTTON_HEIGHT);
            submit[i].setPrefWidth(GuiManager.BUTTON_WIDTH - buttonWidth);
            submit[i].setContentDisplay(ContentDisplay.CENTER);
            //adding label and button to the hBox
            hBoxes[i].getChildren().add(labels[i]);
            hBoxes[i].getChildren().add(submit[i]);
            //todo add listener with second check
            int finalI = i;
            submit[i].setOnAction(actionEvent ->{
                if (GuiManager.acceptPopUp(labels[finalI], "Apply this shift?")) {
                    pushAppliance(shift, graphic);
                }
            });
            this.vBox.getChildren().add(hBoxes[i]);
            i++;
        }
    }

    void pushAppliance(Shift shift, JobApplierGraphic graphic){

        try {
            graphic.pushAppliance(shift, GuiStarter.getUser());
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
