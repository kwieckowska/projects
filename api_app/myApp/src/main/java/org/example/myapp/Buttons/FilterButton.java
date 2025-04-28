package org.example.myapp.Buttons;

import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.myapp.Filters.RekomendacjaFilmow;
import org.example.myapp.MainComponents.MovieListView;
import org.example.myapp.Sliders.DateDoubleSlider;
import org.example.myapp.Sliders.TimeRangeDoubleSlider;
import org.example.myapp.TextFields.ActorTextField;
import org.example.myapp.TextFields.DirectorTextField;

import java.util.List;

public class FilterButton {
    private final GenreCheckBoxes genreCheckBoxes;
    private final DateDoubleSlider dateDoubleSlider;
    private final TimeRangeDoubleSlider timeRangeDoubleSlider;
    private final LanguageComboBox languageComboBox;
    private final SortingRadioButtons sortingRadioButtons;
    private final DirectorTextField directorTextField;
    private final ActorTextField actorTextField;
    private final RekomendacjaFilmow rekomendacjaFilmow;
    private final MovieListView movieListView;
    private final List<Integer> filteredMovieIds;

    public FilterButton(
            GenreCheckBoxes genreCheckBoxes,
            DateDoubleSlider dateDoubleSlider,
            TimeRangeDoubleSlider timeRangeDoubleSlider,
            LanguageComboBox languageComboBox,
            SortingRadioButtons sortingRadioButtons,
            DirectorTextField directorTextField,
            ActorTextField actorTextField,
            MovieListView movieListView,
            List<Integer> filteredMovieIds
    ) {
        this.genreCheckBoxes = genreCheckBoxes;
        this.dateDoubleSlider = dateDoubleSlider;
        this.timeRangeDoubleSlider = timeRangeDoubleSlider;
        this.languageComboBox = languageComboBox;
        this.sortingRadioButtons = sortingRadioButtons;
        this.directorTextField = directorTextField;
        this.actorTextField = actorTextField;
        this.rekomendacjaFilmow = new RekomendacjaFilmow();
        this.movieListView = movieListView;
        this.filteredMovieIds = filteredMovieIds;
    }

    public Button createButton1() {
        Button button = new Button("Filtruj");

        button.setOnAction(event -> {
            handleFilterAction(genreCheckBoxes, directorTextField, actorTextField, dateDoubleSlider,
                    timeRangeDoubleSlider, languageComboBox, sortingRadioButtons);
        });

        return button;
    }

    private void handleFilterAction(
            GenreCheckBoxes checkBoxPanel, DirectorTextField directorField,
            ActorTextField actorField, DateDoubleSlider dateDoubleSlider,
            TimeRangeDoubleSlider timeRangeDoubleSlider, LanguageComboBox languageComboBox,
            SortingRadioButtons radioButtons) {

        String genres = checkBoxPanel.getSelectedGenres();
        List<String> directors = directorField.getDirectorsNamesList();
        List<String> actors = actorField.getActorNamesList();
        String startYear = dateDoubleSlider.getLowValue();
        String endYear = dateDoubleSlider.getHighValue();
        int minRuntime = timeRangeDoubleSlider.getLowValue();
        int maxRuntime = timeRangeDoubleSlider.getHighValue();
        String language = languageComboBox.getValue();
        String sortBy = radioButtons.getValue();
        Boolean includeAdult = checkBoxPanel.includeAdult();

        filteredMovieIds.clear();
        filteredMovieIds.addAll(rekomendacjaFilmow.getMoviesByFilters(
                genres, startYear, endYear, minRuntime, maxRuntime, includeAdult,
                language, sortBy, directors, actors
        ));

        List<Integer> movieIds = rekomendacjaFilmow.getMoviesByFilters(
                genres, startYear, endYear, minRuntime, maxRuntime, includeAdult,
                language, sortBy, directors, actors);

        ObservableList<String> movieTitles = FXCollections.observableArrayList();
        for (int movieId : movieIds) {
            String title = rekomendacjaFilmow.getMovieTitleById(movieId);
            movieTitles.add(title);
        }
        movieListView.updateMovieList(movieTitles, movieIds);
    }
}