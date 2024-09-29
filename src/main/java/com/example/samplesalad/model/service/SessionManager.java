package com.example.samplesalad.model.service;

import com.example.samplesalad.model.user.User;

public class SessionManager {
    // The single instance of the class
    private static SessionManager instance;

    // Private constructor to prevent instantiation from outside
    private SessionManager() {}

    // Method to get the single instance of the class
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Your user tracking logic
    private User loggedInUser;

    public void login(User user) {
        this.loggedInUser = user;
    }

    public void logout() {
        this.loggedInUser = null;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
