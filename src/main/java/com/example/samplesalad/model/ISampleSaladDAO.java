package com.example.samplesalad.model;


import java.util.List;

/**
 * Interface for the Contact Data Access Object that handles
 * the CRUD operations for the Contact class with the database.
 */
public interface ISampleSaladDAO<T> {
    void add();
    void update();
    void delete();
    T get(int id);
    List<T> getAll();
}
