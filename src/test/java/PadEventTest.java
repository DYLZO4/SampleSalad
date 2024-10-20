import com.example.samplesalad.model.PadEvent;
import com.example.samplesalad.model.Pad;

import static org.junit.jupiter.api.Assertions.*;

import com.example.samplesalad.model.Sample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PadEventTest {

    private PadEvent padEvent;
    private Pad mockPad;
    private Sample mockSample;


    @BeforeEach
    void setUp() {
        mockSample  = new Sample("path/to/sample.wav", "Dog", "Woof", "Bark", 0, 1);
        mockPad = new Pad(mockSample);
        padEvent = new PadEvent(mockPad, 0);
    }

    @Test
    void testPadEventPadId() {
        assertEquals(mockPad.getPadId(), padEvent.getPadID());
    }

    @Test
    void testPadEventTimeStamp() {
        assertTrue(padEvent.getTimeStamp() > 0);
    }

}