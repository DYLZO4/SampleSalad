import com.example.samplesalad.model.Pad;
import com.example.samplesalad.model.Sample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PadTest {

    private Pad pad;
    private Sample mockSample;

    @BeforeEach
    void setUp() {
        mockSample = new Sample(1, "filepath");
        pad = new Pad(mockSample);
    }

    @Test
    void testSetAndGetSample() {
        Sample newSample = new Sample(2, "new/filepath");
        pad.setSample(newSample);
        assertEquals(newSample, pad.getSample());
    }

    @Test
    void testTriggerPad() {
        pad.setPadId(1);
        pad.triggerPad();
        // Check output in the console or create a real trigger method to assert changes
    }

    @Test
    void testMutePad() {
        pad.setPadId(1);
        pad.mutePad();
        // Check if pad is muted through any changes in the object or console output
    }

    @Test
    void testUnmutePad() {
        pad.setPadId(1);
        pad.unmutePad();
        // Verify the object state after unmuting or check console output
    }

    @Test
    void testSetVolume() {
        pad.setVolume(0.5);
    }

    @Test
    void testSetKeybind() {
        String keybind = "A";
        pad.setKeybind(keybind);
        assertEquals(keybind, pad.getKeybind());
    }

    @Test
    void testIsPlaying() {
        assertFalse(pad.isPlaying());
    }

    @Test
    void testGetAndSetPadId() {
        pad.setPadId(2);
        assertEquals(2, pad.getPadId());
    }
}
