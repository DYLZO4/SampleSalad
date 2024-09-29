package com.example.samplesalad.controller;

import com.example.samplesalad.model.service.SessionManager;
import com.example.samplesalad.model.service.UserService;
import com.example.samplesalad.model.user.User;

public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public boolean login(String username, String password) {
        User user = userService.authenticate(username, password);
        if (user != null) {
            SessionManager.getInstance().login(user);
            return true;
        }
        return false;
    }

    public void logout() {
        SessionManager.getInstance().logout();
    }

    public User getLoggedInUser() {
        return SessionManager.getInstance().getLoggedInUser();
    }

    public boolean isUserLoggedIn() {
        return SessionManager.getInstance().isLoggedIn();
    }
}