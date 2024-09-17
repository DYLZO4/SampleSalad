package com.example.samplesalad;

import com.example.samplesalad.model.MockUserDAO;
import com.example.samplesalad.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

/**
 * Main application class for launching the JavaFX application.
 * This class extends {@link Application} and sets up the primary stage with a custom style.
 */
public class SampleSaladApplication extends Application {
    private double x, y = 0;

    /**
     * Default constructor for the {@code HelloApplication} class.
     */
    public SampleSaladApplication(){}
    /**
     * Starts the JavaFX application by setting up the primary stage.
     * Loads the FXML file to create the scene, applies an undecorated style to the stage,
     * and sets up drag functionality to move the window.
     *
     * @param primaryStage The primary stage for this application, onto which the application scene is set.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-view.fxml")));
        primaryStage.initStyle(StageStyle.UNDECORATED);


        // Set up event handlers for dragging the window
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });

        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();

        MockUserDAO testDAO = new MockUserDAO();
        User user1 = new User("Simon", "Thomas", "password", "fakeemail@email", "04444444440");
        User user2 = new User("Simone", "Thompson", "passnot", "realemail@email", "0444111111");
        testDAO.add(user1);
        testDAO.add(user2);
        testDAO.delete(user1);
        testDAO.add(user1);
    }

    /**
     * The main entry point for the JavaFX application.
     * Launches the application by invoking the {@code launch} method.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);

    }
}