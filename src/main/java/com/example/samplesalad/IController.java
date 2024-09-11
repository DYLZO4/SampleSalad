package com.example.samplesalad;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.util.logging.Logger;

public abstract interface IController extends Initializable {

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle);

    abstract void loadPage(String page);
}
