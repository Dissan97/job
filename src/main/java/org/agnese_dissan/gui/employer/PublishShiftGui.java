package org.agnese_dissan.gui.employer;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.agnese_dissan.graphicControllers.ShiftPublisherGraphic;
import org.agnese_dissan.gui.GuiManager;
import org.agnese_dissan.gui.GuiStarter;
import org.agnese_dissan.gui.ViewAction;

public class PublishShiftGui {
    @FXML
    public TextField jobName;
    @FXML
    public TextField jobPlace;
    @FXML
    public TextField jobDate;
    @FXML
    public TextField description;
    @FXML
    public TextField jobTime;


    @FXML
    public void goBack() {
        GuiManager.changeScene(ViewAction.BACK);
    }

    @FXML
    public void goHome() {
        GuiManager.changeScene(ViewAction.HOME);
    }

    @FXML
    public void publish() {
        ShiftPublisherGraphic controller = new ShiftPublisherGraphic();

        String name = this.jobName.getText();
        String place = this.jobPlace.getText();
        String date = this.jobDate.getText();
        String time = this.jobTime.getText();
        String description = this.description.getText();


        try {
            String dateTime = controller.setDateTime(date, time);
            if (controller.isGood(name)
                    && controller.isGood(place)
                    && controller.isGood(date)
                    && controller.isGood(time)
            ) {
                if (description.equals("")){
                    description = "Job appliance";
                }
                controller.publishShift(GuiStarter.getUser().getUsername(), name, place, dateTime, description);
                GuiManager.popUp("Shift published");
                this.clear();
            }
        } catch (Exception e) {
            GuiManager.exception(e);
        }

    }

    private void clear(){
        this.jobName.setText("");
        this.jobPlace.setText("");
        this.jobDate.setText("");
        this.jobTime.setText("");
        this.description.setText("");

    }

}
