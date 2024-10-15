import com.example.samplesalad.model.AudioClip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AudioClipTest {

    private AudioClip audioClipWav;
    private AudioClip audioClipMp3;
    private AudioClip audioClipInvalid;

    @BeforeEach
    void setUp() {
        audioClipWav = new AudioClip("C:/Users/delac/IdeaProjects/SampleSalad/759007__looplicator__178-bpm-industrial-drum-loop-5988-wav.wav");
        audioClipMp3 = new AudioClip("C:/Users/delac/IdeaProjects/SampleSalad/425556__t_roy_920__rock-808-beat.mp3");
        audioClipInvalid = new AudioClip("C:/Users/delac/IdeaProjects/SampleSalad/README.md");
    }

    @Test
    void testLoadWavFile() {
        try {
            audioClipWav.loadFile();
            assertNotNull(audioClipWav.isPlaybackCompleted());
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            fail("Exception should not be thrown for a valid WAV file.");
        }
    }

    @Test
    void testLoadMp3File() {
        try {
            audioClipMp3.loadFile();
            assertNotNull(audioClipMp3.isPlaybackCompleted());
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            fail("Exception should not be thrown for a valid MP3 file.");
        }
    }

    @Test
    void testLoadInvalidFile() {
        assertThrows(UnsupportedAudioFileException.class, () -> {
            audioClipInvalid.loadFile();
        });
    }

    @Test
    void testPlayAudioWithoutLoading() {
        assertThrows(IllegalStateException.class, () -> audioClipWav.playAudio());
    }

    @Test
    void testPlayAudio() {
        try {
            audioClipWav.loadFile();
            audioClipWav.playAudio();
            assertTrue(audioClipWav.isPlaybackCompleted());
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            fail("Exception should not be thrown for playing a valid WAV file.");
        }
    }

    @Test
    void testCloseStream() {
        try {
            audioClipWav.loadFile();
            audioClipWav.closeStream();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            fail("Exception should not be thrown when closing a valid stream.");
        }
    }

    @Test
    void testPlaybackEvents() {
        try {
            audioClipWav.loadFile();
            audioClipWav.playAudio();
            assertTrue(audioClipWav.isPlaybackCompleted());
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            fail("Exception should not be thrown during playback event handling.");
        }
    }

    @Test
    void testPlaySegment() {
        try {
            audioClipWav.loadFile();
            audioClipWav.playAudio(1000, 3000);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            fail("Exception should not be thrown for playing a valid segment.");
        }
    }
}
