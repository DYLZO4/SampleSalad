import com.example.samplesalad.model.Pattern;
import com.example.samplesalad.model.PadEvent;
import com.example.samplesalad.model.Pad;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class PatternTest {

    private Pattern pattern;
    private PadEvent event1;
    private PadEvent event2;

    @BeforeEach
    void setUp() {
        pattern = new Pattern(16, 120);

        Pad pad1 = new Pad(null);
        pad1.setPadId(1);
        event1 = new PadEvent(pad1, 0);

        Pad pad2 = new Pad(null);
        pad2.setPadId(2);
        event2 = new PadEvent(pad2, 0);
    }

    @Test
    void testAddPadEventSize() {
        pattern.addPadEvent(event1);
        assertEquals(1, pattern.getPadEvents().size());
    }

    @Test
    void testAddPadEventContains() {
        pattern.addPadEvent(event1);
        assertTrue(pattern.getPadEvents().contains(event1));
    }


    @Test
    void testRemovePadEventSize() {
        pattern.addPadEvent(event1);
        pattern.addPadEvent(event2);
        pattern.removePadEvent(event1);
        assertEquals(1, pattern.getPadEvents().size());
    }

    @Test
    void testRemovePadEventContainsEvent1() {
        pattern.addPadEvent(event1);
        pattern.addPadEvent(event2);
        pattern.removePadEvent(event1);
        assertFalse(pattern.getPadEvents().contains(event1));
    }

    @Test
    void testRemovePadEventContainsEvent2() {
        pattern.addPadEvent(event1);
        pattern.addPadEvent(event2);
        pattern.removePadEvent(event1);
        assertTrue(pattern.getPadEvents().contains(event2));
    }



    @Test
    void testGetPadEventsSize() {
        pattern.addPadEvent(event1);
        pattern.addPadEvent(event2);
        assertEquals(2, pattern.getPadEvents().size());
    }

    @Test
    void testGetPadEventsFirstEvent() {
        pattern.addPadEvent(event1);
        pattern.addPadEvent(event2);
        assertEquals(event1, pattern.getPadEvents().get(0));
    }

    @Test
    void testGetPadEventsSecondEvent() {
        pattern.addPadEvent(event1);
        pattern.addPadEvent(event2);
        assertEquals(event2, pattern.getPadEvents().get(1));
    }

    @Test
    void testGetLength() {
        assertEquals(16, pattern.getLength());
    }

    @Test
    void testSetLength() {
        pattern.setLength(32);
        assertEquals(32, pattern.getLength());
    }
}
