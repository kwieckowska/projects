package org.example.myapp.Buttons;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class LanguageComboBox {
    private ComboBox<String> languageComboBox;
    public Pane createLanguageSelectionPane() {
        Label languageLabel = new Label("Oryginalny język filmu:");

        languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll(
                "Angielski",
                "Polski",
                "Hiszpański",
                "Francuski",
                "Niemiecki",
                "Włoski",
                "Japoński",
                "Chiński",
                "Koreański",
                "Rosyjski"
        );
        languageComboBox.setPromptText("Wybierz język");
        languageComboBox.setStyle("-fx-font-size: 14px;");

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(languageLabel, languageComboBox);

        vBox.setStyle("-fx-padding: 20px; -fx-background-color: #0d253f;");
        vBox.setLayoutX(15);
        vBox.setLayoutY(20);

        return vBox;
    }

    public String getValue() {
        String language = languageComboBox.getValue();

        Map<String, String> languageMap = new HashMap<>();
        languageMap.put("Angielski", "en");
        languageMap.put("Polski", "pl");
        languageMap.put("Hiszpański", "es");
        languageMap.put("Francuski", "fr");
        languageMap.put("Niemiecki", "de");
        languageMap.put("Włoski", "it");
        languageMap.put("Japoński", "ja");
        languageMap.put("Chiński", "zh");
        languageMap.put("Koreański", "ko");
        languageMap.put("Rosyjski", "ru");

        return languageMap.getOrDefault(language, "");
    }
}
