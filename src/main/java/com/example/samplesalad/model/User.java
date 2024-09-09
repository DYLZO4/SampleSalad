package com.example.samplesalad.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String hashedPassword;
    private String email;
    private String phone;

    public User(String firstName, String lastName, String password, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hashedPassword = hashPassword(password);
        this.email = email;
        this.phone = phone;
    }

    public int getID(){
        return id;
    }
    public String getFirstName(){
        return  firstName;
    }
    public String getLastName(){
        return  lastName;
    }
    public String getHashedPassword(){
        return hashedPassword;
    }
    public String getPhone(){
        return phone;
    }

    public String getEmail(){
        return email;
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
