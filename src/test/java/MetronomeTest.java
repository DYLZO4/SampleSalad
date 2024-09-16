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
     * Tests the {@link Metronome#start()} method.
     * Verifies that the metronome starts and is playing after calling the start method.
     */
    @Test
    void testStartMetronome() {
        // Arrange - metronome should not be playing by default
        assertFalse(metronome.isPlaying(), "Metronome should not be playing initially");

        // Act - start the metronome
        metronome.start();

        // Assert - check if the metronome is playing
        assertTrue(metronome.isPlaying(), "Metronome should be playing after start");
    }

    /**
     * Tests the {@link Metronome#stop()} method.
     * Verifies that the metronome stops playing after calling the stop method.
     */
    @Test
    void testStopMetronome() {
        // Arrange - start the metronome
        metronome.start();
        assertTrue(metronome.isPlaying(), "Metronome should be playing after start");

        // Act - stop the metronome
        metronome.stop();

        // Assert - check if the metronome is not playing
        assertFalse(metronome.isPlaying(), "Metronome should not be playing after stop");
    }

    /**
     * Tests the {@link Metronome#setBpm(int)} and {@link Metronome#getBpm()} methods.
     * Verifies that the BPM is correctly set and retrieved.
     */
    @Test
    void testSetAndGetBpm() {
        // Arrange
        int bpm = 100;

        // Act - set the BPM
        metronome.setBpm(bpm);

        // Assert - check if the BPM is correctly set
        assertEquals(bpm, metronome.getBpm(), "The BPM should match the set value");
    }

    /**
     * Tests the {@link Metronome#togglePlay()} method.
     * Verifies that toggling the play state starts and stops the metronome correctly.
     */
    @Test
    void testTogglePlay() {
        // Arrange - initially not playing
        assertFalse(metronome.isPlaying(), "Metronome should not be playing initially");

        // Act - toggle once to start the metronome
        metronome.togglePlay();

        // Assert - check if the metronome is playing
        assertTrue(metronome.isPlaying(), "Metronome should be playing after toggle");

        // Act - toggle again to stop the metronome
        metronome.togglePlay();

        // Assert - check if the metronome is not playing
        assertFalse(metronome.isPlaying(), "Metronome should not be playing after second toggle");
    }

    /**
     * Tests the {@link Metronome#reset()} method.
     * Verifies that the metronome resets to the default BPM and stops playing.
     */
    @Test
    void testReset() {
        // Arrange - set BPM and start the metronome
        metronome.setBpm(150);
        metronome.start();
        assertTrue(metronome.isPlaying(), "Metronome should be playing after start");
        assertEquals(150, metronome.getBpm(), "The BPM should be set to 150");

        // Act - reset the metronome
        metronome.reset();

        // Assert - check if the metronome is reset to default BPM and stopped
        assertFalse(metronome.isPlaying(), "Metronome should not be playing after reset");
        assertEquals(120, metronome.getBpm(), "BPM should reset to default value 120");
    }
}
