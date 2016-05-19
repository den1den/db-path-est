package nl.tue.algorithm;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by dennis on 20-5-16.
 */
public class AStartIteratorTest extends TestCase {

    AStart instance;

    AStart.HeuristicEvaluator hi = new AStart.HeuristicEvaluator();

    @Override
    public void setUp() throws Exception {
        instance = new AStart(new int[]{1, 3, 5}, 5, hi);
    }

    @Test
    public void testNext() throws Exception {
        int[] next;
        AStart.AStartIterator it = instance.iterator();

        Assert.assertEquals("level: 0", it.getLevel(), 0);
        next = it.next();
        Assert.assertArrayEquals("next: 0, 1", new int[]{1}, next);
        hi.setHeuristic(2);
        next = it.next();
        Assert.assertArrayEquals("next: 0, 2", new int[]{3}, next);
        hi.setHeuristic(3);
        next = it.next();
        Assert.assertArrayEquals("next: 0, 3", new int[]{5}, next);
        hi.setHeuristic(0);
        next = it.next();
        // First part done

        Assert.assertEquals("level: 1", it.getLevel(), 1);
        Assert.assertArrayEquals("next: 1, 1", new int[]{3, 3}, next);
        hi.setHeuristic(0);

        next = it.next();
        Assert.assertArrayEquals("next: 1, 1", new int[]{3, 1}, next);
        hi.setHeuristic(0);

        next = it.next();
        Assert.assertArrayEquals("next: 1, 1", new int[]{3, 5}, next);
        hi.setHeuristic(0);

        // Next Level
        Assert.assertArrayEquals("next: 1, 1", new int[]{3, 3}, next);
        hi.setHeuristic(0);

        next = it.next();
        Assert.assertArrayEquals("next: 1, 1", new int[]{3, 1}, next);
        hi.setHeuristic(0);

        next = it.next();
        Assert.assertArrayEquals("next: 1, 1", new int[]{3, 5}, next);
        hi.setHeuristic(0);
    }
}