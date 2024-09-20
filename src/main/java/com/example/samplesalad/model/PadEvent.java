package com.example.samplesalad.model;

/**
 * A record of a pad activity.
 */
public class PadEvent {
    private Pad pad; // The pad associated with this event
    private int padId; // The ID of the triggered pad
    private double timeStamp; // The time at which the pad was triggered

    /**
     * Initializes a PadEvent with the triggered pad.
     * @param newPad The pad that was triggered.
     */
    public PadEvent(Pad newPad) {
        this.pad = newPad;
        this.padId = pad.getPadId();
        this.timeStamp = System.currentTimeMillis() / 1000.0;
    }

    /**
     * Triggers the event, possibly performing actions related to this event.
     * (Temporarily retrieve current time)
     */
    public void triggerEvent() {
        this.timeStamp = (double) System.currentTimeMillis() / 1000.0;
        System.out.println("Event recorded at: " +  timeStamp);
    }

    /**
     * Retrieves the timestamp at which the pad was triggered.
     * @return The timestamp in seconds since the epoch (UNIX time).
     */
    public double getTimeStamp() {
        System.out.println("Current time: " +  timeStamp);
        return timeStamp;
    }

    /**
     * Retrieves the pad ID associated with this event.
     * @return The ID of the triggered pad.
     */
    public int getPadID() {
        System.out.println("Recording events for pad" +  padId);
        return padId;
    }
}
