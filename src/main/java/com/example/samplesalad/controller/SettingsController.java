package com.example.samplesalad.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.logging.Logger;

/**
 * Controller class for managing actions inside Settings view
 * This class implements {@link IController} to inherit common functionalities and
 * provides specific implementations for handling events in the library view.
 */
public class SettingsController implements IController {
    public AnchorPane contentPane;

    /**
     * Loads the specified FXML page and sets it as the content of the main UI elements.
     *
     * @param page The name of the FXML file to load, excluding the ".fxml" extension.
     */
    public void loadPage(String page){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/samplesalad/" + page + ".fxml"));
            Parent root = loader.load();
            AnchorPane newContentPane = (AnchorPane) root.lookup("#contentPane");
            contentPane.getChildren().setAll(newContentPane.getChildren());
        } catch (java.io.IOException ex) {
            Logger.getLogger(SettingsController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public void loadPopup(String page){

    }

    public void openPopup(MouseEvent mouseEvent) {

    }

    public void setKeyBind(KeyEvent keyEvent) {
        //TODO: make functional to be used in popup
    }
}
