package com.example.samplesalad.model;

import com.example.samplesalad.model.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores a collection of pad events, representing a musical pattern that can be looped or played in sequence,
 * with each event occurring at specific timestamps.
 */
public class Pattern {

    private int patternID;
    private int length;
    private List<PadEvent> padEvents;
    private User user;

    /**
     * Initialized with a given length.
     * @param length The length of the pattern.
     */
    public Pattern(int length) {
        this.length = length;
        this.padEvents = new ArrayList<>();
    }

    /**
     * Adds a PadEvent instance to the pattern.
     * @param event The PadEvent to be added.
     */
    public void addPadEvent(PadEvent event) {
        padEvents.add(event);
    }

    /**
     * Removes a PadEvent instance from the pattern.
     * @param event The PadEvent to be removed.
     */
    public void removePadEvent(PadEvent event) {
        padEvents.remove(event);
    }

    /**
     * Returns the list of PadEvents in the pattern.
     * @return A List of PadEvent objects.
     */
    public List<PadEvent> getPadEvents() {
        return padEvents;
    }

    /**
     * Returns the length of the pattern.
     * @return The length of the pattern.
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the length of the pattern.
     * @param length The new length of the pattern.
     */
    public void setLength(int length) {
        this.length = length;
    }

    public void setPatternID(int patternID){ this.patternID = patternID;}

    public int getPatternID(){ return  patternID;}

    /**
     * Retrieve the corresponding user
     * @return user The user of this Pattern
     */
    public User getUser(){ return user; }

    /**
     * Sets the user of this Pattern
     * @param newUser The user to set as this Patterns user
     */
    public void setUser(User newUser) { user = newUser; }
}

