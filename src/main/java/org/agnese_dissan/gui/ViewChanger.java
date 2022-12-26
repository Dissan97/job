package org.agnese_dissan.gui;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class used to change scene throw fxml-views
 */

public class ViewChanger {

    private static Scene home = null;
    private static Scene now;
    private static Scene prev = null;
    public static Stage stage;


    /**
     * This function must be launched before all if you want to run the code
     * @param stage the stage param which appears in the start method
     */

    public static void setStage(Stage stage) {
        ViewChanger.stage = stage;
    }

    /**
     * First function needed to build the Application view
     * @param name fxml filename
     */

    public static void setUp(String name) throws IOException {
        if (home == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(StartGuiUi.class.getResource(name));
            now = new Scene(fxmlLoader.load(), 320, 240);
            home = now;
            prev = null;
        }
        stage.setScene(home);
        stage.show();
    }

    /**
     * This function is used to setUp some view using a specific fxml file
     * @param name fxml filename
     */

    public static void changeScene(String name) throws IOException {
        prev = now;
        FXMLLoader fxmlLoader = new FXMLLoader(StartGuiUi.class.getResource(name));
        now = new Scene(fxmlLoader.load(), 320, 240);
        stage.setScene(now);
        stage.show();
    }

    /**
     * Function used to go back
     */

    public static void goBack(){

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

    public static void goHome(){
        if (now != home) {
            prev = now;
            now = home;
            stage.setScene(now);
            stage.show();
        }
    }

}
