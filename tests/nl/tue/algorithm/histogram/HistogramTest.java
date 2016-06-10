package nl.tue.algorithm.histogram;//import static org.junit.Assert.*;

import junit.framework.TestCase;
import nl.tue.Utils;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dennis on 4-6-2016.
 */
public class HistogramTest extends TestCase {
    public void testBinarySearch() throws Exception {

        List<Integer> l = Utils.toList(new int[]{0, 5, 10});

        assertEquals(-1, Collections.binarySearch(l, -1));
        assertEquals(0, Collections.binarySearch(l, 0));
        assertEquals(-2, Collections.binarySearch(l, 3));
        assertEquals(1, Collections.binarySearch(l, 5));
        assertEquals(-3, Collections.binarySearch(l, 7));
        assertEquals(2, Collections.binarySearch(l, 10));
        assertEquals(-4, Collections.binarySearch(l, 11));

    }
}