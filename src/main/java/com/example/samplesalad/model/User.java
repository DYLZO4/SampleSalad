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
        this.hashedPassword = password;
        this.email = email;
        this.phone = phone;
    }

    public int getId(){
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

    public void setId(int id){
        this.id = id;
    }

}