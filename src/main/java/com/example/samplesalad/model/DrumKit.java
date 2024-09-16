package com.example.samplesalad.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code DrumKit} class represents a collection of pads, each of which can be assigned a {@link Sample}.
 * It provides methods to add pads, load a kit with samples, save the kit, and manage individual pads.
 */
public class DrumKit {
    private String kitName;
    private List<Pad> pads; // A collection of pads in the drum kit

    /**
     * Constructs a {@code DrumKit} with a specified name and number of pads.
     *
     * @param kitName The name of the drum kit.
     */
    public DrumKit(String kitName) {
        this.kitName = kitName;
        this.pads = new ArrayList<>();
    }

    /**
     * Adds a pad to the drum kit with the given sample.
     *
     * @param sample The sample to be assigned to the new pad.
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
     * Saves the current drum kit state to a file. The implementation of saving can vary (e.g., JSON or XML format).
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
     * @return The Pad object at the specified pad number.
     * @throws IllegalArgumentException If the pad number is out of range.
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
     * @param padNumber The pad number (0-based index) to which the sample will be assigned.
     * @throws IllegalArgumentException If the pad number is out of range.
     */
    public void assignSampleToPad(Sample sample, int padNumber) {
        if (padNumber >= 0 && padNumber < pads.size()) {
            pads.get(padNumber).setSample(sample);
        } else {
            throw new IllegalArgumentException("Invalid pad number");
        }
    }

    /**
     * Gets the name of the drum kit.
     *
     * @return The name of the drum kit.
     */
    public String getKitName() {
        return kitName;
    }

    /**
     * Sets the name of the drum kit.
     *
     * @param kitName The name to be set for the drum kit.
     */
    public void setKitName(String kitName) {
        this.kitName = kitName;
    }

    /**
     * Returns the list of pads in the drum kit.
     *
     * @return The list of pads.
     */
    public List<Pad> getPads() {
        return pads;
    }
}