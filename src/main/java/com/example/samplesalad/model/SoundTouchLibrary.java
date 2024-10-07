package com.example.samplesalad.model;

import com.sun.jna.*; // JNA imports

public interface SoundTouchLibrary extends Library {
    SoundTouchLibrary INSTANCE = (SoundTouchLibrary) Native.load("soundtouch", SoundTouchLibrary.class); // Load the SoundTouch library

    // Example SoundTouch function mappings.  You'll need ALL the functions you intend to use.
    Pointer soundtouch_createInstance();  // Create a SoundTouch instance
    void soundtouch_destroyInstance(Pointer handle);
    void soundtouch_setTempoChange(Pointer handle, float tempoChange);
    void soundtouch_setPitchSemiTones(Pointer handle, float pitchSemiTones);
    // ... many more functions ... (input, output, flush, etc.) 
}