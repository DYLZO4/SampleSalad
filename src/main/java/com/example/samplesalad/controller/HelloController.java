package com.example.samplesalad.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloController implements Initializable {
    @FXML
    private ImageView exit, menu;

    @FXML
    private AnchorPane pane1, pane2;

    @FXML
    private BorderPane bp;

    @FXML
    private AnchorPane contentPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        pane1.setVisible(false);
        pane1.setMouseTransparent(true); // Initially make pane1 non-interactive

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition.setByX(-600);
        translateTransition.play();

        menu.setOnMouseClicked(event -> {
            pane1.setVisible(true);
            pane1.setMouseTransparent(false); // Make pane1 interactive
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(1);
            fadeTransition1.play();

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
            translateTransition1.setByX(+600);
            translateTransition1.play();
        });

        pane1.setOnMouseClicked(event -> {
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
            fadeTransition1.setFromValue(1);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                pane1.setVisible(false);
                pane1.setMouseTransparent(true); // Make pane1 non-interactive
            });

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });
    }

    @FXML
    private void login(MouseEvent event) {
        loadPage("login");
    }

    @FXML
    private void register(MouseEvent event) {
        loadPage("signup");
    }

    @FXML
    private void account(MouseEvent event) {
        loadPage("account");
    }

    @FXML
    private void openCondensedLibrary(MouseEvent event) {
        loadPage("library-condensed");
    }
    @FXML
    private void goToMain(MouseEvent event) {
        loadPage("hello-view");
    }

    public void loadPage(String page) {
        try {
            URL fxmlLocation = getClass().getResource("/com/example/samplesalad/" + page + ".fxml");
            if (fxmlLocation == null) {
                Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, "FXML file not found: " + page + ".fxml");
                return;
            }
            Logger.getLogger(HelloController.class.getName()).log(Level.INFO, "Loading FXML file: " + fxmlLocation.toString());
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            if (page.equals("hello-view")) {
                bp.setCenter(root);
            } else {
                contentPane.getChildren().setAll(root);
            }
        } catch (IOException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}