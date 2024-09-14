package com.example.samplesalad.controller;

import javafx.fxml.Initializable;

public abstract interface IController extends Initializable {

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle);

    abstract void loadPage(String page);
}
