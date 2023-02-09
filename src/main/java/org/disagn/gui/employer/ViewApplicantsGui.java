package org.disagn.gui.employer;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.disagn.beans.AccountBean;
import org.disagn.beans.JobApplierBean;
import org.disagn.graphics.ApplicationAcceptorGraphic;
import org.disagn.gui.GuiManager;
import org.disagn.gui.GuiStarter;
import org.disagn.gui.ViewAction;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.User;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

public class ViewApplicantsGui {

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
        ApplicationAcceptorGraphic acceptorController = new ApplicationAcceptorGraphic();
        try {
            acceptorController.getUserData(GuiStarter.user);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        AccountBean accountBean = acceptorController.getAccountBean();
        JobApplierBean applierBean = acceptorController.getApplierBean();
        List<ShiftApply> applyList = applierBean.getShiftApplyList();
        List<User> userList = accountBean.getListEmployees();

        if (applyList != null){
            this.buildList(applyList, userList);
        }
        //Build user list by passing this listUser

        }

    private void buildList(List<ShiftApply> applyList, List<User> userList) {
        this.vBox.getChildren().clear();
        User[] users;
        Button[] buttons;
        Label[] labels;
        HBox[] hBoxes;

        int size = applyList.size();

        users = new User[size];
        buttons = new Button[size];
        labels = new Label[size];
        hBoxes = new HBox[size];

        int i = 0;
        for (ShiftApply apply:
        applyList) {
            labels[i] = new Label("Shift : [" + apply.getShift() + "]\nEmployee : [" + apply.getEmployee() + "]");
            buttons[i] = new Button("INFO");
            ShiftApply tempApply = null;
            for (User user:
                 userList) {
                if (user.getUsername().equals(apply.getEmployee())){
                    users[i] = user;
                    tempApply = apply;
                }
            }

            User finalUser = users[i];


            ShiftApply finalApply = tempApply;
            buttons[i].setOnAction(e ->
                this.popUserPopUpWindow(finalUser, finalApply)
            );

            hBoxes[i] = new HBox();
            hBoxes[i].getChildren().add(labels[i]);
            hBoxes[i].getChildren().add(buttons[i]);
            this.vBox.getChildren().add(hBoxes[i]);
            i++;
        }

    }

    private void popUserPopUpWindow(User user, ShiftApply apply){
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("CANDIDATE INFO");
        Button acceptButton = new Button("Accept");
        Button refuseButton = new Button("Refuse");

        ApplicationAcceptorGraphic controller = new ApplicationAcceptorGraphic();
        acceptButton.setOnAction(e -> {
            try {
                controller.manageCandidate(user, GuiStarter.user, apply, true);
                GuiManager.popUp("Appliance accepted");
            } catch (Exception ex) {
                GuiManager.exception(ex);
            }
            popup.close();
        });
        refuseButton.setOnAction(e -> {
            try {
                controller.manageCandidate(user, GuiStarter.user, apply, false);
                GuiManager.popUp("Appliance cancelled");
            } catch (Exception ex) {
                GuiManager.exception(ex);
            }
            popup.close();
        });

        Label name = new Label("Name: " + user.getUsername());
        Label surname = new Label("Surname: " + user.getSurname());
        Label dateOfBirth = new Label("Date of Birth: "+ user.getDateOfBirth());
        Label cityOfBirth = new Label("City of Birth: " + user.getCityOfBirth());

        VBox root = new VBox();
        root.getChildren().add(name);
        root.getChildren().add(surname);
        root.getChildren().add(dateOfBirth);
        root.getChildren().add(cityOfBirth);

        HBox hBox = new HBox();
        hBox.getChildren().add(acceptButton);
        hBox.getChildren().add(refuseButton);

        root.getChildren().add(hBox);

        Scene scene = new Scene(root,700, 700);
        scene.getStylesheets().add(Objects.requireNonNull(GuiManager.class.getResource("Style.css")).toExternalForm());
        popup.setScene(scene);
        popup.showAndWait();


    }

}
