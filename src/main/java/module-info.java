module com.example.samplesalad {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;


    opens com.example.samplesalad to javafx.fxml;
    exports com.example.samplesalad;
    exports com.example.samplesalad.controller;
    exports com.example.samplesalad.model;
    opens com.example.samplesalad.controller to javafx.fxml;
    exports com.example.samplesalad.model.user;
    exports com.example.samplesalad.model.DAO;
    exports com.example.samplesalad.model.service;
}