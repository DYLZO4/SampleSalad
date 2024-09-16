package com.example.samplesalad.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Controller class for managing interactions in the login view. 
 * This class implements the {@link IController} interface to inherit common functionalities
 * and provide specific implementations for handling events in the log in view
 */
public class LoginController implements IController {

    /**
     * Default constructor for {@code LoginController} class. 
     */
    public LoginController(){}
    public AnchorPane contentPane;

    /**
     * Loads the specified FXML page and sets it as the content of the main UI elements.
     *
     * @param page The name of the FXML file to load, excluding the ".fxml" extension.
     */
    @Override
    public void loadPage(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/samplesalad/" + page + ".fxml"));
            Parent root = loader.load();
            AnchorPane newContentPane = (AnchorPane) root.lookup("#contentPane");
            contentPane.getChildren().setAll(newContentPane.getChildren());
        } catch (java.io.IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
