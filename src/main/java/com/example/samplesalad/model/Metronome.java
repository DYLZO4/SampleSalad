package com.example.samplesalad.model;

/**
 * The Metronome class represents a simple metronome with basic start/stop
 * functionality and BPM (beats per minute) control.
 */
public class Metronome {

    private int bpm;
    public boolean isPlaying;

    /**
     * Starts the metronome.
     */
    public void start(){
        isPlaying = true;
    }

    /**
     * Stops the metronome.
     */
    public void stop(){
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
    public void setBpm(int bpm){
        this.bpm = bpm;
    }

    /**
     * Toggles the playing state of the metronome.
     * If the metronome is currently playing, it will stop, and vice versa.
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
     * @return true if the metronome is playing, false otherwise.
     */
    public boolean isPlaying() {
        return isPlaying;
    }

}

