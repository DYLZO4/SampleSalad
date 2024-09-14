package com.example.samplesalad.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import static javafx.application.Application.launch;

public class libraryController extends HelloController {

    @FXML // Search bar
    public void onTextEntered(ActionEvent actionEvent) {
        //search through table
    }

    @FXML // side arrow bar
    public void onSideBarClicked(MouseEvent mouseEvent) throws IOException {
        loadPage("library-expanded.fxml");
    }


}
