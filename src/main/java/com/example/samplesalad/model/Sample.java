package com.example.samplesalad.model;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Sample {
    private int sampleID;
    private String filePath;
    private double pitch;
    private double volume;
    private double startTime;
    private double endTime;
    private List<Effect> appliedEffects;

    // Constructor
    public Sample(int sampleID, String filePath) {
        this.sampleID = sampleID;
        this.filePath = filePath;
        this.pitch = 1.0; // Default pitch (normal pitch)
        this.volume = 1.0; // Default volume (100%)
        this.startTime = 0.0;
        this.endTime = 0.0; // 0.0 means the whole sample length
        this.appliedEffects = new ArrayList<>();
    }

    // Getters and Setters
    public int getSampleID() {
        return sampleID;
    }

    public void setSampleID(int sampleID) {
        this.sampleID = sampleID;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public double getPitch() {
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public List<Effect> getAppliedEffects() {
        return appliedEffects;
    }

    // Methods
    public void loadSample(File file) {
        // Logic to load the sample file
        this.filePath = file.getPath();
        System.out.println("Sample loaded from " + filePath);
    }

    public void playSample() {
        // Logic to play the sample
        System.out.println("Playing sample from " + filePath + " with pitch " + pitch + " and volume " + volume);
    }

    public void stopSample() {
        // Logic to stop sample playback
        System.out.println("Stopping sample.");
    }

    public void applyEffect(Effect effect) {
        appliedEffects.add(effect);
        effect.apply(this);
        System.out.println("Effect applied: " + effect.getEffectType());
    }

    public void removeEffect(Effect effect) {
        appliedEffects.remove(effect);
        System.out.println("Effect removed: " + effect.getEffectType());
    }
}