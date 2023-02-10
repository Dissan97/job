package org.disagn.gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.disagn.beans.DemiseBean;
import org.disagn.graphics.DemiseGraphicController;
import org.disagn.models.job.Demise;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class AssistantGui {

    @FXML
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
        try {
            controller.pullDemises(GuiStarter.getUser());
        } catch (FileNotFoundException e) {
            GuiManager.exception(e);
        }
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

            //SETUP BUTTONS
            buttons[i] = new Button("MANAGE");
            buttons[i].setPrefHeight(GuiManager.BUTTON_HEIGHT);
            buttons[i].setPrefWidth(GuiManager.BUTTON_WIDTH - boxWidth);
            buttons[i].setOnAction(e -> this.manageDemise(demise));

            //SETUP H_BOXES
            hBoxes[i] = new HBox();
            hBoxes[i].getChildren().add(labels[i]);
            hBoxes[i].getChildren().add(buttons[i]);
            this.vBox.getChildren().add(hBoxes[i]);
            i++;
        }

    }

    private void manageDemise(Demise demise) {
        DemiseGraphicController controller = new DemiseGraphicController();
        DemiseBean bean = controller.getBean();
        controller.setDemiseToManage(demise);

        // start POP UP MENU ....

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("DEMISE MANAGER");


        Button acceptButton = new Button("Accept");
        acceptButton.setOnAction(e -> {
            try {
                controller.manageDemise(demise, true);
            } catch (Exception ex) {
                GuiManager.exception(ex);
            }
            popup.close();
        });

        Button refuseButton = new Button("Refuse");

        refuseButton.setOnAction(e ->{
            try {
                controller.manageDemise(demise, false);
            } catch (Exception ex) {
                GuiManager.exception(ex);
            }
            popup.close();
        });

        VBox root = new VBox();
        Label demiseCode = new Label("Application: " + bean.getPendingDemiseApplication());
        Label demiseEmployee = new Label("Employee: " + bean.getPendingDemiseEmployee());
        Label demiseMotivation = new Label("Motivation:\n" + bean.getPendingDemiseMotivation());

        int height = 100;


        demiseCode.setPrefHeight(height);
        demiseEmployee.setPrefHeight(height);
        demiseMotivation.setPrefHeight(height * 2);

        HBox hBox = new HBox();
        hBox.getChildren().add(acceptButton);
        hBox.getChildren().add(refuseButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);

        root.getChildren().add(demiseCode);
        root.getChildren().add(demiseEmployee);
        root.getChildren().add(demiseMotivation);
        root.getChildren().add(hBox);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 700, 700);
        scene.getStylesheets().add(Objects.requireNonNull(GuiManager.class.getResource("Style.css")).toExternalForm());
        popup.setScene(scene);
        popup.showAndWait();


    }

}
