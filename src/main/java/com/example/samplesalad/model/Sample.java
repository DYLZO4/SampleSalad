package com.example.samplesalad.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Sample} class represents an audio sample with various properties
 * such as pitch, volume, start and end times. It supports applying effects
 * and provides methods to load, play, and stop the sample.
 */
public class Sample {

    private int sampleID;
    private String sampleName;
    private String sampleArtist;
    private String sampleGenre;
    private String filePath;
    private double pitch;
    private double volume;
    private double startTime;
    private double endTime;
    private List<Effect> appliedEffects;

    /**
     * Constructor to initialize a {@code Sample} object with the given sample ID
     * and file path.
     *
     * @param sampleID the unique identifier for the sample
     * @param filePath the file path where the sample is located
     */
    public Sample(int sampleID, String filePath) {
        this.sampleID = sampleID;
        this.filePath = filePath;
        this.pitch = 1.0; // Default pitch (normal pitch)
        this.volume = 1.0; // Default volume (100%)
        this.startTime = 0.0;
        this.endTime = 0.0; // 0.0 means the whole sample length
        this.appliedEffects = new ArrayList<>();
    }

    /**
     * Constructor to initialize a {@code Sample} object with the given sample ID, file path, sample name,
     * sample artist and the selected genre of the sample
     *
     * @param sampleID the unique identifier for the sample
     * @param filePath the file path where the sample is located
     * @param sampleName the name of the sample chosen by the user
     * @param sampleArtist the name of the artist who created  the sample
     * @param sampleGenre the genre category which the sample falls into
     */
    public Sample(int sampleID, String filePath, String sampleName, String sampleArtist, String sampleGenre) {
        this.sampleID = sampleID;
        this.filePath = filePath;
        this.sampleName = sampleName;
        this.sampleArtist = sampleArtist;
        this.sampleGenre = sampleGenre;
        this.pitch = 1.0; // Default pitch (normal pitch)
        this.volume = 1.0; // Default volume (100%)
        this.startTime = 0.0;
        this.endTime = 0.0; // 0.0 means the whole sample length
        this.appliedEffects = new ArrayList<>();
    }

    public Sample(int sampleID, String filePath, String sampleName, String sampleArtist, String sampleGenre, double pitch, double volume, double startTime, double endTime){
        this.sampleID = sampleID;
        this.filePath = filePath;
        this.sampleName = sampleName;
        this.sampleGenre = sampleGenre;
        this.pitch = pitch;
        this.volume = volume;
        this.startTime = startTime;
        this.endTime = endTime;
        this.appliedEffects = new ArrayList<>();
    }


    /**
     * Gets the unique identifier for the sample.
     *
     * @return the sample ID
     */
    public int getSampleID() {
        return sampleID;
    }

    /**
     * Sets the unique identifier for the sample.
     *
     * @param sampleID the sample ID to set
     */
    public void setSampleID(int sampleID) {
        this.sampleID = sampleID;
    }

    /**
     * Gets the file path of the sample.
     *
     * @return the file path of the sample
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the file path of the sample.
     *
     * @param filePath the file path to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Gets the name of the sample
     * @return string of the sample name
     */
    public String getSampleName() { return sampleName; }

    /**
     * Sets the name of the sample
     * @param sampleName the name to set
     */
    public void setSampleName(String sampleName) { this.sampleName = sampleName; }

    /**
     * Gets the name of the artist associated with the sample
     * @return artist name
     */
    public String getSampleArtist() { return sampleArtist; }

    /**
     * Sets the name of the artist associated with the sample
     * @param sampleArtist new name of the sample's artist
     */
    public void setSampleArtist(String sampleArtist) { this.sampleArtist = sampleArtist; }

    /**
     * Gets the genre of the sample
     * @return genre of sample
     */
    public String getSampleGenre() { return sampleGenre; }

    /**
     * Sets the genre of the sample
     * @param sampleGenre genre of the sample
     */
    public void setSampleGenre(String sampleGenre) { this.sampleGenre = sampleGenre; }
    /**
     * Gets the pitch of the sample.
     *
     * @return the pitch of the sample
     */
    public double getPitch() {
        return pitch;
    }

    /**
     * Sets the pitch of the sample.
     *
     * @param pitch the pitch value to set (1.0 for normal pitch)
     */
    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    /**
     * Gets the volume of the sample.
     *
     * @return the volume of the sample
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Sets the volume of the sample.
     *
     * @param volume the volume to set (1.0 for 100% volume)
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    /**
     * Gets the start time of the sample playback.
     *
     * @return the start time of the sample in seconds
     */
    public double getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the sample playback.
     *
     * @param startTime the start time in seconds
     */
    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the sample playback.
     *
     * @return the end time of the sample in seconds
     */
    public double getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the sample playback.
     *
     * @param endTime the end time in seconds
     */
    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the list of effects that have been applied to the sample.
     *
     * @return the list of applied effects
     */
    public List<Effect> getAppliedEffects() {
        return appliedEffects;
    }

    /**
     * Loads the sample from the specified file.
     *
     * @param file the file to load the sample from
     */
    public void loadSample(File file) {
        this.filePath = file.getPath();
        System.out.println("Sample loaded from " + filePath);
    }

    /**
     * Plays the sample, applying the current pitch and volume settings.
     */
    public void playSample() {
        System.out.println("Playing sample from " + filePath + " with pitch " + pitch + " and volume " + volume);
    }

    /**
     * Stops the sample playback.
     */
    public void stopSample() {
        System.out.println("Stopping sample.");
    }

    /**
     * Applies the specified effect to the sample.
     *
     * @param effect the effect to apply
     */
    public void applyEffect(Effect effect) {
        appliedEffects.add(effect);
        effect.apply(this);
        System.out.println("Effect applied: " + effect.getEffectType());
    }

    /**
     * Removes the specified effect from the sample.
     *
     * @param effect the effect to remove
     */
    public void removeEffect(Effect effect) {
        appliedEffects.remove(effect);
        System.out.println("Effect removed: " + effect.getEffectType());
    }
}
