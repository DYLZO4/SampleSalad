package com.example.samplesalad.model;

import com.example.samplesalad.model.user.User;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * A triggerable object that holds and plays a sample,
 * with properties for sensitivity, volume, and key binding,
 * simulating the physical pads on an Akai MPC.
 */
public class Pad {
    private int padID;
    private Sample sample;
    private double volume;
    private double pitch;
    private int bpm;
    private String keybind;
    private User user;
    private AudioClip audioClip;

    /**
     * Initializes the pad with a specific sample.
     *
     * @param sample the sample object to be assigned to this pad
     */
    public Pad(Sample sample) {
        this.sample = sample;
        this.volume = 1;
        this.bpm = 1;
        this.pitch = 1;
        this.audioClip = null;
    }

    /**
     * Sets a new sample to this pad.
     *
     * @param newSample the new sample object to be assigned
     */
    public void setSample(Sample newSample) {
        sample = newSample;
    }

    /**
     * Retrieves the sample assigned to this pad.
     *
     * @return the sample object assigned to this pad
     */
    public Sample getSample() {
        return sample;
    }

    public Double getVolume() {
        return volume;
    }

    /**
     * Triggers the sample audio to play if it's assigned and not currently playing.
     */
    public void triggerPad() {
        // Logic for triggering the pad's sample
        System.out.println("Pad " + padID + " has been triggered");
    }

    /**
     * Mutes the sample assigned to this pad.
     */
    public void mutePad() {
        // Logic for muting the pad's sample
        System.out.println("Pad " + padID + " is muted");
    }

    /**
     * Unmutes the sample assigned to this pad.
     */
    public void unmutePad() {
        // Logic for unmuting the pad's sample
        System.out.println("Pad " + padID + " is unmuted");
    }

    /**
     * Sets the volume for the sample assigned to this pad.
     *
     * @param volume the volume to set for the sample
     */
    public void setVolume(double volume) {
        this.volume = volume;
        if (sample != null) {
            sample.setVolume(volume);
            System.out.println("Volume was changed to " + volume);
        }
    }

    /**
     * Retrieves the keybind mapped to this pad.
     *
     * @return the key binding currently mapped to the pad
     */
    public String getKeybind() {
        System.out.println("Keybind is " + keybind);
        return keybind;
    }

    /**
     * Sets a new keybind for this pad.
     *
     * @param newKeybind the new key binding to map to the pad
     */
    public void setKeybind(String newKeybind) {
        System.out.println("Keybind has been set to " + newKeybind);
        this.keybind = newKeybind;
    }

    /**
     * Checks whether the sample assigned to this pad is currently playing.
     *
     * @return true if the sample is playing, false otherwise
     */
    public boolean isPlaying() {
        System.out.println("Pad " + padID + " is currently playing");
        return false; // This should be updated with actual logic to check playback
    }

    /**
     * Gets the ID of this pad.
     *
     * @return the pad ID
     */
    public int getPadId() {
//        System.out.println("Current pad: pad " + padID);
        return padID;
    }

    /**
     * Sets the ID for this pad.
     *
     * @param padId the ID to be assigned to this pad
     */
    public void setPadId(int padId) {
        System.out.println("Pad has been set as pad " + padId);
        this.padID = padId;
    }

    /**
     * Retrieve the corresponding user
     *
     * @return user The user of this Pad
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user of this Pad
     *
     * @param newUser The user to set as this Pads user
     */
    public void setUser(User newUser) {
        this.user = newUser;
    }


    public void setBPM(int BPM) {
        this.bpm = BPM;
    }

    public int getBPM(){
        return bpm;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public double getPitch(){
        return pitch;
    }

    public AudioClip getAudioClip() {
        return audioClip;
    }

    public void setAudioClip(AudioClip audioClip) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.audioClip = audioClip;
        this.audioClip.loadFile();
    }
}