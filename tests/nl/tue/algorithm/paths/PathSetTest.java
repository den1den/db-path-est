package nl.tue.algorithm.paths;

import junit.framework.TestCase;
import nl.tue.Utils;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by dennis on 2-6-16.
 */
public class PathSetTest extends TestCase {
    LabelSequence s1 = new LabelSequence(1);
    LabelSequence s2 = new LabelSequence(2);
    LabelSequence s9 = new LabelSequence(9);


    @Test
    public void testAdd() throws Exception {
        PathSet ps = new PathSet(s9);
        assertTrue(ps.add(new int[]{0, 1, 4, 4}));
        assertTrue(ps.add(new int[]{0, 1, 4}));
        assertTrue(!ps.add(new int[]{0, 1, 4, 4}));
        assertTrue(ps.add(new int[]{1, 1}));
        assertTrue(!ps.add(new int[]{1, 1}));
        assertTrue(!ps.add(new int[]{0, 1, 4, 4}));

        ps = new PathSet(s2);
        assertTrue(ps.add(new int[]{0}));
        assertTrue(ps.add(new int[]{0, 0}));
        assertTrue(!ps.add(new int[]{0, 0}));
    }

    @Test
    public void testIterator() throws Exception {
        PathSet ps = new PathSet(s2);
        ps.add(new int[]{0, 1});
        ps.add(new int[]{0});
        ps.add(new int[]{1, 1});
        Iterator<int[]> iterator = ps.iterator();
        assertTrue(iterator.hasNext());
        assertArrayEquals(new int[]{0}, iterator.next());
        assertTrue(iterator.hasNext());
        assertArrayEquals(new int[]{0, 1}, iterator.next());
        assertTrue(iterator.hasNext());
        assertArrayEquals(new int[]{1, 1}, iterator.next());
        assertTrue(!iterator.hasNext());
    }

    @Test
    public void testJoin() throws Exception {
        PathSet ps1 = new PathSet(s9);
        ps1.add(new int[]{2, 2});
        PathSet ps2 = new PathSet(s9);
        ps2.add(new int[]{2, 2});
        ps2.add(new int[]{9});

        ps1.join(ps2);

        assertEquals("PathSet([0, 0], [2, 2])", ps2.toFullString());
    }

    @Test
    public void testIntersect() throws Exception {
        PathSet ps1 = new PathSet(s9);
        ps1.add(new int[]{2, 2});
        PathSet ps2 = new PathSet(s9);
        ps2.add(new int[]{2, 2});
        ps2.add(new int[]{9});

        ps1.intersect(ps2);

        assertEquals("PathSet([2, 2])", ps1.toFullString());
    }

    @Test
    public void testSetMinus() throws Exception {
        PathSet ps1 = new PathSet(s9);
        ps1.add(new int[]{2, 2});
        PathSet ps2 = new PathSet(s9);
        ps2.add(new int[]{2, 2});
        ps2.add(new int[]{9});

        ps1.setMinus(ps2);

        assertEquals("PathSet()", ps1.toFullString());
    }

    public void testSpeed() {
        int LABELS = 15;
        LabelSequence ls = new LabelSequence(LABELS);
        Random r = new Random(4567865L);

        for (int additions = 1000; additions < 10000; additions *= 10) {
            int[][] generated = new int[additions][];
            for (int g = 0; g < generated.length; g++) {
                generated[g] = generate(LABELS, 7, r);
            }
            int added = 0;

            long t0 = System.currentTimeMillis();
            PathSet pathSet = new PathSet(ls);
            for (int i = 0; i < generated.length; i++) {
                if (pathSet.add(generated[i])) {
                    added++;
                }
            }
            long t1 = System.currentTimeMillis();

            System.out.printf("PathSet took %sms over %s of %s additions%n", t1 - t0, added, additions);

            int added2 = 0;
            t0 = System.currentTimeMillis();
            HashSet<int[]> hashSet = new HashSet<>(pathSet.size());
            for (int i = 0; i < generated.length; i++) {
                if (hashSet.add(generated[i])) {
                    added2++;
                }
            }
            t1 = System.currentTimeMillis();

            System.out.printf("HashSet took %sms over %s of %s additions%n", t1 - t0, added2, additions);

            assertEquals(added2, added);
        }


    }

    public void testAdd2() {
        int LABELS = 15;
        int DEPTH = 7;
        int ADDITIONS = 100000;
        Random r = new Random(456865L);

        int[][] generated = new int[ADDITIONS][];
        for (int g = 0; g < generated.length; g++) {
            generated[g] = generate(LABELS, DEPTH, r);
        }
        List<List<Integer>> generated2 = new ArrayList<>(generated.length);
        for (int i = 0; i < generated.length; i++) {
            generated2.add(Utils.toList(generated[i]));
        }

        LabelSequence ls = new LabelSequence(LABELS);
        PathSet pathSet = new PathSet(ls);
        HashSet<List<Integer>> hashSet = new HashSet<>(ADDITIONS);

        int added = 0;
        for (int i = 0; i < generated.length; i++) {
            boolean exp = hashSet.add(generated2.get(i));
            boolean act = pathSet.add(generated[i]);
            int k = 6;
            boolean act2 = pathSet.add(generated[i]);
            assertEquals("Failes on " + Arrays.toString(generated[i]), exp, act);
            if (exp) {
                added++;
            }
        }
        System.out.printf("Added %s of %s paths%n", added, generated.length);
    }

    static private int[] generate(int LABELS, int maxSize, Random r) {
        int[] ls = new int[r.nextInt(maxSize - 1) + 1];
        for (int i = 0; i < ls.length; i++) {
            ls[i] = r.nextInt(LABELS);
        }
        return ls;
    }
}