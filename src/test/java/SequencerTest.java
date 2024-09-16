import com.example.samplesalad.model.Sequencer;
import com.example.samplesalad.model.Pattern;
import com.example.samplesalad.model.Pad;
import com.example.samplesalad.model.PadEvent;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class SequencerTest {

    private Sequencer sequencer;
    private Pattern pattern1;
    private Pattern pattern2;

    @BeforeEach
    void setUp() {
        sequencer = new Sequencer(120, 4, 4);

        pattern1 = new Pattern(16);
        pattern2 = new Pattern(8);

        // Add some events to the patterns for testing
        Pad pad1 = new Pad(null);
        pad1.setPadId(1);
        PadEvent event1 = new PadEvent(pad1);
        pattern1.addPadEvent(event1);

        Pad pad2 = new Pad(null);
        pad2.setPadId(2);
        PadEvent event2 = new PadEvent(pad2);
        pattern2.addPadEvent(event2);
    }

    @Test
    void testAddPattern() {
        sequencer.addPattern(pattern1);
        sequencer.addPattern(pattern2);

        List<Pattern> patterns = sequencer.getPatterns();
        assertEquals(2, patterns.size(), "There should be 2 patterns in the sequence.");
        assertTrue(patterns.contains(pattern1), "Pattern1 should be in the sequence.");
        assertTrue(patterns.contains(pattern2), "Pattern2 should be in the sequence.");
    }

    @Test
    void testRecordPattern() {
        sequencer.addPattern(pattern1);
        sequencer.recordPattern(pattern1);
        List<Pattern> patterns = sequencer.getPatterns();

        assertTrue(patterns.contains(pattern1), "Pattern1 should be recorded in the sequence.");
    }

    @Test
    void testIsPlaying() {
        sequencer.addPattern(pattern1);
        // Initially, the sequence should not be playing
        assertFalse(sequencer.isPlaying(), "The sequence should not be playing initially.");

        sequencer.playSequence();
        assertTrue(sequencer.isPlaying(), "The sequence should be playing after calling playSequence().");

        sequencer.stopSequence();
        assertFalse(sequencer.isPlaying(), "The sequence should not be playing after calling stopSequence().");
    }


    @Test
    void testRemovePattern() {
        sequencer.addPattern(pattern1);
        sequencer.addPattern(pattern2);
        sequencer.removePattern(pattern1);

        List<Pattern> patterns = sequencer.getPatterns();
        assertEquals(1, patterns.size(), "There should be 1 pattern left after removal.");
        assertFalse(patterns.contains(pattern1), "Pattern1 should have been removed.");
        assertTrue(patterns.contains(pattern2), "Pattern2 should still be in the sequence.");
    }

    @Test
    void testSetAndGetTempo() {
        sequencer.setTempo(140);
        assertEquals(140, sequencer.getTempo(), "The tempo should be set to 140 bpm.");
    }

    @Test
    void testSetAndGetTimeSignature() {
        sequencer.setTimeSignature(3, 4);
        assertEquals(3, sequencer.getTimeSignatureNumerator(), "The numerator should be set to 3.");
        assertEquals(4, sequencer.getTimeSignatureDenominator(), "The denominator should be set to 4.");
    }

    @Test
    void testPlaySequence() {
        sequencer.addPattern(pattern1);
        sequencer.addPattern(pattern2);

        // Test that playing starts
        sequencer.playSequence();
        assertTrue(sequencer.isPlaying(), "The sequencer should be playing.");
    }

    @Test
    void testStopSequence() {
        sequencer.addPattern(pattern1);
        sequencer.playSequence();

        // Test that stopping works
        sequencer.stopSequence();
        assertFalse(sequencer.isPlaying(), "The sequencer should have stopped playing.");
    }


}
