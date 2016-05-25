package nl.tue.algorithm;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by dennis on 25-5-16.
 */
public class Algorithm_1Test extends TestCase {
    @Test
    public void testManualTraversal20() {
        testQuery(20);
        fail("Test manual");
    }

    @Test
    public void testManualTraversal21() {
        testQuery(21);
    }

    void testQuery(final int length) {
        int tailIndex = length / 2 + length % 2;
        int offset = 0;
        boolean positive = true;
        do {
            if (positive) {
                tailIndex += offset++;
            } else {
                tailIndex -= offset++;
            }
            positive = !positive;

            System.out.printf("(0, %d) - (%d, %d)%n", tailIndex - 1, tailIndex, length);


        } while (offset < length);
    }
}