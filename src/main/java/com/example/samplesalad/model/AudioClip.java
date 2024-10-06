package com.example.samplesalad.model;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;

import javazoom.jl.converter.Converter;
import javax.sound.sampled.*;
import java.io.*;


/**
 * The AudioClip class is responsible for loading, playing, and managing audio files.
 */
public class AudioClip implements LineListener {
    private boolean isPlaybackCompleted;
    private String filePath;
    private AudioInputStream audioStream;
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
     * Loads the audio file into an AudioInputStream. Handles both WAV and MP3.
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

    /**
     * Slices a portion of the original audio file and saves in a new audio file.
     * @param srcFileName the source file to slice from
     * @param newFileName the destination file to save the sliced audio to
     * @param startSecond the start second of the sliced audio
     * @param endSecond the end second of the sliced audio
     */
    public static void copyAudio(String srcFileName, String newFileName, int startSecond, int endSecond) {
        AudioInputStream inputStream = null;
        AudioInputStream slicedStream = null;
        try {
            File file = new File(srcFileName);
            AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
            AudioFormat format = fileFormat.getFormat();
            inputStream = AudioSystem.getAudioInputStream(file);
            int bytesPerSecond = format.getFrameSize() * (int)format.getFrameRate();
            inputStream.skip(startSecond * bytesPerSecond);
            int secondsToSlice = endSecond - startSecond;
            long framesOfAudioToCopy = secondsToSlice * (int)format.getFrameRate();
            slicedStream = new AudioInputStream(inputStream, format, framesOfAudioToCopy);
            File newFile = new File(newFileName);
            AudioSystem.write(slicedStream, fileFormat.getType(), newFile);
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (inputStream != null) try { inputStream.close(); } catch (Exception e) { System.err.println(e); }
            if (slicedStream != null) try { slicedStream.close(); } catch (Exception e) { System.err.println(e); }
        }
    }
}