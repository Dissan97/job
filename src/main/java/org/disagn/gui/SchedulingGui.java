package org.disagn.gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import org.disagn.Macros;
import org.disagn.beans.ShiftSchedulerBean;
import org.disagn.graphicControllers.ShiftSchedulerGraphic;
import org.disagn.models.job.ShiftApply;

import java.util.List;

public class SchedulingGui {

    @FXML
    public VBox vBox;

    @FXML
    public MenuButton menuButton;

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

        ShiftSchedulerGraphic controller = new ShiftSchedulerGraphic(GuiStarter.getUser());
        controller.setUpDates();
        ShiftSchedulerBean bean = controller.getBean();
        List<String> dateList = bean.getDateList();

        for (String date:
             dateList) {
            MenuItem item = new MenuItem(date);

            item.setOnAction(e-> {
                String scheduleDate = item.getText();
                this.schedulePuller(controller, bean, scheduleDate);
            });

            this.menuButton.getItems().add(item);
        }

        schedulePuller(controller, bean,dateList.get(0));

    }

    void schedulePuller(ShiftSchedulerGraphic controller, ShiftSchedulerBean bean,String date){
        this.vBox.getChildren().clear();
        controller.pullSchedules(date, GuiStarter.getUser());
        List<ShiftApply> scheduleList = bean.getSchedules();
        int size = scheduleList.size();


        String actor = "Employee: ";
        Macros type = GuiStarter.getUser().getUserType();
        if (type == Macros.EMPLOYEE){
            actor = "Employer: ";
        }


        Label[] labels = new Label[size];
        int i = 0;
        for (ShiftApply apply:
             scheduleList) {
            String username = apply.getEmployee();
            if (type == Macros.EMPLOYEE){
                username = apply.getEmployer();
            }
            labels[i] = new Label("Appliance: " + apply + "\n" + actor + username);
            labels[i].setPrefHeight(GuiManager.BUTTON_HEIGHT);
            labels[i].setPrefWidth(GuiManager.BUTTON_WIDTH);
            labels[i].setAlignment(Pos.CENTER_LEFT);
            labels[i].setContentDisplay(ContentDisplay.LEFT);
            vBox.getChildren().add(labels[i]);
        }

    }


}
