import com.example.samplesalad.model.DrumKit;
import com.example.samplesalad.model.Pad;
import com.example.samplesalad.model.Sample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

class DrumKitTest {

    private DrumKit drumKit;
    private Sample sample1;
    private Sample sample2;

    @BeforeEach
    void setUp() {
        // Initialize the DrumKit and Samples before each test
        drumKit = new DrumKit("Test Kit");

        // Create some sample objects
        sample1 = new Sample("path/to/sample.wav", "Dog", "Woof", "Bark", 0, 1);
        sample2 = new Sample("path/to/sample2.wav", "Cat", "Meow", "Bark", 0, 1);
    }

    @Test
    void testAddPad() {
        // Add a pad with sample1
        drumKit.addPad(sample1);

        // Verify that the pad was added correctly
        assertEquals(17, drumKit.getPads().size(), "The pad count should be 1");
        assertEquals(sample1, drumKit.getPad(16).getSample(), "The sample assigned to pad 0 should be sample1");
    }

    @Test
    void testGetPad_validPadNumber() {
        // Add a pad with sample1 and sample2
        drumKit.addPad(sample1);
        drumKit.addPad(sample2);

        // Verify that we can retrieve the pads
        Pad pad1 = drumKit.getPad(16);
        Pad pad2 = drumKit.getPad(17);

        assertNotNull(pad1, "Pad 1 should not be null");
        assertNotNull(pad2, "Pad 2 should not be null");
        assertEquals(sample1, pad1.getSample(), "Pad 1 should have sample1 assigned");
        assertEquals(sample2, pad2.getSample(), "Pad 2 should have sample2 assigned");
    }

    @Test
    void testGetPad_invalidPadNumber() {
        // Try retrieving a pad from an invalid index
        drumKit.addPad(sample1);

        // Expect an exception when accessing an out-of-range pad
        assertThrows(IllegalArgumentException.class, () -> drumKit.getPad(1000), "Accessing invalid pad number should throw IllegalArgumentException");
    }

    @Test
    void testLoadKit() {
        // Create a list of samples
        List<Sample> samples = new ArrayList<>();
        samples.add(sample1);
        samples.add(sample2);

        // Load the samples into the drum kit
        drumKit.loadKit(samples);

        // Verify that the samples were assigned to the pads correctly
        assertEquals(18, drumKit.getPads().size(), "The drum kit should have 2 pads after loading the kit");
        assertEquals(sample1, drumKit.getPad(16).getSample(), "Pad 0 should have sample1 assigned");
        assertEquals(sample2, drumKit.getPad(17).getSample(), "Pad 1 should have sample2 assigned");
    }

    @Test
    void testGetKitName() {
        // Verify the kit name
        assertEquals("Test Kit", drumKit.getKitName(), "The kit name should be 'Test Kit'");
    }

    @Test
    void testSetKitName() {
        // Change the kit name and verify
        drumKit.setKitName("New Kit Name");
        assertEquals("New Kit Name", drumKit.getKitName(), "The kit name should be 'New Kit Name'");
    }
}