package org.example.myapp.MainComponents;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.example.myapp.Filters.RekomendacjaFilmow;


import java.util.List;
import java.util.Map;

public class MovieListView {

    private final ObservableList<String> movieList = FXCollections.observableArrayList();
    private final ListView<String> listView = new ListView<>(movieList);
    private List<Integer> movieIds;
    private final java.util.function.Consumer<Map<String, String>> onMovieSelected;

    public MovieListView(java.util.function.Consumer<Map<String, String>> onMovieSelected) {
        this.onMovieSelected = onMovieSelected;
    }

    public ListView<String> createMovieList() {
        listView.setCellFactory(param -> new ListCell<>() {
            private final Text text;

            {
                text = new Text();
                text.setWrappingWidth(250);
                setPrefWidth(0);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    text.setText(item);
                    setGraphic(text);
                }
            }
        });

        listView.setOnMouseClicked(this::handleMovieClick);
        return listView;
    }

    public void updateMovieList(ObservableList<String> movies, List<Integer> ids) {
        movieList.setAll(movies);
        movieIds = ids;
    }

    private void handleMovieClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < movieIds.size()) {
                int movieId = movieIds.get(selectedIndex);
                RekomendacjaFilmow rekomendacjaFilmow = new RekomendacjaFilmow();
                Map<String, String> movieDetails = rekomendacjaFilmow.getMovieDetailsById(movieId);

                if (movieDetails != null && !movieDetails.isEmpty()) {
                    onMovieSelected.accept(movieDetails);
                } else {
                    System.out.println("Nie znaleziono szczegółów dla filmu o ID: " + movieId);
                }
            }
        }
    }

}