package org.example.myapp.MainComponents;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.myapp.Buttons.*;
import org.example.myapp.Filters.RekomendacjaFilmow;
import org.example.myapp.Sliders.DateDoubleSlider;
import org.example.myapp.Sliders.TimeRangeDoubleSlider;
import org.example.myapp.TextFields.ActorTextField;
import org.example.myapp.TextFields.DirectorTextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App extends Application {

    private BorderPane root;
    private VBox centerPane;
    private VBox detailsPane;

    public final List<Integer> filteredMovieIds = new ArrayList<>();
    private final RekomendacjaFilmow rekomendacjaFilmow = new RekomendacjaFilmow();
    private final MovieListView movieListView = new MovieListView(this::showMovieDetails);

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        ListView<String> movieListControl = movieListView.createMovieList();
        root.setLeft(movieListControl);

        centerPane = createMainComponentsPane();
        root.setCenter(centerPane);

        detailsPane = new VBox(10);
        detailsPane.setStyle("-fx-padding: 10;");

        Scene scene = new Scene(root, 825, 650);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setResizable(false);
        primaryStage.setTitle("TMDB App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createMainComponentsPane() {
        VBox centerPane = new VBox(0);

        LogoPane logoPane = new LogoPane();
        centerPane.getChildren().add(logoPane.createLogoPane());

        GenreCheckBoxes checkBoxPanel = new GenreCheckBoxes();
        centerPane.getChildren().add(checkBoxPanel.createCheckBoxes());

        VBox typeTextField = new VBox(-20);
        DirectorTextField directorField = new DirectorTextField();
        typeTextField.getChildren().add(directorField.createDirectorInputPane());
        ActorTextField actorField = new ActorTextField();
        typeTextField.getChildren().add(actorField.createActorInputPane());
        centerPane.getChildren().add(typeTextField);

        VBox sliderField = new VBox(-20);
        TimeRangeDoubleSlider doubleSlider1 = new TimeRangeDoubleSlider();
        sliderField.getChildren().add(doubleSlider1.createDoubleSlider(0, 10, 0, 10));
        DateDoubleSlider doubleSlider2 = new DateDoubleSlider();
        sliderField.getChildren().add(doubleSlider2.createDoubleSlider2(1940, 2030, 1940, 2030));
        centerPane.getChildren().add(sliderField);

        HBox hbox1 = new HBox();
        LanguageComboBox languageComboBox = new LanguageComboBox();
        hbox1.getChildren().add(languageComboBox.createLanguageSelectionPane());

        VBox radioButtonField = new VBox(-20);
        SortingRadioButtons radioButtons = new SortingRadioButtons();
        radioButtonField.getChildren().add(radioButtons.createRadioButtonsPane());
        hbox1.getChildren().add(radioButtonField);

        VBox regBoxes = new VBox(10);
        FilterButton filterButton = new FilterButton(checkBoxPanel, doubleSlider2, doubleSlider1,
                languageComboBox, radioButtons, directorField, actorField, movieListView, filteredMovieIds);
        Button filterButton1 = filterButton.createButton1();
        SaveButton saveButton = new SaveButton(rekomendacjaFilmow, filteredMovieIds);
        Button savePdfButton = saveButton.createButton2();

        regBoxes.getChildren().addAll(filterButton1, savePdfButton);
        hbox1.getChildren().add(regBoxes);

        centerPane.getChildren().add(hbox1);

        return centerPane;
    }

    public void showMovieDetails(Map<String, String> movieDetails) {
        detailsPane.getChildren().clear();

        String title = movieDetails.getOrDefault("Title", "Nieznany film");
        String year = movieDetails.getOrDefault("Release Year", "Brak roku");
        String description = movieDetails.getOrDefault("Overview", "Brak opisu");
        String posterUrl = movieDetails.getOrDefault("Poster", "");

        javafx.scene.control.Label titleLabel = new javafx.scene.control.Label("Tytuł: " + title);
        titleLabel.setWrapText(true);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        titleLabel.setMaxWidth(400);

        javafx.scene.control.Label yearLabel = new javafx.scene.control.Label("Rok: " + year);

        javafx.scene.control.Label descriptionLabel = new javafx.scene.control.Label("Opis: " + description);
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px;");
        descriptionLabel.setMaxWidth(400);


        ImageView posterImageView = new ImageView();
        if (!posterUrl.isEmpty()) {
            Image posterImage = new Image(posterUrl);
            posterImageView.setImage(posterImage);
            posterImageView.setFitWidth(200);
            posterImageView.setFitHeight(300);
            posterImageView.setPreserveRatio(true);
        } else {
            posterImageView.setFitWidth(200);
            posterImageView.setFitHeight(300);
        }

        Button backButton = new Button("Wróć");
        backButton.setOnAction(event -> {
            root.setCenter(centerPane);
            detailsPane.setVisible(false);
            centerPane.setVisible(true);
        });

        HBox detailsLayout = new HBox(20);
        detailsLayout.setStyle("-fx-padding: 20;");


        VBox leftPane = new VBox(10, posterImageView);

        VBox rightPane = new VBox(10, titleLabel, yearLabel, descriptionLabel);

        VBox leftPaneWithBackButton = new VBox(10, leftPane, backButton);

        detailsLayout.getChildren().addAll(leftPaneWithBackButton, rightPane);

        root.setCenter(detailsLayout);
        centerPane.setVisible(false);
        detailsPane.setVisible(true);
    }


    public static void main(String[] args) {
        launch(args);
    }
}