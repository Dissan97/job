package org.agnese_dissan.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.agnese_dissan.interfaces.JobView;

public class StartGuiUi extends Application implements JobView {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("FAST JOB");
        GuiManager.setStage(stage);
        GuiManager.setUp("sign_in.fxml");
    }

    @Override
    public void startUi() {
        launch();
    }
}
