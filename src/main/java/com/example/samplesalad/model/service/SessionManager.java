package com.example.samplesalad.model.service;

import com.example.samplesalad.model.User;

/**
 * Manages user sessions, providing methods for login, logout, and checking login status.
 * This class uses the Singleton pattern to ensure only one instance exists throughout the application.
 */
public class SessionManager {

    /**
     * The single instance of the SessionManager.
     */
    private static SessionManager instance;

    /**
     * Private constructor to prevent external instantiation.
     */
    private SessionManager() {}

    /**
     * Retrieves the single instance of the SessionManager.
     * Creates a new instance if one doesn't already exist.
     *
     * @return The SessionManager instance.
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /**
     * The currently logged-in user.  Null if no user is logged in.
     */
    private User loggedInUser;

    /**
     * Logs in a user.
     *
     * @param user The user to log in.
     */
    public void login(User user) {
        this.loggedInUser = user;
    }

    /**
     * Logs out the current user.
     */
    public void logout() {
        this.loggedInUser = null;
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return True if a user is logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    /**
     * Retrieves the currently logged-in user.
     *
     * @return The logged-in user, or null if no user is logged in.
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }
}
