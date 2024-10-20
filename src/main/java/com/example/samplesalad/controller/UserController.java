package com.example.samplesalad.controller;

import com.example.samplesalad.model.service.SessionManager;
import com.example.samplesalad.model.service.UserService;
import com.example.samplesalad.model.User;

/**
 * Controller for managing user-related operations.
 * Handles login, logout, and session management.
 */
public class UserController {
    private UserService userService;

    /**
     * Creates a new UserController.
     * @param userService The UserService to use for user authentication.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Attempts to log in a user with the provided username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return True if the login was successful, false otherwise.
     */
    public boolean login(String username, String password) {
        User user = userService.authenticate(username, password);
        if (user != null) {
            SessionManager.getInstance().login(user);
            return true;
        }
        return false;
    }

    /**
     * Logs out the current user.
     */
    public void logout() {
        SessionManager.getInstance().logout();
    }

    /**
     * Returns the currently logged-in user.
     *
     * @return The logged-in user, or null if no user is logged in.
     */
    public User getLoggedInUser() {
        return SessionManager.getInstance().getLoggedInUser();
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return True if a user is logged in, false otherwise.
     */
    public boolean isUserLoggedIn() {
        return SessionManager.getInstance().isLoggedIn();
    }
}