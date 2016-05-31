package nl.tue.algorithm;

import junit.framework.TestCase;
import nl.tue.algorithm.pathindex.IndexQueryEstimator;
import org.junit.Test;

/**
 * Created by dennis on 25-5-16.
 */
public class Algorithm_1Test extends TestCase {

    Algorithm_1 algorithm;

    @Override
    public void setUp() throws Exception {
        IndexQueryEstimator estimator = new IndexQueryEstimator();
        algorithm = new Algorithm_1<>(estimator);
    }

    @Test
    public void testManualTraversal7() {
        // 4: **** ***
        // 3: *** ****
        // 5: ***** **
        // 2: ** *****
        // 6: ****** *
        // 1: * ******
        testQuery(7, new int[]{4, 3, 5, 2, 6, 1});
    }

    @Test
    public void testManualTraversal6() {
        // 3: *** ***
        // 4: **** **
        // 2: ** ****
        // 5: ***** *
        // 1: * *****
        testQuery(6, new int[]{3, 4, 2, 5, 1});
    }

    @Test
    public void testManualTraversal5() {
        // *3*1*2*4*
        testQuery(5, new int[]{3, 2, 4, 1});
    }

    @Test
    public void testManualTraversal4() {
        // *3*1*2*
        testQuery(4, new int[]{2, 3, 1});
    }

    @Test
    public void testManualTraversal3() {
        // *1*2*
        testQuery(3, new int[]{2, 1});
    }


    @Test
    public void testManualTraversal2() {
        testQuery(2, new int[]{1});
    }

    @Test
    public void testManualTraversal1() {
        testQuery(1, new int[]{});
    }

    void testQuery(int length, int[] expecteds) {
        Algorithm_1.DynamicProgram d = algorithm.new DynamicProgram(new int[length]);

        int i;
        for (i = 0; i < expecteds.length; i++) {
            assert d.canSplit(i);
            int actual = d.getSplitIndex(i);
            int expected = expecteds[i];
            System.out.println(actual);
            assertEquals("Fail on index " + i, expected, actual);
        }
        assert !d.canSplit(i);
    }
}