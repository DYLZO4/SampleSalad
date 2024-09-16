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

    public void setSample(Sample newSample) {
        sample = newSample;
    };
    public Sample getSample() {
        return sample;
    };


    /**
     * Triggers the sample audio to play if it's assigned and not currently playing.
     */
    public void triggerPad() {
        //Logic for triggering pad
        System.out.println("Pad" +  padId + " has been triggered");
    };

    /**
     * Mutes the pad's assigned sample
     */
    public void mutePad() {
        //Logic for muting pad
        System.out.println("pad" + padId + " is muted");
    };

    /**
     * Unmutes the pad's sample
     */
    public void unmutePad() {
        //Logic for unmuting pad
        System.out.println("pad" + padId + " is unmuted");
    };


    public void setVolume(double volume) {
        this.volume = volume;
        if (sample != null) {
            sample.setVolume(volume);
            System.out.println("Volume was changed to " + volume);
        }
    };


    public String getKeybind() {
        System.out.println("keybind is " + keybind);
        return keybind;
    }

    public void setKeybind(String newKeybind) {
        System.out.println("keybind has been set to " + newKeybind);
        this.keybind = newKeybind;
    }

    /**
     * Returns true if the sample audio is currently playing, false otherwise
     * @return true if the sample audio is currently playing, false otherwise
     */
    public boolean isPlaying() {
        System.out.println("pad" +  padId +  " is currently playing");
        return false;
    };


    public int getPadId() {
        System.out.println("Current pad: pad" +  padId);
        return padId;
    }
    public void setPadId(int padId) {
        System.out.println("Pad has been set as pad" +  padId);
        this.padId = padId;
    }
}
