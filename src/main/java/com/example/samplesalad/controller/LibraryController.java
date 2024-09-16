package com.example.samplesalad.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.util.logging.Logger;

import static javafx.application.Application.launch;

public class LibraryController implements IController {

    public AnchorPane contentPane;

    @FXML // Search bar
    public void onTextEntered(ActionEvent actionEvent) {
        //search through table
    }

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

}
