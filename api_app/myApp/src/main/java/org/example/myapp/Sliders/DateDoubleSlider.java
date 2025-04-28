package org.example.myapp.Sliders;

import org.controlsfx.control.RangeSlider;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

public class DateDoubleSlider {

    private RangeSlider rangeSlider;
    public Pane createDoubleSlider2(double startYear, double endYear, double initialLower, double initialUpper) {

        VBox container = new VBox(10);
        container.setPadding(new Insets(10));

        rangeSlider = new RangeSlider(startYear, endYear, initialLower, initialUpper);
        rangeSlider.setShowTickMarks(true);
        rangeSlider.setShowTickLabels(true);
        rangeSlider.setMajorTickUnit(10);
        rangeSlider.setMinorTickCount(0);
        rangeSlider.setBlockIncrement(10);

        Label rangeLabel = new Label("Rok nakręcenia: " + (int) initialLower + " - " + (int) initialUpper);

        rangeSlider.lowValueProperty().addListener((obs, oldVal, newVal) ->
                rangeLabel.setText("Rok nakręcenia: " + (int) rangeSlider.getLowValue() +
                        " - " + (int) rangeSlider.getHighValue())
        );

        rangeSlider.highValueProperty().addListener((obs, oldVal, newVal) ->
                rangeLabel.setText("Rok nakręcenia: " + (int) rangeSlider.getLowValue() +
                        " - " + (int) rangeSlider.getHighValue())
        );

        container.getChildren().addAll(rangeLabel, rangeSlider);

        return container;
    }
    public String getLowValue() {
        return String.valueOf((int) rangeSlider.getLowValue());
    }

    public String getHighValue() {
        return String.valueOf((int) rangeSlider.getHighValue());
    }

}