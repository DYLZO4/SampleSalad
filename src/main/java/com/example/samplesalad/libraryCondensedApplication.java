package com.example.samplesalad;

import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

import static javafx.application.Application.launch;

public class libraryCondensedApplication extends Application {

    Stage stage = new Stage();

    //@Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(libraryCondensedApplication.class.getResource("library-condensed.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();


    }
    public static void main(String[] args) {
        launch();
    }

    public void expand(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(libraryCondensedApplication.class.getResource("library-condensed.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
