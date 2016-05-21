package nl.tue.algorithm;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by dennis on 21-5-16.
 */
public class LabelSequenceTest extends TestCase {
    LabelSequence labelSequence1 = new LabelSequence(1); // 0
    LabelSequence labelSequence2 = new LabelSequence(2); // 0 1
    LabelSequence labelSequence3 = new LabelSequence(3); // 0 1 2

    @Test
    public void testGet() throws Exception {
        assertArrayEquals(new int[]{0}, labelSequence1.get(0));
        assertArrayEquals(new int[]{0, 0}, labelSequence1.get(1));
        assertArrayEquals(new int[]{0, 0, 0}, labelSequence1.get(2));

        assertArrayEquals(new int[]{0}, labelSequence2.get(0));
        assertArrayEquals(new int[]{1}, labelSequence2.get(1));
        assertArrayEquals(new int[]{0, 0}, labelSequence2.get(2));
        assertArrayEquals(new int[]{1, 1}, labelSequence2.get(5));

        assertArrayEquals(new int[]{0}, labelSequence3.get(0));
        assertArrayEquals(new int[]{1}, labelSequence3.get(1));
        assertArrayEquals(new int[]{2}, labelSequence3.get(2));
        assertArrayEquals(new int[]{0, 0}, labelSequence3.get(3));
        assertArrayEquals(new int[]{0, 1}, labelSequence3.get(4));
        assertArrayEquals(new int[]{0, 2}, labelSequence3.get(5));
        assertArrayEquals(new int[]{1, 0}, labelSequence3.get(6));
        assertArrayEquals(new int[]{2, 2}, labelSequence3.get(11));
        assertArrayEquals(new int[]{0, 0, 0}, labelSequence3.get(12));
        assertArrayEquals(new int[]{0, 0, 1}, labelSequence3.get(13));
        assertArrayEquals(new int[]{1, 0, 1}, labelSequence3.get(22));
    }

    @Test
    public void testGetLength() throws Exception {
        assertEquals(labelSequence1.getLength(0), 1);
        assertEquals(labelSequence1.getLength(1), 2);
        assertEquals(labelSequence1.getLength(50), 51);

        assertEquals(labelSequence2.getLength(0), 1);
        assertEquals(labelSequence2.getLength(1), 1);
        assertEquals(labelSequence2.getLength(2), 2);

        assertEquals(labelSequence3.getLength(0), 1);
        assertEquals(labelSequence3.getLength(2), 1);
        assertEquals(labelSequence3.getLength(3), 2);
        assertEquals(labelSequence3.getLength(11), 2);
        assertEquals(labelSequence3.getLength(12), 3);
    }

    public void testGetFloorIndex() throws Exception {
        assertEquals(labelSequence2.getFloorIndex(1), 0);
        assertEquals(labelSequence2.getFloorIndex(2), 2);
        assertEquals(labelSequence2.getFloorIndex(3), 6);
    }
}