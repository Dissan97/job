package org.disagn.gui.employee;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.disagn.beans.DemiseBean;
import org.disagn.graphicControllers.DemiseGraphicController;
import org.disagn.gui.GuiManager;
import org.disagn.gui.GuiStarter;
import org.disagn.gui.ViewAction;
import org.disagn.models.job.Demise;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class DemiseManagerGui {

    private Demise chooseDemise;
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
        this.vBox.getChildren().clear();
        DemiseGraphicController controller = new DemiseGraphicController();
        DemiseBean bean = controller.getBean();
        try {
            controller.pullDemises(GuiStarter.getUser());
        } catch (FileNotFoundException e) {
            GuiManager.exception(e);
        }
        List<Demise> demiseList = bean.getDemiseList();


        int size = demiseList.size();
        HBox[] hBoxes = new HBox[size];
        Button[] buttons = new  Button[size];
        Label[] labels = new Label[size];
        int i = 0;
        int boxWidth = 325;
        for (Demise demise :
                demiseList) {
            labels[i] = new Label(demise.getApplication());
            labels[i].setPrefHeight(GuiManager.BUTTON_HEIGHT);
            labels[i].setPrefWidth(boxWidth);
            labels[i].setAlignment(Pos.CENTER_LEFT);
            labels[i].setContentDisplay(ContentDisplay.LEFT);
            labels[i].setStyle(
                    "-fx-border-color: #C7C7CC;\n" +
                    "-fx-border-width: 1;"
            );
            buttons[i] = new Button("SET MOTIVATION");
            hBoxes[i] = new HBox();
            hBoxes[i].getChildren().add(labels[i]);
            hBoxes[i].getChildren().add(buttons[i]);

            buttons[i].setPrefHeight(GuiManager.BUTTON_HEIGHT);
            buttons[i].setPrefWidth(GuiManager.BUTTON_WIDTH - boxWidth);

            //Adding event listener to manage demise
            buttons[i].setOnAction(e ->{
                if (this.buildMessagePopup(demise, controller, bean)){
                    try {
                        controller.pushPendingDemise();
                        GuiManager.popUp("Demise sent");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            vBox.getChildren().add(hBoxes[i]);
            i++;
        }


    }

    private boolean buildMessagePopup(Demise finalDemise, DemiseGraphicController controller, DemiseBean bean) {
        this.chooseDemise = finalDemise;

        Label label = new Label("Write something");
        label.setStyle("-fx-font-size: 18px");
        TextField textField = new TextField();
        textField.setPrefHeight(200);
        Button submit = new Button("SUBMIT");

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle(finalDemise.getApplication());

        AtomicBoolean ret = new AtomicBoolean(false);

        submit.setOnAction(e->{
            String motivation = textField.getText();
            if (controller.setMotivation(this.chooseDemise, motivation)){
                ret.set(true);
                this.chooseDemise = bean.getPendingDemise();
                popup.close();
            }
        });

        VBox root = new VBox();
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(label);
        root.getChildren().add(textField);
        root.getChildren().add(submit);


        Scene scene = new Scene(root, 720, 720);
        scene.getStylesheets().add(Objects.requireNonNull(GuiManager.class.getResource("Style.css")).toExternalForm());
        popup.setScene(scene);
        popup.showAndWait();

        return ret.get();

    }
}
