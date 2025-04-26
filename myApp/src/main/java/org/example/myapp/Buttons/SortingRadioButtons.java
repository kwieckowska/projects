package org.example.myapp.Buttons;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class SortingRadioButtons {
    private ToggleGroup group;
    public Pane createRadioButtonsPane() {

        Label radioLabel = new Label("Wybierz opcję sortowania:");

        group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("oceny");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("popularność");
        rb2.setToggleGroup(group);


        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(radioLabel, rb1, rb2);

        vBox.setStyle("-fx-padding: 20px; -fx-background-color: #0d253f;");

        return vBox;
    }
    public String getValue() {
        RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();

        if (selectedRadioButton != null) {
            if (selectedRadioButton.getText().equals("oceny")) {
                return "vote_average.desc";
            } else if (selectedRadioButton.getText().equals("popularność")) {
                return "popularity.desc";
            }
        }
        return "";
    }
}
