module com.example.samplesalad {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.samplesalad to javafx.fxml;
    exports com.example.samplesalad;
}