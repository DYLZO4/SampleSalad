package com.example.samplesalad.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for handling user interactions and scene transitions.
 * Implements {@link Initializable} to initialize UI components after they are loaded.
 */
public class MainController implements Initializable {

    @FXML
    private ImageView exit, menu;

    @FXML
    private AnchorPane pane1, pane2;

    @FXML
    private BorderPane bp;

    @FXML
    private AnchorPane contentPane;

    // Define a variable to track if an animation is currently running
    private boolean isAnimating = false;

    /**
     * Default constructor for the {@code HelloController} class.
     */
    public MainController (){}

    /**
     * Initializes the controller class. Sets up event handlers and transitions.
     * This method is called after the FXML file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or {@code null} if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if the root object is not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Exit button handler
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        // Initially set pane1 to be hidden and non-interactive
        pane1.setVisible(false);
        pane1.setMouseTransparent(true);

        // Create and play the fade-out transition for pane1
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeOutTransition.setFromValue(1);
        fadeOutTransition.setToValue(0);
        fadeOutTransition.play();

        // Create and play the slide transition for pane2
        TranslateTransition slideOutTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        slideOutTransition.setByX(-600);
        slideOutTransition.play();

        // Menu button click event to show pane1
        menu.setOnMouseClicked(event -> {
            if (!isAnimating) { // Check if an animation is currently running
                isAnimating = true; // Set the flag to true

                // Ensure pane1 is visible and interactive
                pane1.setVisible(true);
                pane1.setMouseTransparent(false);

                // Create and play the fade-in transition for pane1
                FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), pane1);
                fadeInTransition.setFromValue(0);
                fadeInTransition.setToValue(1);

                fadeInTransition.setOnFinished(event1 -> {
                    isAnimating = false; // Reset the flag when the animation finishes
                });
                fadeInTransition.play();

                // Create and play the slide transition for pane2
                TranslateTransition slideInTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
                slideInTransition.setByX(600);
                slideInTransition.play();
            }
        });

        // Pane1 click event to hide it
        pane1.setOnMouseClicked(event -> {
            if (!isAnimating) { // Check if an animation is currently running
                isAnimating = true; // Set the flag to true

                // Create and play the fade-out transition for pane1
                FadeTransition fadeOutTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
                fadeOutTransition1.setFromValue(1);
                fadeOutTransition1.setToValue(0);

                // Set an action to run after the fade-out transition finishes
                fadeOutTransition1.setOnFinished(event1 -> {
                    pane1.setVisible(false);
                    pane1.setMouseTransparent(true); // Make pane1 non-interactive again
                    isAnimating = false; // Reset the flag when the animation finishes
                });
                fadeOutTransition1.play();

                // Create and play the slide transition for pane2
                TranslateTransition slideOutTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
                slideOutTransition1.setByX(-600);
                slideOutTransition1.play();
            }
        });
    }

        /**
         * Handles the settings button click event. Loads settings page
         * @param event The mouse event triggered by clicking the settings button
         */
    @FXML
    private void openSettings(MouseEvent event){
        loadPage("settings");
    }

    /**
     * Handles the login button click event. Loads the login page.
     *
     * @param event The mouse event triggered by clicking the login button.
     */
    @FXML
    private void login(MouseEvent event) {
        loadPage("login");
    }

    /**
     * Handles the register button click event. Loads the registration page.
     *
     * @param event The mouse event triggered by clicking the register button.
     */
    @FXML
    private void account(MouseEvent event) {
        loadPage("account");
    }

    /**
     * Handles the condensed library button click event. Loads the condensed library page.
     *
     * @param event The mouse event triggered by clicking the condensed library button.
     */
    @FXML
    private void openLibrary(MouseEvent event) {
        loadPage("library-expanded");
    }

    /**
     * Handles the go to main button click event. Loads the main page.
     *
     * @param event The mouse event triggered by clicking the go to main button.
     */
    @FXML
    private void goToMain(MouseEvent event) {
        loadPage("main-view");
    }

    /**
     * Loads the specified FXML page and sets it as the content of the main UI elements.
     *
     * @param page The name of the FXML file to load, excluding the ".fxml" extension.
     */
    public void loadPage(String page) {
        try {
            URL fxmlLocation = getClass().getResource("/com/example/samplesalad/" + page + ".fxml");
            if (fxmlLocation == null) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, "FXML file not found: " + page + ".fxml");
                return;
            }
            Logger.getLogger(MainController.class.getName()).log(Level.INFO, "Loading FXML file: " + fxmlLocation.toString());
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            if (page.equals("main-view")) {
                bp.setCenter(root);
            } else {
                contentPane.getChildren().setAll(root);
            }
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void register(MouseEvent mouseEvent) {

    }
}