package com.example.samplesalad.model;

import com.example.samplesalad.model.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Stores a collection of pad events, representing a musical pattern that can be looped or played in sequence,
 * with each event occurring at specific timestamps.
 */
public class Pattern {
    private long startTime;
    private long endTime;
    private int patternID;
    private int length;
    private List<PadEvent> padEvents;
    private List<Double> timestamps = new ArrayList<>(Arrays.asList(0.0));;
    private User user;
    private Boolean isPlaying;

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
//        double timestamp;
//        if(padEvents.isEmpty()) {
//            timestamp = 0;
//        }
//        timestamp = event.getTimeStamp() ;
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

    public void startRecordPattern() {
        startTime = System.currentTimeMillis();
    }

    public void endRecordPattern() {
        endTime = System.currentTimeMillis();
    }

    public long getStartTime() {
        return startTime;
    }

    public void addTimeStamp(double newTimeStamp) {
        timestamps.add(newTimeStamp);
    }

    public void stopPattern() {
        isPlaying = false;
    }

    public void playPattern() {
        isPlaying = true;

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable playPattern = () -> {
            for (int i = 0; i < padEvents.size(); i++) {
                int index = i;

                scheduler.schedule(() -> {
                    padEvents.get(index).getPad().getAudioClip().playAudio();
                }, timestamps.get(i).longValue(), TimeUnit.MILLISECONDS);
            }
        };
        long patternDuration = endTime - startTime;
        scheduler.scheduleAtFixedRate(() -> {
            if (isPlaying) {
                playPattern.run();
            } else {
                scheduler.shutdown();
            }
        }, 0, patternDuration, TimeUnit.MILLISECONDS);
    }

}

