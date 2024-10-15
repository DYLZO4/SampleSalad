package com.example.samplesalad.model;

import com.example.samplesalad.model.user.User;

import java.io.File;
import java.sql.Timestamp;
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
    private double BPM;
    private int startTime;
    private int endTime;
    private List<Effect> appliedEffects;
    private Timestamp dateAdded;
    private double duration;
    private File audioFile;
    private User user;


    /**
     * Constructor to initialize a {@code Sample} object with the given sample ID, file path, sample name,
     * sample artist and the selected genre of the sample

     */
     public Sample(String filePath, String sampleName, String sampleArtist, String sampleGenre, int BPM, double duration) {
        this.filePath = filePath;
        this.sampleName = sampleName;
        this.sampleArtist = sampleArtist;
        this.sampleGenre = sampleGenre;
        this.pitch = 1.0;
        this.BPM = BPM;
        this.appliedEffects = new ArrayList<>();
        this.dateAdded =new Timestamp(System.currentTimeMillis());
        this.audioFile = new File(filePath);
        this.duration = duration;
        startTime = 0;
        endTime = (int) duration * 1000;
     }


    public Sample(Integer sampleID, String filePath, String sampleName, String sampleArtist, String sampleGenre, Double pitch, Double bpm, Timestamp dateAdded, double duration) {
        this.sampleID = sampleID;
        this.filePath = filePath;
        this.sampleName = sampleName;
        this.sampleGenre = sampleGenre;
        this.sampleArtist = sampleArtist;
        this.pitch = pitch;
        this.BPM = bpm;
        this.appliedEffects = new ArrayList<>();
        this.dateAdded = dateAdded;
        this.duration = duration;
        startTime = 0;
        endTime = (int) duration * 1000;
    }

    public Sample(String filePath, String sampleName, String sampleArtist, String sampleGenre, Double pitch, Double bpm, int startTime, int endTime) {
        this.sampleID = sampleID;
        this.filePath = filePath;
        this.sampleName = sampleName;
        this.sampleGenre = sampleGenre;
        this.sampleArtist = sampleArtist;
        this.pitch = pitch;
        this.BPM = bpm;
        dateAdded = new Timestamp(System.currentTimeMillis());
        this.appliedEffects = new ArrayList<>();
        this.dateAdded = dateAdded;
        this.startTime = startTime;
        this.endTime = endTime;
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
    public double getBPM() {
        return BPM;
    }

    /**
     * Sets the volume of the sample.
     *
     * @param BPM the volume to set (1.0 for 100% volume)
     */
    public void setBPM(double BPM) {
        this.BPM = BPM;
    }


    public void setDuration(double duration){this.duration = duration;}
    public double getDuration(){return duration;}

    /**
     * Gets the list of effects that have been applied to the sample.
     *
     * @return the list of applied effects
     */
    public List<Effect> getAppliedEffects() {
        return appliedEffects;
    }

    /**
     * Gets the date and time when the sample was added.
     *
     * @return the date and time the sample was added
     */
    public Timestamp getDateAdded() {
        return dateAdded;
    }

    /**
     * Sets the date and time when the sample was added.
     *
     * @param dateAdded the date and time to set
     */
    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
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
        System.out.println("Playing sample from " + filePath + " with pitch " + pitch + " and volume " + BPM);
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

    /**
     * Retrieve the corresponding user
     * @return user The user of this Sample
     */
    public User getUser(){ return user; }

    /**
     * Sets the user of this Sample
     * @param newUser The user to set as this Samples user
     */
    public void setUser(User newUser) { user = newUser; }

    /**
     * Gets the start time in milliseconds
     * @return startTime The timestamp marking the start of an audio clip.
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Gets the end time in milliseconds
     * @return endTime The timestamp marking the end of an audio clip.
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * Sets the end start in milliseconds
     * @return endTime The timestamp marking the end of an audio clip.
     */
    public void setStartTime(int msStartTime) {
        startTime = msStartTime;
    }

    /**
     * Sets the end time in milliseconds
     * @return endTime The timestamp marking the end of an audio clip.
     */
    public void setEndTime(int msEndTime) {
        endTime = msEndTime;
    }
}
