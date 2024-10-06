package com.example.samplesalad.model.service;

import java.util.regex.Pattern;

/**
 * The {@code UserValidator} class provides utility methods for validating user inputs,
 * such as email addresses, phone numbers, and passwords.
 * <p>
 * This class offers static methods that check whether the provided user information meets
 * specific format and security requirements.
 * </p>
 */
public class UserValidator {

    // Regular expression pattern for validating email addresses
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    // Regular expression pattern for validating phone numbers (allows optional country code)
    private static final String PHONE_PATTERN = "^\\+?[0-9\\s\\-()]{10,}$";

    // Regular expression pattern for validating passwords
    // Password must be at least 8 characters, with at least one uppercase letter, one digit, and one special character
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    /**
     * Default constructor for {@code UserValidator}.
     * <p>
     * This class contains only static methods, so instances of this class are generally unnecessary.
     * </p>
     */
    public UserValidator() {}

    /**
     * Validates if the given email is in a valid format.
     *
     * @param email the email address to validate
     * @return {@code true} if the email matches the expected format, {@code false} otherwise
     */
    public static boolean validateEmail(String email) {
        return Pattern.matches(EMAIL_PATTERN, email);
    }

    /**
     * Validates if the given phone number is in a valid format.
     *
     * @param phoneNumber the phone number to validate
     * @return {@code true} if the phone number matches the expected format, {@code false} otherwise
     */
    public static boolean validatePhoneNumber(String phoneNumber) {
        return Pattern.matches(PHONE_PATTERN, phoneNumber);
    }

    /**
     * Validates if the given password meets the security requirements.
     *
     * The password must:
     * <ul>
     *     <li>Contain at least 8 characters</li>
     *     <li>Include at least one uppercase letter</li>
     *     <li>Contain at least one digit</li>
     *     <li>Include at least one special character (e.g., @$!%*?&amp;)</li>
     * </ul>
     *
     * @param password the password to validate
     * @return {@code true} if the password meets the security criteria, {@code false} otherwise
     */
    public static boolean validatePassword(String password) {
        return Pattern.matches(PASSWORD_PATTERN, password);
    }

    /**
     * Validates all user details, including email, phone number, and password.
     *
     * @param email the email address to validate
     * @param phoneNumber the phone number to validate
     * @param password the password to validate
     * @return {@code true} if all inputs are valid, {@code false} otherwise
     */
    public static boolean validateUserDetails(String email, String phoneNumber, String password) {
        return validateEmail(email) && validatePhoneNumber(phoneNumber) && validatePassword(password);
    }
}