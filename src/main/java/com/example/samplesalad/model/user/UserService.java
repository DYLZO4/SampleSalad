package com.example.samplesalad.model.user;

import com.example.samplesalad.model.HashUtil;
import com.example.samplesalad.model.DAO.UserDAO;

/**
 * The {@code UserService} class provides high-level services for managing users.
 * It handles user registration and login operations, interacting with the {@code UserDAO} to manage user data.
 */
public class UserService {

    private UserDAO userDAO;

    /**
     * Constructs a {@code UserService} object with the specified {@code UserDAO}.
     *
     * @param userDAO the data access object used for user-related database operations
     */
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Registers a new user by creating a {@code User} object and adding it to the database via {@code UserDAO}.
     *
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param password the user's password (which will be hashed before storing)
     * @param email the email address of the user
     * @param phone the phone number of the user
     */
    public void registerUser(String firstName, String lastName, String password, String email, String phone) {
        User user = new User(firstName, lastName, HashUtil.hashPassword(password), email, phone);
        userDAO.add(user);
    }

    /**
     * Attempts to log in a user by checking if the provided email and password match the stored credentials.
     *
     * @param email the email address of the user attempting to log in
     * @param password the password provided for authentication
     * @return {@code true} if the login is successful, {@code false} otherwise
     */
    public boolean loginUser(String email, String password) {
        User user = userDAO.getByEmail(email);
        if (user != null) {
            String hashedPassword = HashUtil.hashPassword(password);
            return hashedPassword.equals(user.getHashedPassword());
        }
        return false;
    }
}
