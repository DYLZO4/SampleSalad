module com.example.samplesalad {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.samplesalad to javafx.fxml;
    exports com.example.samplesalad;
}