package com.example.samplesalad.model;

import java.util.regex.Pattern;

public class UserValidator {

    // Regex patterns for validation
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String PHONE_PATTERN = "^\\+?[0-9\\s\\-()]{10,}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    // Validates email format
    public static boolean validateEmail(String email) {
        return Pattern.matches(EMAIL_PATTERN, email);
    }

    // Validates phone number format
    public static boolean validatePhoneNumber(String phoneNumber) {
        return Pattern.matches(PHONE_PATTERN, phoneNumber);
    }

    // Validates password strength
    public static boolean validatePassword(String password) {
        return Pattern.matches(PASSWORD_PATTERN, password);
    }

    // Method to validate all input
    public static boolean validateUserDetails(String email, String phoneNumber, String password) {
        return validateEmail(email) && validatePhoneNumber(phoneNumber) && validatePassword(password);
    }
}