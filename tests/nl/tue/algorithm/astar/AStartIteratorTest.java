package nl.tue.algorithm.astar;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by dennis on 20-5-16.
 */
public class AStartIteratorTest extends TestCase {
    AStart aStart;
    AStart.AStartIterator it;

    @Test
    public void testNext3() throws Exception {
        aStart = new AStart(3, 5);
        it = aStart.iterator();

        check(new int[]{0}, 2);
        check(new int[]{1}, 3);
        check(new int[]{2}, 0);
        // First level done
        // Heuristics ordering: 1, 0, 2

        // Deplete node 1
        check(new int[]{1, 1}, 0);
        check(new int[]{1, 0}, 0);
        check(new int[]{1, 2}, 0);

        // First get next one
        check(new int[]{0, 1}, 100);

        // Get best heuristic values, namely continue with 0,1
        check(new int[]{0, 1, 1}, 0);
        check(new int[]{0, 1, 0}, 0);
    }

    @Test
    public void testNext2() throws Exception {
        aStart = new AStart(2, 5);
        it = aStart.iterator();

        check(new int[]{0}, 5);
        check(new int[]{1}, 1);
        // First level done
        // Heuristics ordering: 0, 1

        // Depth first
        check(new int[]{0, 0}, 80);
        check(new int[]{0, 0, 0}, 100);
        check(new int[]{0, 0, 0, 0}, 60);
        // First deplete parent, h=100
        check(new int[]{0, 0, 0, 1}, 60);
        // Then deplete second best, h=80
        check(new int[]{0, 0, 1}, 0);
        // Then deplete h=60.
        // On tie, take the best heuristic ordering of most significant (last)
        check(new int[]{0, 0, 0, 0, 0}, 0);
        check(new int[]{0, 0, 0, 0, 1}, 0);
        // On tie, take the second best
        check(new int[]{0, 0, 0, 1, 0}, 0);
        check(new int[]{0, 0, 0, 1, 1}, 0);
        // On tie, continue with smallest getEstimation
        check(new int[]{1, 0}, 0);
        check(new int[]{1, 1}, 0);
    }

    @Test
    public void testDuplicates() {
        aStart = new AStart(5, 5);
        it = aStart.iterator();
        Set<int[]> rs = new HashSet<>();
        for (int i = 0; i < 1024 * 250; i++) {
            int[] r = it.next();
            aStart.setHeuristic(5);
            assert rs.add(r);
        }
    }

    @Test
    public void testSpeed() {
        final int N = 1024 * 250;
        long t0 = System.currentTimeMillis();
        for (int LABELS = 2; LABELS < 20; LABELS++) {
            aStart = new AStart(LABELS, 10000);
            it = aStart.iterator();
            for (int i = 0; i < N; i++) {
                int[] r = it.next();
                aStart.setHeuristic(0);
            }
            long t1 = System.currentTimeMillis();
            System.out.println(String.format("With %s labels, %s iterations took %sms", LABELS, N, t1 - t0));
            t0 = t1;
        }
    }

    private void check(int[] ints, int heuristic) {
        int[] result = it.next();

        // Iterate
        int iteration = 0;
        for (Iterator<AStart.AStartIterator.Node> iterator = it.queue.iterator(); iterator.hasNext(); ) {
            iteration++;
            AStart.AStartIterator.Node n = iterator.next();
            System.out.printf("iteration %s:  %s - %s%n", iteration, n.toString(), n.heuristic);
        }
        System.out.println();

        if (!Arrays.equals(ints, result)) {
            System.err.println(" real: " + Arrays.toString(result));
            System.err.println(" expt: " + Arrays.toString(ints));
        }
        Assert.assertArrayEquals(ints, result);
        aStart.setHeuristic(heuristic);
    }
}
