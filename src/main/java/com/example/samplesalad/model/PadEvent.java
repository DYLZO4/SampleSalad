package com.example.samplesalad.model;

import com.example.samplesalad.model.user.User;

/**
 * A record of a pad activity.
 */
public class PadEvent {
    private Pad pad;
    private int padId;
    private long timeStamp;
    private User user;

    /**
     * Initializes a PadEvent with the triggered pad.
     * @param newPad The pad that was triggered.
     */
    public PadEvent(Pad newPad) {
        this.pad = newPad;
        this.padId = pad.getPadId();
        this.timeStamp = System.currentTimeMillis();
    }

    /**
     * Triggers the event, possibly performing actions related to this event.
     * (Temporarily retrieve current time)
     */
    public void triggerEvent() {
        this.timeStamp = System.currentTimeMillis();
        System.out.println("Event recorded at: " +  timeStamp);
    }

    /**
     * Retrieves the timestamp at which the pad was triggered.
     * @return The timestamp in seconds since the epoch (UNIX time).
     */
    public long getTimeStamp() {
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

    /**
     * Retrieve the corresponding user
     * @return user The user of this PadEvent
     */
    public User getUser(){ return user; }

    /**
     * Sets the user of this PadEvent
     * @param newUser The user to set as this PadEvents user
     */
    public void setUser(User newUser) { user = newUser; }

    public Pad getPad() {
        return pad;
    }
}
