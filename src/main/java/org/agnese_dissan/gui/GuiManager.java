package org.agnese_dissan.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.agnese_dissan.Macros;
import org.agnese_dissan.beans.JobApplierBean;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.graphicControllers.JobApplierGraphic;
import org.agnese_dissan.models.job.ShiftApply;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class used to change scene throw fxml-views
 */

public class GuiManager {

    public final static int WEIGHT = 1024, HEIGHT = 720;
    public static final double BUTTON_WIDTH = 500;
    public static final int BUTTON_HEIGHT = 100;

    //TODO must be changed to a state machine pattern
    private static Scene home = null;
    private static Scene now;
    private static Scene prev = null;

    public static Stage stage;

    /**
     * This function must be launched before all if you want to run the code
     * @param stage the stage param which appears in the start method
     */

    public static void setStage(Stage stage) {
        GuiManager.stage = stage;
        GuiManager.stage.setResizable(false);
    }

    /**
     * First function needed to build the Application view
     * @param name fxml filename
     */

    public static void setUp(String name) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(GuiManager.class.getResource(name));
        now = new Scene(fxmlLoader.load(), WEIGHT, HEIGHT);
        home = now;
        prev = null;

        stage.setScene(home);
        stage.show();
    }

    /**
     * This function is used to set some view using a specific fxml file
     * @param name fxml filename
     */

    public static void changeScene(String name) throws IOException {
        prev = now;
        FXMLLoader fxmlLoader = new FXMLLoader(GuiManager.class.getResource(name));
        now = new Scene(fxmlLoader.load(),  WEIGHT, HEIGHT);
        stage.setScene(now);
        stage.show();
    }

    /**
     * Function used to go back
     */

    private static void goBack(){

        if (prev != null && now != home) {
            Scene temp;
            temp = now;
            now = prev;
            prev = temp;
            stage.setScene(now);
            stage.show();
        }
    }

    /**
     * Function used to go home
     */

    private static void goHome(){
        if (now != home) {
            prev = now;
            now = home;
            stage.setScene(now);
            stage.show();
        }
    }

    public static void changeScene(ViewAction action){
        if (action == ViewAction.HOME){
            goHome();
        } else if (action == ViewAction.BACK){
            goBack();
        }
    }

    public static void popUp(String message) {
        popUp(message, 300);
    }

    public static void popUp(String s, int i) {
        Stage stage = new Stage();
        stage.setTitle("ALERT");
        Scene scene = new Scene(new StackPane(new Label(s)), i, 100);
        stage.setScene(scene);
        stage.show();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> stage.close()));
        timeline.play();
    }


    public static void logout(){
        try {
            GuiManager.setUp("sign_in.fxml");
        } catch (IOException e) {
            GuiManager.exception(e);
        }
    }

    public static void exception(Exception e) {
        popUp("Something wrong\n\t" + e.getMessage(), e.getMessage().length() + 400);
    }

    public static boolean acceptPopUp(Label l, String title) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle(title);
        Button acceptButton = new Button("Accept");
        Button refuseButton = new Button("Refuse");

        Label label = new Label(l.getText());
        label.setAlignment(Pos.CENTER_LEFT);

        //set up return value
        AtomicBoolean ret = new AtomicBoolean(false);

        acceptButton.setOnAction(e -> {
            popup.close();
            ret.set(true);
        });
        refuseButton.setOnAction(e -> {
            popup.close();
            ret.set(false);
        });

        HBox buttons = new HBox(acceptButton, refuseButton);
        buttons.setSpacing(25);
        buttons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(label, buttons);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(50);

        Scene scene = new Scene(layout, 300, 200);
        scene.getStylesheets().add(Objects.requireNonNull(GuiManager.class.getResource("Style.css")).toExternalForm());
        popup.setScene(scene);
        popup.showAndWait();

        return ret.get();
    }

    public static void showApplies(VBox vBox) {
        JobApplierGraphic graphic = new JobApplierGraphic();
        JobApplierBean bean = graphic.getBean();
        vBox.getChildren().clear();
        vBox.getStylesheets().add(Objects.requireNonNull(GuiManager.class.getResource("Style.css")).toExternalForm());
        HBox[] hBoxes;
        Button[] buttons;

        int boxWidth = 350;
        try {
            graphic.pullAppliances(GuiStarter.getUser());
            List<ShiftApply> applyList = bean.getShiftApplyList();
            if (applyList != null){
                int size = applyList.size();
                Label[] information = new Label[size];
                hBoxes = new HBox[size];
                buttons = new Button[size];
                int i = 0;
                for (ShiftApply apply:
                        applyList) {
                    hBoxes[i] = new HBox();
                    if (GuiStarter.getUser().getUserType() == Macros.EMPLOYER){
                        information[i] = new Label("[SHIFT]: " + apply.getShift()+"\n[EMPLOYEE]: " + apply.getEmployee());
                        buttons[i] = new Button("MANAGE");
                    }else {
                        information[i] = new Label("[SHIFT]: " + apply.getShift() + "\n[EMPLOYER]: " + apply.getEmployer());
                        buttons[i] = new Button("DEMISE");
                    }
                    information[i].setPrefHeight(BUTTON_HEIGHT);
                    information[i].setPrefWidth(boxWidth);
                    information[i].setAlignment(Pos.CENTER_LEFT);
                    information[i].setContentDisplay(ContentDisplay.LEFT);
                    information[i].setStyle(
                            "-fx-border-color: #C7C7CC;\n" +
                                    "-fx-border-width: 1;"
                    );
                    buttons[i].setPrefHeight(BUTTON_HEIGHT);
                    buttons[i].setPrefWidth(BUTTON_WIDTH - boxWidth);
                    //ADDING CONTROL

                    Label finalLabel = information[i];
                    buttons[i].setOnAction(e -> {
                        if (acceptPopUp(finalLabel, "Want to remove?")){
                            try {
                                graphic.removeAppliance(apply, GuiStarter.getUser());
                                popUp("Application removed");
                            } catch (Exception ex) {
                                popUp("The application is sent to Assistant");
                            }
                        }
                    });
                    hBoxes[i].getChildren().add(information[i]);
                    hBoxes[i].getChildren().add(buttons[i]);
                    vBox.getChildren().add(hBoxes[i]);
                }
            }
        } catch (FileNotFoundException e) {
            GuiManager.exception(e);
        }
    }
}
