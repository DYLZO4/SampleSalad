package com.example.samplesalad.model;

import javax.sound.sampled.*;
import java.io.*;
import javazoom.jl.converter.Converter;



/**
 * The `AudioClip` class is responsible for loading, playing, and managing audio files. It supports WAV and MP3 formats.
 */
public class AudioClip implements LineListener {
    private boolean isPlaybackCompleted;
    private String filePath;
    private AudioInputStream audioStream;
    private Clip audioClip;

    /**
     * Constructs an `AudioClip` with the specified audio file path.
     * @param file The file path or name of the audio clip to be played.
     */
    public AudioClip(String file) {
        this.filePath = file;
    }

    /**
     * Callback method for handling `LineEvent`s during audio playback.
     * This method updates the `isPlaybackCompleted` flag when playback starts or stops.
     * @param event The `LineEvent` triggered during playback.
     */
    @Override
    public void update(LineEvent event) {
        if (LineEvent.Type.START == event.getType()) {
            //System.out.println("Playback started.");
        } else if (LineEvent.Type.STOP == event.getType()) {
            isPlaybackCompleted = true;
            //System.out.println("Playback completed.");
        }
    }

    /**
     * Loads the audio file from the specified file path into an `AudioInputStream`.
     * Handles both WAV and MP3 files.  MP3 files are converted to WAV format internally.
     * @throws UnsupportedAudioFileException If the audio file format is unsupported.
     * @throws IOException If an I/O error occurs while loading the file.
     * @throws LineUnavailableException If the system cannot open the audio line.
     */
    public void loadFile() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("File not found: " + filePath);
            return;
        }

        if (filePath.toLowerCase().endsWith(".mp3")) {
            // Handle MP3 files using JLayer conversion to WAV (unchanged)
            try {
                File tempWavFile = File.createTempFile("temp_audio", ".wav");
                tempWavFile.deleteOnExit();

                Converter converter = new Converter();
                converter.convert(filePath, tempWavFile.getAbsolutePath());

                audioStream = AudioSystem.getAudioInputStream(tempWavFile);
            } catch (Exception e) {
                System.err.println("Error converting MP3: " + e.getMessage());
                throw new UnsupportedAudioFileException("MP3 conversion failed");
            }
        } else if (filePath.toLowerCase().endsWith(".wav")) {
            try {
                AudioInputStream originalStream = AudioSystem.getAudioInputStream(file);
                AudioFormat originalFormat = originalStream.getFormat();

                AudioFormat targetFormat = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);

                if (!originalFormat.matches(targetFormat)) {
                    try {
                        audioStream = AudioSystem.getAudioInputStream(targetFormat, originalStream); // Direct conversion
                    } catch (IllegalArgumentException e1) {
                        // If direct conversion fails, try two-stage conversion:
                        System.out.println("Direct conversion failed, attempting two-stage conversion...");
                        try (AudioInputStream pcmStream = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, originalStream)) {
                            audioStream = AudioSystem.getAudioInputStream(targetFormat, pcmStream);
                        }
                    }
                } else {
                    audioStream = originalStream; // Formats match, no conversion needed
                }

            } catch (Exception e) {
                System.err.println("Error converting WAV: " + e.getMessage());
                throw new UnsupportedAudioFileException("WAV conversion failed: "+ e.getMessage());
            }

        } else {
            throw new UnsupportedAudioFileException("Unsupported audio format: " + filePath);
        }


        audioClip = AudioSystem.getClip();
        audioClip.addLineListener(this);
        audioClip.open(audioStream);
    }

    /**
     * Loads the audio file from the resources folder.
     * @throws UnsupportedAudioFileException if the audio format is unsupported
     * @throws IOException if an IO error occurs while loading the file
     * @throws LineUnavailableException if an error occurs during audio output
     */
    public void loadResource() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // Load the file from the resources folder
        InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(filePath);

        if (resourceStream == null) {
            System.err.println("Resource not found: " + filePath);
            return;
        }

        // Load the audio stream directly
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(resourceStream)) {
            audioClip = AudioSystem.getClip();
            audioClip.addLineListener(this);
            audioClip.open(audioStream);
        }
    }


    /**
     * Plays the loaded audio file.
     * @throws IllegalStateException If the audio file is not loaded.
     */
    public void playAudio() {
        isPlaybackCompleted = false;
        if (audioClip == null) {
            throw new IllegalStateException("Audio file not loaded. Call loadFile() before playing.");
        }
        audioClip.start();
        isPlaybackCompleted = true;
    }

    /**
     * Closes the audio clip and releases resources.
     * This method should be called after playback is completed or when the audio clip is no longer needed.
     * @throws IOException If an I/O error occurs while closing the audio stream.
     */
    public void closeStream() throws IOException {
        if (audioClip != null) {
            audioClip.close();
        }
        if (audioStream != null) {
            audioStream.close();
        }

    }

    /**
     * Checks if the audio playback is completed.
     * @return `true` if playback is complete, `false` otherwise.
     */
    public boolean isPlaybackCompleted() {
        return isPlaybackCompleted;
    }


    /**
     * Plays a segment of the loaded audio file between the specified start and end times.
     * @param startMillisecond The start time of the segment in milliseconds.
     * @param endMillisecond The end time of the segment in milliseconds.
     * @throws IllegalStateException if audio clip has not yet been loaded using loadFile()
     */
    public void playAudio(int startMillisecond, int endMillisecond) {
        isPlaybackCompleted = false;
        if (audioClip == null) {
            throw new IllegalStateException("Audio file not loaded. Call loadFile() before playing.");
        }
        int frameRate = (int) audioClip.getFormat().getFrameRate();
        int startFrame = (int) (startMillisecond / 1000.0 * frameRate);
        int endFrame = (int) (endMillisecond / 1000.0 * frameRate);
        endFrame = Math.min(endFrame, audioClip.getFrameLength());
        audioClip.setFramePosition(startFrame);
        audioClip.start();

        new Thread(() -> {
            try {
                // Sleep for the duration (difference between end and start) in milliseconds
                Thread.sleep(endMillisecond - startMillisecond);
                audioClip.stop();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
        isPlaybackCompleted = true;
    }
}