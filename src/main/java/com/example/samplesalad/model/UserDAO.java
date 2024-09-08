package com.example.samplesalad.model;

import com.example.samplesalad.controller.User;

import java.util.ArrayList;
import java.util.List;


public class UserDAO implements ISampleSaladDAO<User> {
    public static final ArrayList<User> users = new ArrayList<>();
    private static int autoIncrementedId = 0;
    public UserDAO () {

    }
    public void add() {

    }

    public void update() {

    }

    public void delete() {

    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }
}
