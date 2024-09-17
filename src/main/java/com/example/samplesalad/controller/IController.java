package com.example.samplesalad.controller;

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

/**
 * Interface containing functions that are common for all controller classes so that implementation is consistent
 */
public abstract interface IController {

    public AnchorPane contentPane = null;

    /**
     * When implemented, loads the specific FXML page and sets it as the content of the main UI elements
     * 
     * @param page The name of the FXML file to load, excluding the ".fxml" extension.
     */
    abstract void loadPage(String page);
}
