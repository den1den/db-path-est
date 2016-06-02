package nl.tue.algorithm.paths;//import static org.junit.Assert.*;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * Created by Dennis on 31-5-2016.
 */
public class QuerySplitterTest {
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
        testQuery(5, new int[]{3, 2, 4, 1});
    }

    @Test
    public void testManualTraversal4() {
        testQuery(4, new int[]{2, 3, 1});
    }

    @Test
    public void testManualTraversal3() {
        testQuery(3, new int[]{2, 1});
    }

    @Test
    public void testManualTraversal2() {
        testQuery(2, new int[]{1});
    }

    @Test
    public void testManualTraversal1() {
        try {
            testQuery(1, null);
            testQuery(0, null);
            fail("Cannot split query of 0 or 1");
        } catch (IllegalArgumentException e){

        }
    }

    void testQuery(int length, int[] expecteds) {
        QuerySplitter splitter = new QuerySplitter(new int[length]);

        int i;
        for (i = 0; i < expecteds.length; i++) {
            assert splitter.hasNext();
            int actual = splitter.getSplitIndex();
            int expected = expecteds[i];
            System.out.println(actual);
            assertEquals("Fail on index " + i, expected, actual);

            splitter.next();
        }
        assert !splitter.hasNext();
    }

}