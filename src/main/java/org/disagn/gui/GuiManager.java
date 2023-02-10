package org.disagn.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class used to change scene throw fxml-views
 */

public class GuiManager {

    public static final  int WEIGHT = 1024;
    public static final int HEIGHT = 720;
    public static final double BUTTON_WIDTH = 500;
    public static final int BUTTON_HEIGHT = 100;
    private static Scene home = null;
    private static Scene now;
    private static Scene prev = null;

    private static Stage stage;

    private GuiManager(){
        //this is singleton
    }

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
        Scene scene = new Scene(new StackPane(new Label(s)), i + 200, 400);
        scene.getStylesheets().add(Objects.requireNonNull(GuiManager.class.getResource("Style.css")).toExternalForm());
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
        popUp("Something wrong\n\t" + e.getMessage(),  600);
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

}
