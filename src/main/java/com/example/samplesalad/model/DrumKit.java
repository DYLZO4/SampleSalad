package com.example.samplesalad.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code DrumKit} class represents a collection of pads
 */

public class DrumKit {
    private String kitName;
    private List<Pad> pads; // A collection of pads in the drum kit

    // Constructor
    public DrumKit(String kitName, int numberOfPads) {
        this.kitName = kitName;
        this.pads = new ArrayList<>(numberOfPads);
    }

    /**
     * Adds a pad to the drum kit.
     *
     * @param sample The sample to be assigned to the pad.
     */
    public void addPad(Sample sample) {
        Pad newPad = new Pad(sample); // Create a new Pad with the given sample
        pads.add(newPad); // Add the pad to the drum kit
    }

    /**
     * Loads a drum kit by assigning samples to pads.
     *
     * @param samples List of samples to be assigned to the pads.
     */
    public void loadKit(List<Sample> samples) {
        for (Sample sample : samples) {
            addPad(sample); // Add a pad for each sample
        }
    }

    /**
     * Saves the current drum kit state to a file (implementation can vary).
     *
     * @param filePath The path where the drum kit should be saved.
     */
    public void saveKit(String filePath) {
        // Logic to save the kit to the specified filePath (e.g., JSON or XML format)
    }

    /**
     * Returns the pad assigned to a specific pad number.
     *
     * @param padNumber The pad number (0-based index).
     * @return The Pad object.
     */
    public Pad getPad(int padNumber) {
        if (padNumber >= 0 && padNumber < pads.size()) {
            return pads.get(padNumber);
        } else {
            throw new IllegalArgumentException("Invalid pad number");
        }
    }

    /**
     * Assigns a sample to a specific pad.
     *
     * @param sample The sample to be assigned.
     * @param padNumber The pad number (0-based index).
     */
    public void assignSampleToPad(Sample sample, int padNumber) {
        if (padNumber >= 0 && padNumber < pads.size()) {
            pads.get(padNumber).setSample(sample);
        } else {
            throw new IllegalArgumentException("Invalid pad number");
        }
    }

    /**
     * Gets the kit name.
     *
     * @return The name of the drum kit.
     */
    public String getKitName() {
        return kitName;
    }

    /**
     * Sets the kit name.
     *
     * @param kitName The name to be set for the drum kit.
     */
    public void setKitName(String kitName) {
        this.kitName = kitName;
    }

    /**
     * Returns the list of pads.
     *
     * @return The list of pads.
     */
    public List<Pad> getPads() {
        return pads;
    }
}
