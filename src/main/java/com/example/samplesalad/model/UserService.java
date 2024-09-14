package com.example.samplesalad.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public void registerUser(String firstName, String lastName, String password, String email, String phone){
        User user = new User(firstName, lastName, password, email, phone);
        userDAO.add(user);
    }

    public boolean loginUser(String email, String password) {
        User user = userDAO.getByEmail(email);
        if (user != null) {
            String hashedPassword = HashUtil.hashPassword(password);
            return hashedPassword.equals(user.getHashedPassword());
        }
        return false;
    }


}
