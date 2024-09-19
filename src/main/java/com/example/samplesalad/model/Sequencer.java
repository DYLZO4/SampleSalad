package com.example.samplesalad.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the arrangement and playback of patterns, enabling the user to record,
 * play, and edit sequences of pad events over time.
 */
public class Sequencer {

    private int sequencerID;
    private List<Pattern> patterns;
    private int tempo;
    private int timeSignatureNumerator;
    private int timeSignatureDenominator;
    private boolean isPlaying;

    /**
     * Initialised with the tempo and time signature numerator and denominator
     */
    public Sequencer(int tempo, int numerator, int denominator) {
        this.patterns = new ArrayList<>();
        this.tempo = tempo;
        this.timeSignatureNumerator = numerator;
        this.timeSignatureDenominator = denominator;
        this.isPlaying = false;
    }

    /**
     * Will record a new pattern
     *
     * @param pattern An instance of a pattern
     */
    public void recordPattern(Pattern pattern) {
        //Logic for recording pattern
        System.out.println("Pattern recorded.");
    }

    /**
     * Plays the sequence of patterns in order
     */
    public void playSequence() {
        if (patterns.isEmpty()) {
            System.out.println("No patterns to play.");
            return;
        }

        isPlaying = true;
        System.out.println("Playing sequence at " + tempo + " bpm, " +
                timeSignatureNumerator + "/" + timeSignatureDenominator + " time signature.");

        for (Pattern pattern : patterns) {
            // Logic to play patterns
            System.out.println("Playing pattern with " + pattern.getPadEvents().size() + " events.");
        }

    }

    /**
     * Stops the sequence
     */
    public void stopSequence() {
        if (!isPlaying) {
            System.out.println("Sequence is not currently playing.");
            return;
        }

        isPlaying = false;
        System.out.println("Sequence stopped.");
    }

    /**
     * Returns the tempo of the sequence in beats per minute
     *
     * @return tempo The tempo of the sequence in beats per minute
     */
    public int getTempo() {
        return tempo;
    }

    /**
     * Sets the tempo of the sequence in beats per minute
     *
     * @param bpm The desired tempo
     */
    public void setTempo(int bpm) {
        this.tempo = bpm;
        System.out.println("Tempo set to " + bpm + " bpm.");
    }

    /**
     * Sets the time signature of the sequence.
     *
     * @param numerator   The numerator of the time signature.
     * @param denominator The denominator of the time signature.
     */
    public void setTimeSignature(int numerator, int denominator) {
        this.timeSignatureNumerator = numerator;
        this.timeSignatureDenominator = denominator;
        System.out.println("Time signature set to " + numerator + "/" + denominator + ".");
    }

    /**
     * Returns the numerator of the time signature
     *
     * @return timeSignatureNumerator The numerator of the time signature
     */
    public int getTimeSignatureNumerator() {
        return timeSignatureNumerator;
    }

    /**
     * Returns the denominator of the time signature
     *
     * @return timeSignatureDenominator The Denominator of the time signature
     */
    public int getTimeSignatureDenominator() {
        return timeSignatureDenominator;
    }

    /**
     * Adds a pattern to the sequence
     *
     * @param pattern The pattern to add
     */
    public void addPattern(Pattern pattern) {
        patterns.add(pattern);
        System.out.println("Pattern added to the sequence.");
    }

    /**
     * Removes a pattern from the sequence
     *
     * @param pattern The pattern to remove
     */
    public void removePattern(Pattern pattern) {
        patterns.remove(pattern);
        System.out.println("Pattern removed from the sequence.");
    }

    /**
     * Returns the list of patterns in the sequence
     *
     * @return A list of patterns
     */
    public List<Pattern> getPatterns() {
        return patterns;
    }

    /**
     * Checks if the sequence is currently playing
     *
     * @return true if the sequence is playing, false otherwise
     */
    public boolean isPlaying() {
        return isPlaying;
    }


    public void setSequencerID(int sequencerID) {
        this.sequencerID = sequencerID;
    }

    public int getSequencerID(){return sequencerID;}
}