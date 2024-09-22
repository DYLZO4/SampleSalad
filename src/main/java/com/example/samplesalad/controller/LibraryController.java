package com.example.samplesalad.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.application.Application.launch;

/**
 * Controller class for managing interactions in the library view.
 * This class implements {@link IController} to inherit common functionalities and
 * provides specific implementations for handling events in the library view.
 */
public class LibraryController implements IController {

    public AnchorPane contentPane;

    /**
     * Default constructor for the {@code libraryController} class.
     */
    public LibraryController(){}

    /**
     * Handles the event when text is entered into the search bar.
     * This method is used to search through the table based on the entered text.
     *
     * @param actionEvent The action event triggered by entering text into the search bar.
     */
    @FXML
    public void onTextEntered(ActionEvent actionEvent) {
        // TODO: Implement search functionality through table
    }

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
            Logger.getLogger(LibraryController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public void openRecents(MouseEvent mouseEvent) {
        System.out.println("openRecent called");
        // TODO: Implement view to sort by last opened/added
    }

    @FXML
    public void uploadSong(MouseEvent mouseEvent) throws IOException {
        URL popupURL = getClass().getResource("/com/example/samplesalad/file-picker-dialog.fxml");
        if (popupURL == null) {
            Logger.getLogger(LibraryController.class.getName()).log(Level.SEVERE, "FXML file not found: file-picker-dialog.fxml");
            return;
        }
        Logger.getLogger(LibraryController.class.getName()).log(Level.INFO, "Loading FXML file: " + popupURL.toString());
        FXMLLoader popupLoader = new FXMLLoader(popupURL);

        Scene scene = popupLoader.load();

        Stage popup = new Stage();
        popup.setTitle("Upload new song");
        popup.setScene(scene);
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.showAndWait();
        // TODO: create dialogue box to upload a song to the database
    }
}
