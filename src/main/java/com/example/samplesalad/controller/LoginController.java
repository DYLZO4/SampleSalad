package com.example.samplesalad.controller;

import com.example.samplesalad.model.DAO.UserDAO;
import com.example.samplesalad.model.service.UserService;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import java.util.logging.Logger;

/**
 * Controller class for managing interactions in the login view.
 * This class implements the {@link IController} interface to inherit common functionalities
 * and provide specific implementations for handling events in the log in view
 */
public class LoginController implements IController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginStatusLabel;

    @FXML
    private AnchorPane contentPane;

    private UserDAO userDAO;
    private UserService userService;
    private UserController userController;

    /**
     * Default constructor for {@code LoginController} class.
     */
    public LoginController(){
        userDAO = new UserDAO();
        userService = new UserService(userDAO);
        userController = new UserController(userService);
    }

    @FXML
    private void register(MouseEvent event) {
        loadPage("signup");
    }

    /**
     * Handles the account button click event. Loads the account page.
     *
     * @param event The mouse event triggered by clicking the account button.
     */
    @FXML
    private void handleLogin(MouseEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (userController.login(email, password)) {
            loginStatusLabel.setText("Login successful!");
            loadPage("main-view"); // Load account.fxml if login is successful
        } else {
            loginStatusLabel.setText("Invalid email or password.");
        }
    }

    /**
     * Loads the specified FXML page and sets it as the content of the main UI elements.
     *
     * @param page The name of the FXML file to load, excluding the ".fxml" extension.
     */
    @Override
    public void loadPage(String page) {
        loadPage(page, null);
    }

    /**
     * Loads the specified FXML page and sets it as the content of the main UI elements.
     *
     * @param page The name of the FXML file to load, excluding the ".fxml" extension.
     * @param email The email of the logged-in user.
     */
    public void loadPage(String page, String email) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/samplesalad/" + page + ".fxml"));
            Parent root = loader.load();
            if (email != null) {
                AccountController accountController = loader.getController();
                accountController.loadUserInfo();
            }
            AnchorPane newContentPane = (AnchorPane) root.lookup("#contentPane");
            contentPane.getChildren().setAll(newContentPane.getChildren());
        } catch (java.io.IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}