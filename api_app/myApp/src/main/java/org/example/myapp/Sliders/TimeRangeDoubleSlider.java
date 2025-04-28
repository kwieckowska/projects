package org.example.myapp.Sliders;

import org.controlsfx.control.RangeSlider;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

public class TimeRangeDoubleSlider {

    private RangeSlider rangeSlider;
    public Pane createDoubleSlider(double minHours, double maxHours, double initialLower, double initialUpper) {

        VBox container = new VBox(10);
        container.setPadding(new Insets(10));

        rangeSlider = new RangeSlider(minHours, maxHours, initialLower, initialUpper);
        rangeSlider.setShowTickMarks(true);
        rangeSlider.setShowTickLabels(true);
        rangeSlider.setMajorTickUnit(1);
        rangeSlider.setMinorTickCount(1);
        rangeSlider.setBlockIncrement(0.5);

        Label rangeLabel = new Label("Czas trwania filmu: " + formatTime(initialLower) + " - " + formatTime(initialUpper) + " godzin");

        rangeSlider.lowValueProperty().addListener((obs, oldVal, newVal) ->
                rangeLabel.setText("Czas trwania filmu: " + formatTime(rangeSlider.getLowValue()) +
                        " - " + formatTime(rangeSlider.getHighValue()) + " godzin")
        );

        rangeSlider.highValueProperty().addListener((obs, oldVal, newVal) ->
                rangeLabel.setText("Czas trwania filmu: " + formatTime(rangeSlider.getLowValue()) +
                        " - " + formatTime(rangeSlider.getHighValue()) + " godzin")
        );

        // Dodanie elementów do VBox
        container.getChildren().addAll(rangeLabel, rangeSlider);

        // Zwracanie VBox jako Pane
        return container;
    }

    // Metoda formatująca wartości numeryczne na godziny i minuty
    private String formatTime(double time) {
        int hours = (int) time; // Całkowita liczba godzin
        int minutes = (int) ((time - hours) * 60); // Pozostałe minuty
        return String.format("%02d:%02d", hours, minutes);
    }

    // Zwrócenie wartości lowValue w minutach
    public int getLowValue() {
        // Zamieniamy godziny na minuty i dodajemy pozostałe minuty
        int hours = (int) rangeSlider.getLowValue();
        int minutes = (int) ((rangeSlider.getLowValue() - hours) * 60);
        return hours * 60 + minutes;
    }

    // Zwrócenie wartości highValue w minutach
    public int getHighValue() {
        // Zamieniamy godziny na minuty i dodajemy pozostałe minuty
        int hours = (int) rangeSlider.getHighValue();
        int minutes = (int) ((rangeSlider.getHighValue() - hours) * 60);
        return hours * 60 + minutes;
    }
}