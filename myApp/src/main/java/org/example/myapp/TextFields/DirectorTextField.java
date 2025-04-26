package org.example.myapp.TextFields;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectorTextField {
    private TextField directorField;
    public Pane createDirectorInputPane() {

        directorField = new TextField();
        directorField.setPromptText("Wpisz imię i nazwisko reżysera (różne osoby po przecinku)");
        directorField.setStyle("-fx-font-size: 14px;");

        VBox vBox = new VBox(10);
        vBox.getChildren().add(directorField);

        vBox.setStyle("-fx-padding: 20px; -fx-background-color: #0d253f;");
        vBox.setLayoutX(15);
        vBox.setLayoutY(10);

        return vBox;
    }


    public List<String> getDirectorsNamesList() {
        String text = directorField.getText();
        if (text != ""){
            String[] directorsArray = text.split("\\s*,\\s*");
            return Arrays.asList(directorsArray);
        }
        else {
            return new ArrayList<>();
        }
    }

}