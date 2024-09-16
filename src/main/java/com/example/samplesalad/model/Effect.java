package com.example.samplesalad.model;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code Effect} class represents an audio effect that can be applied to a {@code Sample}.
 * <p>
 * Each effect has a type (e.g., "Reverb", "Delay") and a set of parameters that configure how the effect is applied.
 * </p>
 */
public class Effect {
    private String effectType; // Example: "Reverb", "Delay", etc.
    private Map<String, Float> parameters; // Parameters for the effect

    /**
     * Constructs an {@code Effect} with the specified effect type.
     * <p>
     * Initializes an empty set of parameters for the effect.
     * </p>
     *
     * @param effectType the type of the effect (e.g., "Reverb", "Delay")
     */
    public Effect(String effectType) {
        this.effectType = effectType;
        this.parameters = new HashMap<>(); // Initialize parameters map
    }

    /**
     * Returns the type of the effect.
     *
     * @return the effect type
     */
    public String getEffectType() {
        return effectType;
    }

    /**
     * Sets the type of the effect.
     *
     * @param effectType the effect type to set
     */
    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    /**
     * Returns the parameters of the effect.
     * <p>
     * The parameters are represented as a map where the keys are parameter names
     * and the values are the parameter values.
     * </p>
     *
     * @return the map of parameters
     */
    public Map<String, Float> getParameters() {
        return parameters;
    }

    /**
     * Applies the effect to a {@code Sample}.
     * <p>
     * This method simulates the application of the effect to the sample. In a real
     * implementation, it would adjust the sample's properties based on the effect type
     * and parameters.
     * </p>
     *
     * @param sample the {@code Sample} to which the effect is applied
     */
    public void apply(Sample sample) {
        // Logic to apply the effect to a sample
        System.out.println("Applying " + effectType + " to sample: " + sample.getSampleID());
        // Simulate effect application by adjusting sample properties (e.g., pitch, volume)
        // based on effect type and parameters.
    }

    /**
     * Sets a parameter for the effect.
     * <p>
     * This method updates the value of a specific parameter for the effect.
     * </p>
     *
     * @param name  the name of the parameter
     * @param value the value to set for the parameter
     */
    public void setParameter(String name, float value) {
        parameters.put(name, value);
        System.out.println("Set parameter " + name + " to " + value + " for effect " + effectType);
    }

    /**
     * Returns the value of a parameter.
     * <p>
     * If the parameter is not found, a default value of 0.0f is returned.
     * </p>
     *
     * @param name the name of the parameter
     * @return the value of the parameter, or 0.0f if the parameter is not found
     */
    public float getParameter(String name) {
        return parameters.getOrDefault(name, 0.0f); // Default value if parameter not found
    }
}
