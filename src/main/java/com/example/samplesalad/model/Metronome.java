package com.example.samplesalad.model;

/**
 * The {@code Metronome} class represents a simple metronome with basic start/stop
 * functionality and BPM (beats per minute) control.
 * <p>
 * This class allows for starting and stopping a metronome, adjusting the BPM,
 * and resetting the metronome to its default state.
 * </p>
 */
public class Metronome {

    private int bpm;  // Beats per minute of the metronome
    public boolean isPlaying;  // Indicates whether the metronome is currently playing

    /**
     * Constructs a {@code Metronome} with the specified BPM and playing state.
     */
    public Metronome() {
    }

    /**
     * Starts the metronome.
     * <p>
     * This method sets the playing state of the metronome to true.
     * </p>
     */
    public void start() {
        isPlaying = true;
    }

    /**
     * Stops the metronome.
     * <p>
     * This method sets the playing state of the metronome to false.
     * </p>
     */
    public void stop() {
        isPlaying = false;
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

}