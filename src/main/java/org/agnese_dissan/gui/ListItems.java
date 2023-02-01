package org.agnese_dissan.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ListItems {
    Button[] buttons;
    Label[] labels;
    HBox[] hBoxes;
    public void setButtons(Button[] buttons) {
        this.buttons = buttons;
    }

    public void setLabels(Label[] labels) {
        this.labels = labels;
    }

    public void setHBoxes(HBox[] hBoxes) {
        this.hBoxes = hBoxes;
    }

    public HBox[] getHBoxes() {
        return this.hBoxes;
    }

    public Button[] getButtons() {
        return this.buttons;
    }

    public Label[] getLabels() {
        return this.labels;
    }
}
