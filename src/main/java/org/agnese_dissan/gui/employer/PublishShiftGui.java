package org.agnese_dissan.gui.employer;


import javafx.scene.control.TextField;
import org.agnese_dissan.beans.ShiftBean;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.graphicControllers.ShiftPublisherGraphic;
import org.agnese_dissan.gui.GuiManager;
import org.agnese_dissan.gui.UserGuiStarter;
import org.agnese_dissan.gui.ViewAction;
import org.agnese_dissan.models.users.Employer;

public class PublishShiftGui {
    public TextField jobName;
    public TextField jobPlace;
    public TextField jobDate;
    public TextField description;
    public TextField jobTime;

    public void goBack() {
        GuiManager.changeScene(ViewAction.BACK);
    }

    public void goHome() {
        GuiManager.changeScene(ViewAction.HOME);
    }

    public void publish() {
            ShiftPublisherGraphic graphic = new ShiftPublisherGraphic();
            ShiftBean bean = graphic.getBean();
        try {
            bean.setEmployer(new Employer(UserGuiStarter.getUser()));
        } catch (InvalidDateException e) {
            GuiManager.exception(e);
        }

        if (bean.setName(this.jobName.getText()) == -1){
            GuiManager.popUp("Job name cannot be void");
        }

        if (bean.setJobPlace(this.jobPlace.getText()) == -1){
            GuiManager.popUp("Job place cannot be null");
        }

        try {
            bean.setDateTime(this.jobDate.getText(), this.jobTime.getText());
        } catch (Exception e) {
            GuiManager.popUp(e.getMessage());
        }

        bean.setDescription(this.description.getText());

        graphic.publishShift();

    }

}
