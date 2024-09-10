package com.example.samplesalad;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;

import java.io.IOException;

import static javafx.application.Application.launch;

public class libraryCondensedController {
    @FXML // Search bar
    public void onTextEntered(ActionEvent actionEvent) {
        //search through table
    }

    @FXML // side arrow bar
    public void onSideBarClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader expandedLoader = new FXMLLoader(libraryCondensedApplication.class.getResource("library-expanded.fxml"));
    }
}
