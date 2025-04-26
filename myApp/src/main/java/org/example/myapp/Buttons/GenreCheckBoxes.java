package org.example.myapp.Buttons;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GenreCheckBoxes {

    CheckBox cb1 = new CheckBox("Akcja");
    CheckBox cb2 = new CheckBox("Animacja");
    CheckBox cb3 = new CheckBox("Dokumentalny");
    CheckBox cb4 = new CheckBox("Dramat");
    CheckBox cb5 = new CheckBox("Familijny");
    CheckBox cb6 = new CheckBox("Fantasy");
    CheckBox cb7 = new CheckBox("Historyczny");
    CheckBox cb8 = new CheckBox("Horror");
    CheckBox cb9 = new CheckBox("Komedia");
    CheckBox cb10 = new CheckBox("Kryminał");
    CheckBox cb11 = new CheckBox("Muzyczny");
    CheckBox cb12 = new CheckBox("Przygodowy");
    CheckBox cb13 = new CheckBox("Romans");
    CheckBox cb14 = new CheckBox("Sci-Fi");
    CheckBox cb15 = new CheckBox("Tajemnica");
    CheckBox cb16 = new CheckBox("Thriller");
    CheckBox cb17 = new CheckBox("Western");
    CheckBox cb18 = new CheckBox("Wojenny");
    CheckBox cb19 = new CheckBox("film TV");
    CheckBox cb20 = new CheckBox("dla dzieci");
    public Pane createCheckBoxes() {



        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);


        CheckBox[] checkBoxes = {cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9, cb10, cb11, cb12, cb13, cb14, cb15, cb16, cb17, cb18, cb19, cb20};
        int columnCount = 5;
        for (int i = 0; i < checkBoxes.length; i++) {
            int row = i / columnCount;
            int col = i % columnCount;
            gridPane.add(checkBoxes[i], col, row);
        }

        gridPane.setMaxWidth(400);
        gridPane.setMaxHeight(200);


        gridPane.setLayoutX(22);
        gridPane.setLayoutY(25);

        Pane pane = new Pane();
        pane.getChildren().add(gridPane);

        return pane;
    }

    public String getSelectedGenres() {
        List<String> selectedGenres = new ArrayList<>();

        if (cb1.isSelected()) selectedGenres.add("action");
        if (cb2.isSelected()) selectedGenres.add("animation");
        if (cb3.isSelected()) selectedGenres.add("documentary");
        if (cb4.isSelected()) selectedGenres.add("drama");
        if (cb5.isSelected()) selectedGenres.add("family");
        if (cb6.isSelected()) selectedGenres.add("fantasy");
        if (cb7.isSelected()) selectedGenres.add("historical");
        if (cb8.isSelected()) selectedGenres.add("horror");
        if (cb9.isSelected()) selectedGenres.add("comedy");
        if (cb10.isSelected()) selectedGenres.add("crime");
        if (cb11.isSelected()) selectedGenres.add("musical");
        if (cb12.isSelected()) selectedGenres.add("adventure");
        if (cb13.isSelected()) selectedGenres.add("romance");
        if (cb14.isSelected()) selectedGenres.add("science fiction");
        if (cb15.isSelected()) selectedGenres.add("mystery");
        if (cb16.isSelected()) selectedGenres.add("thriller");
        if (cb17.isSelected()) selectedGenres.add("western");
        if (cb18.isSelected()) selectedGenres.add("war");
        if (cb19.isSelected()) selectedGenres.add("tv movie");

        return String.join(", ", selectedGenres);
    }  // od razu mozna wstawic wynik tego do funkcji filter

    public boolean includeAdult() {
        return !cb20.isSelected();
    }  // wynik od razu wstawiamy do funkcji filtrujacej
    // jesli zaznaczony - nie chcemy



}
