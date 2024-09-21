package com.example.samplesalad.controller;

import com.example.samplesalad.model.user.User;
import com.example.samplesalad.model.DAO.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.util.logging.Logger;

/**
 * Controller class for managing interactions in the account view.
 */
public class AccountController extends LoginController {

    @FXML
    private TextField fnameField;

    @FXML
    private TextField lnameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private UserDAO userDAO;
    private User currentUser;
    private String email;

    /**
     * Initializes the controller and loads the user information.
     */
    @FXML
    public void initialize() {
        userDAO = new UserDAO();
        loadUserInfo();
    }

    /**
     * Sets the email of the current user.
     *
     * @param email The email of the current user.
     */
    public void setEmail(String email) {
        this.email = email;
        currentUser = userDAO.getByEmail(email);
        loadUserInfo();
    }

    /**
     * Loads the current user's information into the form fields.
     */
    private void loadUserInfo() {
        if (currentUser != null) {
            fnameField.setText(currentUser.getFirstName());
            lnameField.setText(currentUser.getLastName());
            phoneField.setText(currentUser.getPhone());
            emailField.setText(currentUser.getEmail());
            // Password field is not set for security reasons
        }
    }

    /**
     * Handles the save button click event to update the user information.
     *
     * @param event the mouse event
     */
    @FXML
    private void handleSave(MouseEvent event) {
        if (currentUser != null) {
            User updatedUser = new User(
                    fnameField.getText(),
                    lnameField.getText(),
                    currentUser.getHashedPassword(),
                    emailField.getText(),
                    phoneField.getText()
            );

            updatedUser.setId(currentUser.getId()); // Set the ID to ensure the correct user is updated
            userDAO.update(updatedUser);
            Logger.getLogger(AccountController.class.getName()).log(java.util.logging.Level.INFO, "User information updated successfully.");
        }
    }
}