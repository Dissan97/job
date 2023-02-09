package org.disagn.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.disagn.interfaces.JobView;

public class StartGuiUi extends Application implements JobView {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("FAST JOB");
        GuiManager.setStage(stage);
        if (GuiStarter.user == null) {
            GuiManager.setUp("sign_in.fxml");
        }
    }

    @Override
    public void startUi() {
        launch();
    }
}
