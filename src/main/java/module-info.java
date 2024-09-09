module com.example.samplesalad {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.samplesalad to javafx.fxml;
    exports com.example.samplesalad;
    exports com.example.samplesalad.controller;
    exports com.example.samplesalad.model;
    opens com.example.samplesalad.controller to javafx.fxml;
}