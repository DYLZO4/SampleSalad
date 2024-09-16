package com.example.samplesalad.model;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code FakeUserDAO} class is a mock implementation of the {@code UserDAO} class
 * that uses an in-memory data structure (a {@code HashMap}) to simulate database operations.
 * <p>
 * This class is intended for testing purposes and does not interact with a real database.
 * </p>
 */
public class FakeUserDAO extends UserDAO {

    private Map<String, User> users = new HashMap<>();

    /**
     * Adds a user to the in-memory data structure.
     * <p>
     * This method simulates the addition of a user to a database by storing it in
     * an in-memory {@code HashMap} with the user's email as the key.
     * </p>
     *
     * @param user the {@code User} object to be added
     */
    @Override
    public void add(User user) {
        users.put(user.getEmail(), user);
    }

    /**
     * Retrieves a user by email from the in-memory data structure.
     * <p>
     * This method simulates retrieving a user from a database by looking up the user
     * in an in-memory {@code HashMap} using the user's email.
     * </p>
     *
     * @param email the email of the {@code User} to be retrieved
     * @return the {@code User} object associated with the specified email, or {@code null} if no user is found
     */
    @Override
    public User getByEmail(String email) {
        return users.get(email);
    }
}
