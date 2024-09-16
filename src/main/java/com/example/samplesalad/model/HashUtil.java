package com.example.samplesalad.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The {@code HashUtil} class provides utility methods for hashing passwords using
 * the SHA-256 algorithm.
 * <p>
 * This class contains static methods and is intended to be used for hashing passwords
 * to ensure secure storage.
 * </p>
 */
public class HashUtil {

    /**
     * Hashes a password using the SHA-256 algorithm.
     * <p>
     * This method takes a plain text password, applies SHA-256 hashing, and returns
     * the hashed password as a hexadecimal string.
     * </p>
     *
     * @param password the plain text password to be hashed
     * @return the hashed password in hexadecimal format
     * @throws RuntimeException if the SHA-256 algorithm is not available
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }
}
