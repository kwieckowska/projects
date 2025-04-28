package org.example.myapp.TextFields;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.List;

public class ActorTextField {
    private TextField actorField;
    public Pane createActorInputPane() {

        actorField = new TextField();
        actorField.setPromptText("Wpisz imię i nazwisko aktora (różne osoby po przecinku)");
        actorField.setStyle("-fx-font-size: 14px;");

        VBox vBox = new VBox(10);
        vBox.getChildren().add(actorField);

        vBox.setStyle("-fx-padding: 20px; -fx-background-color: #0d253f;");
        vBox.setLayoutX(15);
        vBox.setLayoutY(-100);

        return vBox;
    }

    public List<String> getActorNamesList() {
        String text = actorField.getText();
        String[] actorsArray = text.split("\\s*,\\s*");
        return Arrays.asList(actorsArray);
    }


}