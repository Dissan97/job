package org.disagn.gui.employee;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.disagn.beans.JobApplierBean;
import org.disagn.graphicControllers.JobApplierGraphic;
import org.disagn.gui.GuiManager;
import org.disagn.gui.GuiStarter;
import org.disagn.gui.ViewAction;
import org.disagn.models.job.ShiftApply;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

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
        JobApplierGraphic controller = new JobApplierGraphic();
        JobApplierBean bean = controller.getBean();
        vBox.getChildren().clear();
        vBox.getStylesheets().add(Objects.requireNonNull(GuiManager.class.getResource("Style.css")).toExternalForm());
        HBox[] hBoxes;
        Button[] buttons;
        int boxWidth = 325;
        try {
            controller.pullAppliances(GuiStarter.getUser());
            List<ShiftApply> applyList = bean.getShiftApplyList();
            if (applyList != null){
                int size = applyList.size();
                Label[] labels = new Label[size];
                hBoxes = new HBox[size];
                buttons = new Button[size];
                int i = 0;
                for (ShiftApply apply:
                        applyList) {
                    hBoxes[i] = new HBox();
                    labels[i] = new Label("[SHIFT]: " + apply.getShift()+"\n[EMPLOYEE]: " + apply.getEmployee());
                    buttons[i] = new Button("MANAGE");
                    labels[i].setPrefHeight(GuiManager.BUTTON_HEIGHT);
                    labels[i].setPrefWidth(boxWidth);
                    labels[i].setAlignment(Pos.CENTER_LEFT);
                    labels[i].setContentDisplay(ContentDisplay.LEFT);
                    labels[i].setStyle(
                            "-fx-border-color: #C7C7CC;\n" +
                                    "-fx-border-width: 1;"
                    );
                    buttons[i].setPrefHeight(GuiManager.BUTTON_HEIGHT);
                    buttons[i].setPrefWidth(GuiManager.BUTTON_WIDTH - boxWidth);

                    Label finalLabel = labels[i];
                    //ADDING EVENT CONTROL TO THE BUTTON
                    buttons[i].setOnAction(e -> {
                        if (GuiManager.acceptPopUp(finalLabel, "Want to remove?")){
                            try {
                                controller.removeAppliance(apply);
                                GuiManager.popUp("Application removed");

                            } catch (Exception ex) {
                                GuiManager.popUp("The application is inserted to demise state");
                            }finally {
                                this.buildView();
                            }
                        }
                    });

                    hBoxes[i].getChildren().add(labels[i]);
                    hBoxes[i].getChildren().add(buttons[i]);
                    vBox.getChildren().add(hBoxes[i]);
                }
            }
        } catch (FileNotFoundException e) {
            GuiManager.exception(e);
        }

    }
}
