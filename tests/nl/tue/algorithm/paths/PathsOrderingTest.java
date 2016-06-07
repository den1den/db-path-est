package nl.tue.algorithm.paths;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by dennis on 21-5-16.
 */
public class PathsOrderingTest extends TestCase {
    PathsOrdering pathsOrdering1 = new PathsOrdering(1, 15); // 0
    PathsOrdering pathsOrdering2 = new PathsOrdering(2, 15); // 0 1
    PathsOrdering pathsOrdering3 = new PathsOrdering(3, 15); // 0 1 2

    public void testMax() throws Exception {
        assertEquals(2, PathsOrdering.max(2, 1));
        assertEquals(100, PathsOrdering.max(100, 1));

        assertEquals(2 + 4, PathsOrdering.max(2, 2));
        assertEquals(2 + 4 + 8, PathsOrdering.max(2, 3));
        assertEquals(2 + 4 + 8 + 16, PathsOrdering.max(2, 4));

        assertEquals(3 + 9, PathsOrdering.max(3, 2));
        assertEquals(3 + 9 + 27, PathsOrdering.max(3, 3));
    }

    public void testGetMaxIndex() throws Exception {
        for (int L = 1; L <= 8; L++) {
            PathsOrdering sequence = new PathsOrdering(L, 10);
            for (int p = 1; p <= 10; p++) {
                sequence.getMaxIndex();
            }
        }
        for (int L = 9; L <= 21; L++) {
            PathsOrdering sequence = new PathsOrdering(L, 7);
            for (int p = 1; p <= 7; p++) {
                sequence.getMaxIndex();
            }
        }
    }

    @Test
    public void testGet() throws Exception {
        testGet(pathsOrdering2, new int[]{0, 0}, 2);
        testGet(pathsOrdering2, new int[]{1, 1}, 5);
        testGet(pathsOrdering2, new int[]{0}, 0);
        testGet(pathsOrdering2, new int[]{1}, 1);

        testGet(pathsOrdering1, new int[]{0}, 0);
        testGet(pathsOrdering1, new int[]{0, 0}, 1);
        testGet(pathsOrdering1, new int[]{0, 0, 0}, 2);

        testGet(pathsOrdering3, new int[]{0}, 0);
        testGet(pathsOrdering3, new int[]{1}, 1);
        testGet(pathsOrdering3, new int[]{2}, 2);
        testGet(pathsOrdering3, new int[]{0, 0}, 3);
        testGet(pathsOrdering3, new int[]{0, 1}, 4);
        testGet(pathsOrdering3, new int[]{0, 2}, 5);
        testGet(pathsOrdering3, new int[]{1, 0}, 6);
        testGet(pathsOrdering3, new int[]{2, 2}, 11);
        testGet(pathsOrdering3, new int[]{0, 0, 0}, 12);
        testGet(pathsOrdering3, new int[]{0, 0, 1}, 13);
        testGet(pathsOrdering3, new int[]{1, 0, 1}, 22);
    }

    private void testGet(PathsOrdering labelSequences, int[] expArr, int expIndex) {
        int[] actArr = labelSequences.get(expIndex);
        Assert.assertArrayEquals(expArr, actArr);

        int actIndex = labelSequences.get(expArr);
        Assert.assertEquals(expIndex, actIndex);
    }

    @Test
    public void testGetLength() throws Exception {
        assertEquals(pathsOrdering1.getLength(0), 1);
        assertEquals(pathsOrdering1.getLength(1), 2);
        assertEquals(pathsOrdering1.getLength(50), 51);

        assertEquals(pathsOrdering2.getLength(0), 1);
        assertEquals(pathsOrdering2.getLength(1), 1);
        assertEquals(pathsOrdering2.getLength(2), 2);

        assertEquals(pathsOrdering3.getLength(0), 1);
        assertEquals(pathsOrdering3.getLength(2), 1);
        assertEquals(pathsOrdering3.getLength(3), 2);
        assertEquals(pathsOrdering3.getLength(11), 2);
        assertEquals(pathsOrdering3.getLength(12), 3);
    }

    public void testGetFloorIndex() throws Exception {
        assertEquals(pathsOrdering2.getFloorIndex(1), 0);
        assertEquals(pathsOrdering2.getFloorIndex(2), 2);
        assertEquals(pathsOrdering2.getFloorIndex(3), 6);
    }
}