package com.example.samplesalad.model;

import java.util.HashMap;
import java.util.Map;

public class Effect {
    private String effectType; // Example: "Reverb", "Delay", etc.
    private Map<String, Float> parameters; // Parameters for the effect

    // Constructor
    public Effect(String effectType) {
        this.effectType = effectType;
        this.parameters = new HashMap<>(); // Initialize parameters map
    }

    // Getters and Setters
    public String getEffectType() {
        return effectType;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    public Map<String, Float> getParameters() {
        return parameters;
    }

    // Methods
    public void apply(Sample sample) {
        // Logic to apply the effect to a sample
        System.out.println("Applying " + effectType + " to sample: " + sample.getSampleID());
        // Simulate effect application by adjusting sample properties (e.g., pitch, volume)
        // based on effect type and parameters.
    }

    public void setParameter(String name, float value) {
        parameters.put(name, value);
        System.out.println("Set parameter " + name + " to " + value + " for effect " + effectType);
    }

    public float getParameter(String name) {
        return parameters.getOrDefault(name, 0.0f); // Default value if parameter not found
    }
}