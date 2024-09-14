package com.example.samplesalad.model;

import java.util.HashMap;
import java.util.Map;

public class FakeUserDAO extends UserDAO {

    private Map<String, User> users = new HashMap<>();

    @Override
    public void add(User user) {
        users.put(user.getEmail(), user);
    }

    @Override
    public User getByEmail(String email) {
        return users.get(email);
    }
}
