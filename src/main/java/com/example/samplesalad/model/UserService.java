package com.example.samplesalad.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public void registerUser(String firstName, String lastName, String password, String email, String phone){
        User user = new User(firstName, lastName, hashPassword(password), email, phone);
        userDAO.add(user);
    }

    public boolean loginUser(String email, String password) {
        User user = userDAO.getByEmail(email);
        if (user != null) {
            String hashedPassword = hashPassword(password);
            return hashedPassword.equals(user.getHashedPassword());
        }
        return false;
    }

    private String hashPassword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b: hashedBytes){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
