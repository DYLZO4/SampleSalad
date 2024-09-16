package com.example.samplesalad.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * Controller class for managing interactions in the library view.
 * This class extends {@link HelloController} to inherit common functionalities and
 * provides specific implementations for handling events in the library view.
 */
public class libraryController extends HelloController {

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
     * Handles the event when the side arrow bar is clicked.
     * This method loads the expanded library view.
     *
     * @param mouseEvent The mouse event triggered by clicking the side arrow bar.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @FXML
    public void onSideBarClicked(MouseEvent mouseEvent) throws IOException {
        loadPage("library-expanded.fxml");
    }
}
