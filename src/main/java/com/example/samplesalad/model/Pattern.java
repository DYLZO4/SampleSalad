package com.example.samplesalad.model;

import javafx.application.Platform;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents a musical pattern, containing a sequence of timed `PadEvent`s.
 * Supports recording, playback, and control over length and BPM.
 */
public class Pattern {
    private long startTime;
    private int patternID;
    private int length;
    private int BPM;
    private List<PadEvent> padEvents;
    private Timer recordingTimer;
    private User user;
    private boolean isPlaying;
    private final ReentrantLock audioLock = new ReentrantLock(); // Used to synchronize audio playback if AudioClip is not thread-safe
    private boolean isRecording;
    private List<PadEvent> currentRecordingEvents;
    private ScheduledExecutorService scheduler;

    /**
     * Creates a new `Pattern` with the specified length and BPM.
     *
     * @param length The length of the pattern in beats.
     * @param BPM    The tempo of the pattern in beats per minute.
     */
    public Pattern(int length, int BPM) {
        this.length = length;
        this.BPM = BPM;
        this.padEvents = new ArrayList<>();
        this.isPlaying = false;
        this.isRecording = false;
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    /**
     * Adds a `PadEvent` to the pattern.  If recording, the event is added to the current recording; otherwise, it's added to the main event list.
     *
     * @param event The `PadEvent` to add.
     */
    public void addPadEvent(PadEvent event) {
        if (isRecording) {
            currentRecordingEvents.add(event);
        } else {
            padEvents.add(event);
        }
    }

    /**
     * Removes a `PadEvent` from the pattern.
     *
     * @param event The `PadEvent` to remove.
     */
    public void removePadEvent(PadEvent event) {
        padEvents.remove(event);
    }

    /**
     * Gets the list of `PadEvent`s in this pattern.
     *
     * @return The list of `PadEvent`s.
     */
    public List<PadEvent> getPadEvents() {
        return padEvents;
    }

    /**
     * Gets the length of the pattern in beats.
     *
     * @return The pattern length.
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the length of the pattern in beats.
     *
     * @param length The new pattern length.
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Sets the ID of the pattern.
     *
     * @param patternID The new pattern ID.
     */
    public void setPatternID(int patternID) {
        this.patternID = patternID;
    }

    /**
     * Gets the ID of the pattern.
     *
     * @return The pattern ID.
     */
    public int getPatternID() {
        return patternID;
    }

    /**
     * Gets the user associated with this pattern.
     *
     * @return The associated user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with this pattern.
     *
     * @param newUser The user to associate.
     */
    public void setUser(User newUser) {
        user = newUser;
    }

    /**
     * Starts recording a pattern.  Initializes recording state, starts a timer for the recording duration, and prints a debug message.
     */
    public void startRecordPattern() {
        if (!isRecording) {
            isRecording = true;
            startTime = System.currentTimeMillis();
            currentRecordingEvents = new ArrayList<>();

            long recordingDuration = calculateRecordingDuration();
            recordingTimer = new Timer();
            recordingTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> endRecordPattern());
                }
            }, recordingDuration);

            System.out.println("Recording Started");
        }
    }

    /**
     * Ends the recording of a pattern. Stops the timer, adds the recorded events to the pattern,
     * sorts the events by timestamp, and then automatically plays the recorded pattern.
     */
    public void endRecordPattern() {
        if (recordingTimer != null) {
            recordingTimer.cancel();
            recordingTimer = null;
            isRecording = false;

            padEvents.addAll(currentRecordingEvents);
            padEvents.sort(Comparator.comparingLong(PadEvent::getTimeStamp));
            currentRecordingEvents = null;

            System.out.println("Recording Ended");
            playPattern();
            startPattern();
        }
    }


    /**
     * Gets the start time of the pattern recording.
     *
     * @return The start time in milliseconds.
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Starts playback of the pattern. Sets the `isPlaying` flag to true.
     */
    public void startPattern() {
        isPlaying = true;
    }

    /**
     * Plays the pattern. Schedules each `PadEvent` to play at its designated time, using a `ScheduledExecutorService`.
     * The pattern loops continuously if `isPlaying` is true.
     */
    public void playPattern() {
        if (padEvents.isEmpty()) {
            System.out.println("No events recorded.");
            return;
        }

        for (PadEvent event : padEvents) {
            long eventTime = event.getTimeStamp();
            long delayFromLoopStart = eventTime; // This calculation is now correct.  Removed the subtraction of loopStartTime.

            scheduler.schedule(() -> {
                try {
                    audioLock.lock();  // Acquire the lock before playing audio (if needed for thread safety)
                    try {
                        event.getPad().getAudioClip().loadFile(); // Should this be outside the loop? Consider performance.
                        if (event.getPad().getSample().getEndTime() == 0) {
                            event.getPad().getAudioClip().playAudio();
                        } else {
                            event.getPad().getAudioClip().playAudio(event.getPad().getSample().getStartTime(), event.getPad().getSample().getEndTime());
                        }
                    } finally {
                        audioLock.unlock(); // Release the lock in a finally block
                    }
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                    e.printStackTrace();
                }
            }, delayFromLoopStart, TimeUnit.MILLISECONDS);
        }

        long loopDuration = calculateRecordingDuration();
        scheduler.schedule(() -> {
            if (isPlaying) {
                playPattern(); // Loop the pattern if still playing
            }
        }, loopDuration, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops the pattern playback and shuts down the scheduler.
     */
    public void stopPattern() {
        isPlaying = false;
        scheduler.shutdownNow(); // Immediately stops the scheduler
        try {
            if (!scheduler.awaitTermination(500, TimeUnit.MILLISECONDS)) {
                System.out.println("Scheduler did not terminate");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        scheduler = Executors.newScheduledThreadPool(1); // Create a new scheduler for future use.

    }


    /**
     * Calculates the total duration of the pattern recording in milliseconds.
     * Assumes a standard 4/4 time signature.
     *
     * @return The calculated duration in milliseconds.
     */
    private long calculateRecordingDuration() {
        return (long) (length * (60000.0 / BPM) * 4);
    }

    /**
     * Checks if the pattern is currently playing.
     *
     * @return `true` if playing, `false` otherwise.
     */
    public boolean getIsPlaying() {
        return isPlaying;
    }

    /**
     * Checks if the pattern is currently recording.
     *
     * @return `true` if recording, `false` otherwise.
     */
    public boolean isRecording() {
        return isRecording;
    }


    /**
     * Sets the BPM (Beats Per Minute) for this pattern.
     * @param bpm The new BPM value.
     */
    public void setBPM(int bpm) {
        this.BPM = bpm;
    }

    /**
     * Sets the pattern length in beats.
     * @param value the length of the pattern in beats.
     */
    public void setPatternLength(Integer value) {
        this.length = value;
    }
}