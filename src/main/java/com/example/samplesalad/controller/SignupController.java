package com.example.samplesalad.controller;

import com.example.samplesalad.model.DAO.UserDAO;
import com.example.samplesalad.model.user.UserService;
import com.example.samplesalad.model.user.UserValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class SignupController {

    @FXML
    private TextField fnameField;

    @FXML
    private TextField lnameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    @FXML
    private AnchorPane contentPane;

    private UserService userService;

    public SignupController() {
        userService = new UserService(new UserDAO());
    }

    @FXML
    private void handleSignup(MouseEvent event) {
        String firstName = fnameField.getText();
        String lastName = lnameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();

        if (!UserValidator.validateEmail(email)) {
            statusLabel.setText("Invalid email format.");
            return;
        }

        if (!UserValidator.validatePhoneNumber(phone)) {
            statusLabel.setText("Invalid phone number format.");
            return;
        }

        if (!UserValidator.validatePassword(password)) {
            statusLabel.setText("Password must be at least 8 characters, include an uppercase letter, a digit, and a special character.");
            return;
        }

        userService.registerUser(firstName, lastName, password, email, phone);
        statusLabel.setText("User registered successfully...");
    }
}