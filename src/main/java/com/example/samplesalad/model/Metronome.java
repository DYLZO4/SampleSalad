package com.example.samplesalad.model;
import com.example.samplesalad.model.user.User;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The {@code Metronome} class represents a simple metronome with basic start/stop
 * functionality and BPM (beats per minute) control.
 * <p>
 * This class allows for starting and stopping a metronome, adjusting the BPM,
 * and resetting the metronome to its default state.
 * </p>
 */
public class Metronome {
    private User user;
    private int bpm;  // Beats per minute of the metronome
    public boolean isPlaying;  // Indicates whether the metronome is currently playing
    AudioClip metronomeAudioClip;
    private ScheduledExecutorService metronomeScheduler;

    /**
     * Constructs a {@code Metronome} with the specified BPM and playing state.
     */
    public Metronome() {
        this.metronomeAudioClip = new AudioClip("metronome.wav"); // Update path as needed.
        this.isPlaying = false;
        this.metronomeScheduler = Executors.newScheduledThreadPool(10); // Create the scheduler
    }

    /**
     * Starts the metronome.
     * <p>
     * This method sets the playing state of the metronome to true.
     * </p>
     */
    public void startMetronome(int bpm) {
        if (isPlaying) {
            return; // Already playing
        }

        isPlaying = true;  // Set playing flag before scheduling

        long delayBetweenBeats = 60000 / bpm;

        metronomeScheduler.scheduleAtFixedRate(() -> {
            try {
                playMetronomeSound();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, delayBetweenBeats, TimeUnit.MILLISECONDS);
    }

    // Method to play the metronome sound (you can customize this)
    public void playMetronomeSound() {
        try {
            // Load the metronome sound file and play it
            metronomeAudioClip.loadResource(); // Assuming you have an AudioClip for the metronome sound
            metronomeAudioClip.playAudio();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the metronome.
     * <p>
     * This method sets the playing state of the metronome to false.
     * </p>
     */
    public void stop() {
        isPlaying = false;
        metronomeScheduler.shutdownNow(); // Stop the scheduler's tasks
        try {
            if (!metronomeScheduler.awaitTermination(500, TimeUnit.MILLISECONDS)) {
                System.err.println("Metronome scheduler did not terminate cleanly.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        metronomeScheduler = Executors.newScheduledThreadPool(1);
    }


    /**
     * Returns the current BPM of the metronome.
     *
     * @return the current BPM.
     */
    public int getBpm() {
        return bpm;
    }

    /**
     * Sets the BPM of the metronome.
     *
     * @param bpm the desired BPM to set.
     */
    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    /**
     * Toggles the playing state of the metronome.
     * <p>
     * If the metronome is currently playing, it will stop; if it is stopped, it will start playing.
     * </p>
     */
    public void togglePlay() {
        isPlaying = !isPlaying;
    }

    /**
     * Resets the metronome to a default BPM (e.g., 120) and stops it.
     */
    public void reset() {
        bpm = 120;
        isPlaying = false;
    }

    /**
     * Returns whether the metronome is currently playing.
     *
     * @return {@code true} if the metronome is playing, {@code false} otherwise.
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Retrieve the corresponding user
     * @return user The user of this Metronome
     */
    public User getUser(){ return user; }

    /**
     * Sets the user of this Metronome
     * @param newUser The user to set as this Metronomes user
     */
    public void setUser(User newUser) { user = newUser; }
}