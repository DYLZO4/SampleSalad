package com.example.samplesalad.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.logging.Logger;

public class LoginController implements IController {

    public AnchorPane contentPane;

    @Override
    public void loadPage(String fxmlpage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/samplesalad/" + fxmlpage + ".fxml"));
            Parent root = loader.load();
            AnchorPane newContentPane = (AnchorPane) root.lookup("#contentPane");
            contentPane.getChildren().setAll(newContentPane.getChildren());
        } catch (java.io.IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
