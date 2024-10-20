package com.example.samplesalad.model;

/**
 * A record of a pad activity, storing the pad, its ID, the timestamp of the event, and the associated user.
 */
public class PadEvent {
    private Pad pad;
    private int padId;
    private long timeStamp;
    private User user;

    /**
     * Initializes a `PadEvent` with the triggered pad and the start time of the pattern.
     * The timestamp is calculated relative to the pattern start time.
     *
     * @param newPad       The pad that was triggered.
     * @param patternStart The system time in milliseconds when the pattern started.
     */
    public PadEvent(Pad newPad, long patternStart) {
        this.pad = newPad;
        this.padId = newPad.getPadId(); // Get the pad ID directly from the Pad object
        this.timeStamp = System.currentTimeMillis() - patternStart;
    }

    /**
     * Triggers the event and updates the timestamp to the current system time.
     * This method might be used for real-time event triggering, but the current implementation
     * simply updates the timestamp and prints a debug message.  Consider removing the timestamp
     * update here if this method is intended for playback of recorded events.
     */
    public void triggerEvent() {
        this.timeStamp = System.currentTimeMillis();
        System.out.println("Event recorded at: " + timeStamp);
    }

    /**
     * Retrieves the timestamp of the event.
     *
     * @return The timestamp in milliseconds since the epoch (UNIX time) or relative to the pattern start.
     */
    public long getTimeStamp() {
        System.out.println("Current time: " + timeStamp);
        return timeStamp;
    }

    /**
     * Retrieves the ID of the pad associated with this event.
     *
     * @return The ID of the triggered pad.
     */
    public int getPadID() {
        System.out.println("Recording events for pad " + padId); // Corrected debug message formatting.
        return padId;
    }

    /**
     * Retrieves the user associated with this event.
     *
     * @return The user who triggered the event.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with this event.
     *
     * @param newUser The user to associate with the event.
     */
    public void setUser(User newUser) {
        user = newUser;
    }

    /**
     * Gets the Pad object associated with this PadEvent.
     * @return The Pad object.
     */
    public Pad getPad() {
        return pad;
    }
}
