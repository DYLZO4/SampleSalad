package com.example.samplesalad.controller;

import javafx.fxml.Initializable;

/**
 * Interface representing a controller in the application.
 * This interface extends {@link Initializable} to include the initialization
 * method for controllers and defines an abstract method for loading pages.
 */
public abstract interface IController extends Initializable {

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method is required to be implemented by classes that implement this interface.
     * It is used to set up initial states or configurations for the controller.
     *
     * @param url The location used to resolve relative paths for the root object, or {@code null} if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if the root object is not localized.
     */
    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle);

    /**
     * Abstract method for loading a specific page into the controller's view.
     * Implementing classes should provide the logic for loading the page content.
     *
     * @param page The name of the page to load, typically corresponding to an FXML file.
     */
    abstract void loadPage(String page);
}
