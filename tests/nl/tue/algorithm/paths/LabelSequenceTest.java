package nl.tue.algorithm.paths;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by dennis on 21-5-16.
 */
public class LabelSequenceTest extends TestCase {
    public void testMax() throws Exception {
        assertEquals(2, LabelSequence.max(2, 1));
        assertEquals(100, LabelSequence.max(100, 1));

        assertEquals(2+4, LabelSequence.max(2, 2));
        assertEquals(2+4+8, LabelSequence.max(2, 3));
        assertEquals(2+4+8+16, LabelSequence.max(2, 4));

        assertEquals(3+9, LabelSequence.max(3, 2));
        assertEquals(3+9+27, LabelSequence.max(3, 3));
    }

    public void testGetMaxIndex() throws Exception {
        for (int L = 1; L <= 8; L++) {
            LabelSequence sequence = new LabelSequence(L, 10);
            for (int p = 1; p <= 10; p++) {
                sequence.getMaxIndex();
            }
        }
        for (int L = 9; L <= 21; L++) {
            LabelSequence sequence = new LabelSequence(L, 7);
            for (int p = 1; p <= 7; p++) {
                sequence.getMaxIndex();
            }
        }
    }

    LabelSequence labelSequence1 = new LabelSequence(1, 15); // 0
    LabelSequence labelSequence2 = new LabelSequence(2, 15); // 0 1
    LabelSequence labelSequence3 = new LabelSequence(3, 15); // 0 1 2

    @Test
    public void testGet() throws Exception {
        testGet(labelSequence2, new int[]{0, 0}, 2);
        testGet(labelSequence2, new int[]{1, 1}, 5);
        testGet(labelSequence2, new int[]{0}, 0);
        testGet(labelSequence2, new int[]{1}, 1);

        testGet(labelSequence1, new int[]{0}, 0);
        testGet(labelSequence1, new int[]{0, 0}, 1);
        testGet(labelSequence1, new int[]{0, 0, 0}, 2);

        testGet(labelSequence3, new int[]{0}, 0);
        testGet(labelSequence3, new int[]{1}, 1);
        testGet(labelSequence3, new int[]{2}, 2);
        testGet(labelSequence3, new int[]{0, 0}, 3);
        testGet(labelSequence3, new int[]{0, 1}, 4);
        testGet(labelSequence3, new int[]{0, 2}, 5);
        testGet(labelSequence3, new int[]{1, 0}, 6);
        testGet(labelSequence3, new int[]{2, 2}, 11);
        testGet(labelSequence3, new int[]{0, 0, 0}, 12);
        testGet(labelSequence3, new int[]{0, 0, 1}, 13);
        testGet(labelSequence3, new int[]{1, 0, 1}, 22);
    }

    private void testGet(LabelSequence labelSequences, int[] expArr, int expIndex) {
        int[] actArr = labelSequences.get(expIndex);
        Assert.assertArrayEquals(expArr, actArr);

        int actIndex = labelSequences.get(expArr);
        Assert.assertEquals(expIndex, actIndex);
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