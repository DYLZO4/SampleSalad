package com.example.samplesalad.model.DAO;

import java.util.List;

/**
 * Data Access Object (DAO) interface for performing CRUD (Create, Read, Update, Delete) operations.
 * This generic interface defines methods for managing entities of type {@code T}.
 *
 * @param <T> The type of entity managed by the DAO.
 */
public interface ISampleSaladDAO<T> {

    /**
     * Adds a new entity to the data source.
     *
     * @param t The entity to be added.
     */
    void add(T t);

    /**
     * Updates an existing entity in the data source.
     *
     * @param t The entity with updated values.
     */
    void update(T t);

    /**
     * Deletes an entity from the data source.
     *
     * @param t The entity to be deleted.
     */
    void delete(T t);

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id The unique identifier of the entity to be retrieved.
     * @return The entity with the specified id, or {@code null} if not found.
     */
    T get(int id);

    /**
     * Retrieves all entities from the data source.
     *
     * @return A list of all entities.
     */
    List<T> getAll();
}
