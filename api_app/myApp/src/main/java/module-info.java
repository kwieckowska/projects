module org.example.myapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires json;
    requires itextpdf;

    exports org.example.myapp.TextFields;
    opens org.example.myapp.TextFields to javafx.fxml;
    exports org.example.myapp.Sliders;
    opens org.example.myapp.Sliders to javafx.fxml;
    exports org.example.myapp.Buttons;
    opens org.example.myapp.Buttons to javafx.fxml;
    exports org.example.myapp.MainComponents;
    opens org.example.myapp.MainComponents to javafx.fxml;
}