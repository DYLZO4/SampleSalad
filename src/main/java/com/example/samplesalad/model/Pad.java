package com.example.samplesalad.model;

/**
 * A triggerable object that holds and plays a sample,
 * with properties for sensitivity, volume, and muting,
 * simulating the physical pads on an Akai MPC.
 */
public class Pad {
    private int padId;
    private Sample sample;
    private double volume;
    private String keybind;


    /**
     *
     */
    public Pad(Sample sample) {
        this.sample = sample;
    }

    /**
     * Assigns an instance of Sample to the "sample" field in pad
     */
    void assignSample(Sample newSample) {
        sample = newSample;
    };

    /**
     * Triggers the sample audio to play if it's assigned and not currently playing.
     */
    void triggerPad() {
        if (sample != null && !sample.isPlaying()) {
            sample.play(); // Play the assigned sample
        }
    };

    /**
     * Mutes the pad's assigned sample
     */
    void mutePad() {
        if (sample != null) {
            sample.mute();
        }
    };

    /**
     * Unmutes the pad's sample
     */
    void unmutePad() {
        if (sample != null) {
            sample.unmute();
        }
    };

    /**
     * Sets the volume of the pad's sample
     * @param volume
     */
    void setVolume(float volume) {
        this.volume = volume;
        if (sample != null) {
            sample.setVolume(volume);
        }
    };

    /**
     * Retrieves the keybind
     * @return
     */
    public String getKeybind() {
        return keybind;
    }

    /**
     * Sets the keybind
     * @param keybind
     */
    public void setKeybind(String keybind) {
        this.keybind = keybind;
    }

    /**
     * Returns true if the sample audio is currently playing, false otherwise
     * @return true if the sample audio is currently playing, false otherwise
     */
    boolean isPlaying() {
        return sample != null && sample.isPlaying();
    };

    /**
     * Gets the pad ID.
     * @return The ID of the pad.
     */
    public int getPadId() {
        return padId;
    }

    /**
     * Sets the pad ID.
     * @param padId The ID to be assigned to this pad.
     */
    public void setPadId(int padId) {
        this.padId = padId;
    }
}
