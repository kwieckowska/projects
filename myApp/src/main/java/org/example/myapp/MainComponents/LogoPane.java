package org.example.myapp.MainComponents;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LogoPane {
    public Pane createLogoPane() {
        Pane mainPane = new Pane();

        Rectangle rectangle = new Rectangle(220, 60, Color.WHITE);
        rectangle.setArcWidth(30);
        rectangle.setArcHeight(30);
        rectangle.setLayoutX(20);
        rectangle.setLayoutY(15);

        Image image = new Image(getClass().getResource("/images/logo.jpg").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setLayoutX(30);
        imageView.setLayoutY(32);

        mainPane.getChildren().addAll(rectangle, imageView);
        return mainPane;
    }
}
