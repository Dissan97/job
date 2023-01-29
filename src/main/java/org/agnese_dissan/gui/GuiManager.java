package org.agnese_dissan.gui;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class used to change scene throw fxml-views
 */

public class GuiManager {

    public final static int WEIGHT = 1024, HEIGHT = 720;
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


        FXMLLoader fxmlLoader = new FXMLLoader(StartGuiUi.class.getResource(name));
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
        FXMLLoader fxmlLoader = new FXMLLoader(StartGuiUi.class.getResource(name));
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

}
