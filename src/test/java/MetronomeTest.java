import com.example.samplesalad.model.Metronome;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Metronome} class.
 */
class MetronomeTest {

    private Metronome metronome;

    /**
     * Sets up a new instance of {@link Metronome} before each test.
     */
    @BeforeEach
    void setUp() {
        metronome = new Metronome();
    }

    /**
     * Tests the {@link Metronome#startMetronome(int)} method.
     * Verifies that the metronome starts and is playing after calling the startMetronome method.
     */
    @Test
    void testStartMetronome() {
        metronome.startMetronome(120);
        assertTrue(metronome.isPlaying());
    }

    /**
     * Tests that the metronome is not playing initially.
     */
    @Test
    void testInitialPlayingState() {
        assertFalse(metronome.isPlaying());
    }


    /**
     * Tests the {@link Metronome#stop()} method.
     * Verifies that the metronome stops playing after calling the stop method.
     */
    @Test
    void testStopMetronome() {
        metronome.startMetronome(120);
        metronome.stop();
        assertFalse(metronome.isPlaying());
    }

    /**
     * Tests the {@link Metronome#setBpm(int)} method.
     */
    @Test
    void testSetBpm() {
        int bpm = 100;
        metronome.setBpm(bpm);
        assertEquals(bpm, metronome.getBpm());
    }



    /**
     * Tests the {@link Metronome#togglePlay()} method starts the metronome.
     */
    @Test
    void testTogglePlayStart() {
        metronome.togglePlay();
        assertTrue(metronome.isPlaying());
    }

    /**
     * Tests the {@link Metronome#togglePlay()} method stops the metronome.
     */
    @Test
    void testTogglePlayStop() {
        metronome.togglePlay(); // Start
        metronome.togglePlay(); // Stop
        assertFalse(metronome.isPlaying());
    }



    /**
     * Tests the {@link Metronome#reset()} resets the playing state.
     */
    @Test
    void testResetPlayingState() {
        metronome.startMetronome(120);
        metronome.reset();
        assertFalse(metronome.isPlaying());
    }

    /**
     * Tests the {@link Metronome#reset()} resets the BPM.
     */
    @Test
    void testResetBpm() {
        metronome.setBpm(150);
        metronome.reset();
        assertEquals(120, metronome.getBpm());
    }
}
