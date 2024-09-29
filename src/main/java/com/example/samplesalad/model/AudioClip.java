package com.example.samplesalad.model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;

/**
 * The AudioClip class is responsible for loading, playing, and managing audio files.
 */
public class AudioClip implements LineListener {
    private boolean isPlaybackCompleted;
    private String filePath;
    private AudioInputStream audioStream;
    private URL inputStream;
    private Clip audioClip;

    /**
     * Constructs an AudioClip with an audio file.
     * @param file the file path or name of the audio clip to be played.
     */
    public AudioClip(String file) {
        this.filePath = file;
    }

    /**
     * Callback method for handling LineEvents.
     * Logs when playback starts and completes.
     * @param event the LineEvent triggered during playback.
     */
    @Override
    public void update(LineEvent event) {
        if (LineEvent.Type.START == event.getType()) {
            System.out.println("Playback started.");
        } else if (LineEvent.Type.STOP == event.getType()) {
            isPlaybackCompleted = true;
            System.out.println("Playback completed.");
        }
    }

    /**
     * Loads the audio file into an AudioInputStream.
     * This method should be called before playing the audio.
     * @throws UnsupportedAudioFileException if the audio format is unsupported.
     * @throws IOException if an I/O error occurs while loading the file.
     * @throws LineUnavailableException if the system cannot open the audio line.
     */
    public void loadFile() throws UnsupportedAudioFileException, IOException, LineUnavailableException {


        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("File not found: " + filePath);
            return;
        }

        audioStream = AudioSystem.getAudioInputStream(file);
        audioClip = AudioSystem.getClip();

        audioClip.addLineListener(this);
        audioClip.open(audioStream);
    }

    /**
     * Plays the loaded audio file.
     * If the file is not loaded, it throws an IllegalStateException.
     */
    public void playAudio() {
        if (audioClip == null) {
            throw new IllegalStateException("Audio file not loaded. Call loadFile() before playing.");
        }
        audioClip.start();
    }

    /**
     * Closes the audio clip
     * Should be called after playback is completed or when no longer needed.
     * @throws IOException if an IO error occurs while closing the audio stream.
     */
    public void closeStream() throws IOException {
        if (audioClip != null) {
            audioClip.close();
        }
        if (audioStream != null) {
            audioStream.close();
        }
//        if (inputStream != null) {
//            inputStream.close();
//        }
    }

    /**
     * Checks whether the playback is completed.
     * @return true if playback is complete, otherwise false.
     */
    public boolean isPlaybackCompleted() {
        return isPlaybackCompleted;
    }
}
