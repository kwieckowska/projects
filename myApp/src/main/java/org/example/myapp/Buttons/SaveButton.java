package org.example.myapp.Buttons;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import org.example.myapp.Filters.PdfExporter;
import org.example.myapp.Filters.RekomendacjaFilmow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SaveButton {

    private final RekomendacjaFilmow rekomendacjaFilmow;
    private final List<Integer> movieIds;

    public SaveButton(RekomendacjaFilmow rekomendacjaFilmow, List<Integer> movieIds) {
        this.rekomendacjaFilmow = rekomendacjaFilmow;
        this.movieIds = movieIds;
    }

    public Button createButton2() {
        Button button = new Button("Zapisz");
        button.setOnAction(event -> {
            if (movieIds.isEmpty()) return;

            List<Map<String, String>> movieDetailsList = new ArrayList<>();
            for (Integer movieId : movieIds) {
                Map<String, String> movieDetails = rekomendacjaFilmow.getMovieDetailsById(movieId);
                String actorList = movieDetails.getOrDefault("Actors", "Brak danych");
                String[] actorArray = actorList.split(", ");
                if (actorArray.length > 5) {
                    actorList = String.join(", ", actorArray[0], actorArray[1], actorArray[2], actorArray[3], actorArray[4]) + " (and others)";
                }
                movieDetails.put("Actors", actorList);
                movieDetailsList.add(movieDetails);
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Zapisz jako");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pliki PDF", "*.pdf"));
            fileChooser.setInitialFileName("ListaFilmów.pdf");
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                PdfExporter.saveMoviesToPdf(movieDetailsList, file.getAbsolutePath());
                System.out.println("Zapisano plik PDF w lokalizacji: " + file.getAbsolutePath());
            }
        });
        return button;
    }
}