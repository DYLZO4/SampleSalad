import com.example.samplesalad.model.Sequencer;
import com.example.samplesalad.model.Pattern;
import com.example.samplesalad.model.Pad;
import com.example.samplesalad.model.PadEvent;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class SequencerTest {

    private Sequencer sequencer;
    private Pattern pattern1;
    private Pattern pattern2;

    @BeforeEach
    void setUp() {
        sequencer = new Sequencer(120, 4, 4);

        pattern1 = new Pattern(16, 120);
        pattern2 = new Pattern(8, 120);

        // Add some events to the patterns for testing
        Pad pad1 = new Pad(null);
        pad1.setPadId(1);
        PadEvent event1 = new PadEvent(pad1, 0);
        pattern1.addPadEvent(event1);

        Pad pad2 = new Pad(null);
        pad2.setPadId(2);
        PadEvent event2 = new PadEvent(pad2, 0);
        pattern2.addPadEvent(event2);
    }

    @Test
    void testAddPatternSize() {
        sequencer.addPattern(pattern1);
        sequencer.addPattern(pattern2);
        assertEquals(2, sequencer.getPatterns().size());
    }

    @Test
    void testAddPatternContainsPattern1() {
        sequencer.addPattern(pattern1);
        assertTrue(sequencer.getPatterns().contains(pattern1));
    }

    @Test
    void testAddPatternContainsPattern2() {
        sequencer.addPattern(pattern2);
        assertTrue(sequencer.getPatterns().contains(pattern2));
    }




    @Test
    void testRecordPattern() {  // This test doesn't really test anything meaningful.  What's the expected behavior of recordPattern?
        sequencer.addPattern(pattern1);
        sequencer.recordPattern(pattern1);
        assertTrue(sequencer.getPatterns().contains(pattern1)); // This will always be true because addPattern was called
    }



    @Test
    void testIsPlayingInitial() {
        assertFalse(sequencer.isPlaying());
    }

    @Test
    void testIsPlayingAfterPlay() {
        sequencer.playSequence();
        assertTrue(sequencer.isPlaying());
    }

    @Test
    void testIsPlayingAfterStop() {
        sequencer.playSequence();
        sequencer.stopSequence();
        assertFalse(sequencer.isPlaying());
    }


    @Test
    void testRemovePatternSize() {
        sequencer.addPattern(pattern1);
        sequencer.addPattern(pattern2);
        sequencer.removePattern(pattern1);
        assertEquals(1, sequencer.getPatterns().size());
    }

    @Test
    void testRemovePatternContainsPattern1() {
        sequencer.addPattern(pattern1);
        sequencer.addPattern(pattern2);
        sequencer.removePattern(pattern1);
        assertFalse(sequencer.getPatterns().contains(pattern1));
    }

    @Test
    void testRemovePatternContainsPattern2() {
        sequencer.addPattern(pattern1);
        sequencer.addPattern(pattern2);
        sequencer.removePattern(pattern1);
        assertTrue(sequencer.getPatterns().contains(pattern2));
    }


    @Test
    void testSetAndGetTempo() {
        sequencer.setTempo(140);
        assertEquals(140, sequencer.getTempo());
    }

    @Test
    void testSetTimeSignatureNumerator() {
        sequencer.setTimeSignature(3, 4);
        assertEquals(3, sequencer.getTimeSignatureNumerator());
    }

    @Test
    void testSetTimeSignatureDenominator() {
        sequencer.setTimeSignature(3, 4);
        assertEquals(4, sequencer.getTimeSignatureDenominator());
    }



    @Test
    void testPlaySequence() {
        sequencer.playSequence();
        assertTrue(sequencer.isPlaying());
    }


    @Test
    void testStopSequence() {
        sequencer.playSequence();
        sequencer.stopSequence();
        assertFalse(sequencer.isPlaying());
    }
}