package org.dissan.gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.dissan.beans.DemiseBean;
import org.dissan.beans.ShiftSchedulerBean;
import org.dissan.graphicControllers.DemiseGraphicController;
import org.dissan.graphicControllers.ShiftSchedulerGraphic;
import org.dissan.models.job.Demise;
import org.dissan.models.job.ShiftApply;
import org.dissan.models.users.User;

import java.io.IOException;
import java.util.List;

public class AssistantGui {

    public VBox vBox;

    @FXML
    public void initialize(){
        this.buildView();
    }



    @FXML
    public void logout() {
        GuiManager.logout();
    }
    @FXML
    public void getAccount() {
        try {
            GuiManager.changeScene("account.fxml");
        } catch (IOException e) {
            GuiManager.exception(e);
        }
    }

    @FXML
    private void buildView() {
        DemiseGraphicController controller = new DemiseGraphicController();
        DemiseBean bean = controller.getBean();
        controller.pullDemises(GuiStarter.getUser());
        List<Demise> demiseList = bean.getDemiseList();
        this.buildList(demiseList);
    }

    private void buildList(List<Demise> demiseList) {
        this.vBox.getChildren().clear();

        Button[] buttons;
        Label[] labels;
        HBox[] hBoxes;

        int size = demiseList.size();

        buttons = new Button[size];
        labels = new Label[size];
        hBoxes = new HBox[size];
        int boxWidth = 325;
        int i = 0;
        for (Demise demise:
                demiseList) {
            labels[i] = new Label("Apply: " + demise.getApplication()  + "\nEmployee: " + demise.getEmployee());
            labels[i].setPrefHeight(GuiManager.BUTTON_HEIGHT);
            labels[i].setPrefWidth(boxWidth);
            labels[i].setAlignment(Pos.CENTER_LEFT);
            labels[i].setContentDisplay(ContentDisplay.LEFT);
            labels[i].setStyle(
                    "-fx-border-color: #C7C7CC;\n" +
                            "-fx-border-width: 1;"
            );

            buttons[i] = new Button("MANAGE");
            buttons[i].setPrefHeight(GuiManager.BUTTON_HEIGHT);
            buttons[i].setPrefWidth(GuiManager.BUTTON_WIDTH - boxWidth);
            hBoxes[i] = new HBox();
            hBoxes[i].getChildren().add(labels[i]);
            hBoxes[i].getChildren().add(buttons[i]);
            this.vBox.getChildren().add(hBoxes[i]);
            i++;
        }

    }

}
