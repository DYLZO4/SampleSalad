package com.example.samplesalad.model;

import com.example.samplesalad.model.PadEvent;
import com.example.samplesalad.model.user.User;
import javafx.application.Platform;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Pattern {
    private long startTime;
    private int patternID;
    private int length;
    private int BPM;
    private List<PadEvent> padEvents;
    private Timer recordingTimer;
    private User user;
    private boolean isPlaying;
    private final ReentrantLock audioLock = new ReentrantLock(); // Keep if AudioClip isn't thread-safe
    private boolean isRecording;
    private List<PadEvent> currentRecordingEvents;
    private ScheduledExecutorService scheduler;

    public Pattern(int length, int BPM) {
        this.length = length;
        this.BPM = BPM;
        this.padEvents = new ArrayList<>();
        this.isPlaying = false;
        this.isRecording = false;
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public void addPadEvent(PadEvent event) {
        if (isRecording) {
            currentRecordingEvents.add(event);
        } else {
            padEvents.add(event);
        }
    }

    public void removePadEvent(PadEvent event) {
        padEvents.remove(event);
    }

    public List<PadEvent> getPadEvents() {
        return padEvents;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setPatternID(int patternID) {
        this.patternID = patternID;
    }

    public int getPatternID() {
        return patternID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User newUser) {
        user = newUser;
    }

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

    public void endRecordPattern() {
        if (recordingTimer != null) {
            recordingTimer.cancel();
            recordingTimer = null;
            isRecording = false;

            padEvents.addAll(currentRecordingEvents);
            padEvents.sort(Comparator.comparingLong(PadEvent::getTimeStamp));
            currentRecordingEvents = null;

            System.out.println("Recording Ended");
            // Consider removing auto-play here if not desired behavior
            playPattern();
            startPattern();
        }
    }

    public long getStartTime() {
        return startTime;
    }

    public void startPattern() {
        isPlaying = true;
    }


    public void playPattern() {
        if (padEvents.isEmpty()) {
            System.out.println("No events recorded.");
            return;
        }

        long loopStartTime = System.currentTimeMillis();

        for (PadEvent event : padEvents) {
            long eventTime = event.getTimeStamp();
            long delayFromLoopStart = eventTime; //- loopStartTime;  // Correct delay calculation

            scheduler.schedule(() -> {
                try {
                    audioLock.lock();
                    try {
                        event.getPad().getAudioClip().loadFile();
                        event.getPad().getAudioClip().playAudio();
                    } finally {
                        audioLock.unlock();
                    }
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                    e.printStackTrace();
                }
            }, delayFromLoopStart, TimeUnit.MILLISECONDS);
        }

        long loopDuration = calculateRecordingDuration();
        scheduler.schedule(() -> {
            if (isPlaying) {
                playPattern();
            }
        }, loopDuration, TimeUnit.MILLISECONDS);
    }


    public void stopPattern() {
        isPlaying = false;
        scheduler.shutdownNow();
        try {
            if(!scheduler.awaitTermination(500, TimeUnit.MILLISECONDS)){
                System.out.println("Scheduler did not terminate");
            }

        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        scheduler = Executors.newScheduledThreadPool(1);


    }



    private long calculateRecordingDuration() {
        return (long) (length * (60000.0 / BPM) * 4); // Correct calculation, assuming beats of 4
    }

    public boolean getIsPlaying() {
        return isPlaying;
    }

    public boolean isRecording() {
        return isRecording;
    }


    public void setBPM(int bpm) {
        this.BPM = bpm;
    }

}