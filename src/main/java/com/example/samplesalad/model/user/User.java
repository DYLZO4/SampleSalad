package com.example.samplesalad.model.user;

/**
 * The {@code User} class represents a user in the system.
 * It contains personal information such as first name, last name, hashed password, email, and phone number.
 * The password is stored in a hashed format for security purposes.
 */
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String hashedPassword;
    private String email;
    private String phone;

    /**
     * Constructs a new {@code User} object with the provided first name, last name, password, email, and phone number.
     * The password is hashed using {@code HashUtil}.
     *
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param password the plain text password of the user (will be hashed)
     * @param email the email address of the user
     * @param phone the phone number of the user
     */
    public User(String firstName, String lastName, String password, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hashedPassword = password;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Gets the user's unique identifier (ID).
     *
     * @return the user's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the user's first name.
     *
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the user's last name.
     *
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the hashed password of the user.
     *
     * @return the hashed password of the user
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Gets the user's phone number.
     *
     * @return the phone number of the user
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Gets the user's email address.
     *
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's unique identifier (ID).
     *
     * @param id the user's ID to set
     */
    public void setId(int id) {
        this.id = id;
    }
}
