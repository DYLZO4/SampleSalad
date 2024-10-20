package com.example.samplesalad.model;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * A triggerable object representing a pad (like on an Akai MPC), which holds and plays a sample.
 * Includes properties for volume, pitch, BPM, key binding, and associated user.
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
     * Initializes a new Pad with the given sample, setting default volume, BPM, and pitch to 1.
     *
     * @param sample The sample to be assigned to this pad.
     */
    public Pad(Sample sample) {
        this.sample = sample;
        this.volume = 1;
        this.bpm = 1;
        this.pitch = 1;
        this.audioClip = null;
    }

    /**
     * Sets a new sample for this pad.
     *
     * @param newSample The new sample to assign.
     */
    public void setSample(Sample newSample) {
        sample = newSample;
    }

    /**
     * Gets the sample currently assigned to this pad.
     *
     * @return The assigned sample.
     */
    public Sample getSample() {
        return sample;
    }

    /**
     * Gets the volume of this pad.
     *
     * @return The current volume.
     */
    public Double getVolume() {
        return volume;
    }

    /**
     * Triggers the pad to play its assigned sample.  Currently prints a debug message.  Actual playback logic needs to be implemented.
     */
    public void triggerPad() {
        // Logic for triggering the pad's sample
        System.out.println("Pad " + padID + " has been triggered");
    }

    /**
     * Mutes the pad. Currently prints a debug message. Actual muting logic needs to be implemented.
     */
    public void mutePad() {
        // Logic for muting the pad's sample
        System.out.println("Pad " + padID + " is muted");
    }

    /**
     * Unmutes the pad. Currently prints a debug message.  Actual unmuting logic needs to be implemented.
     */
    public void unmutePad() {
        // Logic for unmuting the pad's sample
        System.out.println("Pad " + padID + " is unmuted");
    }

    /**
     * Sets the volume for this pad.
     *
     * @param volume The new volume to set (typically between 0.0 and 1.0).
     */
    public void setVolume(double volume) {
        this.volume = volume;
        if (sample != null) {
            System.out.println("Volume was changed to " + volume);
        }
    }

    /**
     * Gets the keybind associated with this pad.
     *
     * @return The current keybind.
     */
    public String getKeybind() {
        System.out.println("Keybind is " + keybind);
        return keybind;
    }

    /**
     * Sets the keybind for this pad.
     *
     * @param newKeybind The new keybind to assign.
     */
    public void setKeybind(String newKeybind) {
        System.out.println("Keybind has been set to " + newKeybind);
        this.keybind = newKeybind;
    }

    /**
     * Checks if the pad's sample is currently playing.
     * Currently always returns `false`.  Needs implementation to check actual playback status.
     *
     * @return `true` if the sample is playing, `false` otherwise.
     */
    public boolean isPlaying() {
        System.out.println("Pad " + padID + " is currently playing");
        return false;
    }

    /**
     * Gets the ID of this pad.
     *
     * @return The pad ID.
     */
    public int getPadId() {
        return padID;
    }

    /**
     * Sets the ID of this pad.
     *
     * @param padId The new ID to assign.
     */
    public void setPadId(int padId) {
        System.out.println("Pad has been set as pad " + padId);
        this.padID = padId;
    }

    /**
     * Gets the user associated with this pad.
     *
     * @return The associated user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with this pad.
     *
     * @param newUser The user to associate.
     */
    public void setUser(User newUser) {
        this.user = newUser;
    }

    /**
     * Sets the BPM (Beats Per Minute) for this pad.
     *
     * @param BPM The new BPM value.
     */
    public void setBPM(int BPM) {
        this.bpm = BPM;
    }

    /**
     * Gets the BPM (Beats Per Minute) for this pad.
     *
     * @return The current BPM value.
     */
    public int getBPM() {
        return bpm;
    }

    /**
     * Sets the pitch for this pad.
     *
     * @param pitch The new pitch value.
     */
    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    /**
     * Gets the pitch for this pad.
     *
     * @return The current pitch value.
     */
    public double getPitch() {
        return pitch;
    }


    /**
     * Gets the AudioClip for this Pad
     * @return The audioclip of this Pad
     */
    public AudioClip getAudioClip() {
        return audioClip;
    }

    /**
     * Sets the AudioClip for this Pad
     * @param audioClip The audioclip to set
     * @throws UnsupportedAudioFileException If the file isn't supported
     * @throws LineUnavailableException if the audio line is unavailable
     * @throws IOException If the file can't be found or loaded
     */
    public void setAudioClip(AudioClip audioClip) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.audioClip = audioClip;
        this.audioClip.loadFile();
    }
}