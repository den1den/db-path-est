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
    PathsOrderingLexicographical S = new PathsOrderingLexicographical(15, 4);

    @Test
    public void testAdd() throws Exception {
        PathSet ps = new PathSet(S);
        assertTrue(ps.add(new int[]{0, 1, 4, 4}));
        assertTrue(ps.add(new int[]{0, 1, 4}));
        assertTrue(!ps.add(new int[]{0, 1, 4, 4}));
        assertTrue(ps.add(new int[]{1, 1}));
        assertTrue(!ps.add(new int[]{1, 1}));
        assertTrue(!ps.add(new int[]{0, 1, 4, 4}));

        ps = new PathSet(S);
        assertTrue(ps.add(new int[]{0}));
        assertTrue(ps.add(new int[]{0, 0}));
        assertTrue(!ps.add(new int[]{0, 0}));
    }

    @Test
    public void testAdd3() throws Exception {
        PathSet ps = new PathSet(S);
        assertTrue(ps.add(new int[]{1, 1}));
        assertFalse(ps.add(new int[]{1, 1}));

        assertTrue(ps.add(new int[]{0}));
        assertFalse(ps.add(new int[]{1, 1}));
    }

    @Test
    public void testIterator() throws Exception {
        PathSet ps = new PathSet(S);
        Iterator<int[]> iterator = ps.iterator();
        assertFalse(iterator.hasNext());

        ps.add(new int[]{0, 1});

        iterator = ps.iterator();
        assertTrue(iterator.hasNext());
        assertArrayEquals(new int[]{0, 1}, iterator.next());
        assertFalse(iterator.hasNext());

        ps.add(new int[]{0});
        ps.add(new int[]{1, 1});

        iterator = ps.iterator();
        assertTrue(iterator.hasNext());
        assertArrayEquals(new int[]{0}, iterator.next());
        assertTrue(iterator.hasNext());
        assertArrayEquals(new int[]{0, 1}, iterator.next());
        assertTrue(iterator.hasNext());
        assertArrayEquals(new int[]{1, 1}, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testJoin() throws Exception {
        PathSet ps1 = new PathSet(S);
        ps1.add(new int[]{2, 2});
        PathSet ps2 = new PathSet(S);
        ps2.add(new int[]{2, 2});
        ps2.add(new int[]{8});

        ps1.join(ps2);

        assertEquals("PathSet([8], [2, 2])", ps2.toString());
    }

    @Test
    public void testIntersect() throws Exception {
        PathSet ps1 = new PathSet(S);
        ps1.add(new int[]{2, 2});
        PathSet ps2 = new PathSet(S);
        ps2.add(new int[]{2, 2});
        ps2.add(new int[]{8});

        ps1.intersect(ps2);

        assertEquals("PathSet([2, 2])", ps1.toString());
    }

    @Test
    public void testSetMinus() throws Exception {
        PathSet ps1 = new PathSet(S);
        ps1.add(new int[]{2, 2});
        PathSet ps2 = new PathSet(S);
        ps2.add(new int[]{2, 2});
        ps2.add(new int[]{5});

        ps1.setMinus(ps2);

        assertEquals("PathSet()", ps1.toString());
    }

    public void testSpeed() {
        Random r = new Random(4567865L);
        int ADDITIONS, MAX_VALUE;

        ADDITIONS = 10000000;
        MAX_VALUE = 1;
        testSpeed(r, MAX_VALUE, ADDITIONS);

        ADDITIONS = 10000000;
        MAX_VALUE = ADDITIONS / 10;
        testSpeed(r, MAX_VALUE, ADDITIONS);

        ADDITIONS = 10000000;
        MAX_VALUE = ADDITIONS / 2;
        testSpeed(r, MAX_VALUE, ADDITIONS);

        MAX_VALUE = ADDITIONS;
        testSpeed(r, MAX_VALUE, ADDITIONS);

        MAX_VALUE = ADDITIONS * 2;
        testSpeed(r, MAX_VALUE, ADDITIONS);

        MAX_VALUE = ADDITIONS * 10;
        testSpeed(r, MAX_VALUE, ADDITIONS);

        MAX_VALUE = Integer.MAX_VALUE;
        testSpeed(r, MAX_VALUE, ADDITIONS);
    }

    void testSpeed(Random r, int MAX_VALUE, int additions) {
        int[] generated = new int[additions];
        for (int g = 0; g < generated.length; g++) {
            generated[g] = r.nextInt(MAX_VALUE);
        }
        int added = 0;

        long t0 = System.currentTimeMillis();
        IntSet pathSet = new IntSet();
        for (int i = 0; i < generated.length; i++) {
            //System.out.println("Adding  " + IntSet.PathSet.toString(ls.get(generated[i]) % IntSet.PathSet.BAG_SIZE));
            if (pathSet.addInt(generated[i])) {
                added++;
            }
            //System.out.println(pathSet);
        }
        long t1 = System.currentTimeMillis();

        System.out.printf("IntSet.PathSet took %sms over %.1f%% of %s additions%n", t1 - t0, (double) added / additions * 100, additions);

        int added2 = 0;
        t0 = System.currentTimeMillis();
        HashSet<Integer> hashSet = new HashSet<>(pathSet.size());
        for (int i = 0; i < generated.length; i++) {
            if (hashSet.add(generated[i])) {
                added2++;
            }
        }
        t1 = System.currentTimeMillis();

        System.out.printf("HashSet took %sms over %.1f%% of %s additions%n", t1 - t0, 100 * (double) (added) / additions, additions);

        assertEquals(hashSet.size(), added2);
        assertEquals(hashSet.size(), added);
        System.out.println();
    }

    public void testAdd2() {
        int LABELS = 15;
        int DEPTH = 7;
        int ADDITIONS = 100000;
        Random r = new Random(456865L);

        int[][] generated = new int[ADDITIONS][];
        for (int g = 0; g < generated.length; g++) {
            generated[g] = Utils.generate(LABELS, DEPTH, r);
        }
        List<List<Integer>> generated2 = Utils.toList(generated);

        PathsOrderingLexicographical ls = new PathsOrderingLexicographical(LABELS, DEPTH);
        PathSet pathSet = new PathSet(ls);
        HashSet<List<Integer>> hashSet = new HashSet<>(ADDITIONS);

        int added = 0;
        for (int i = 0; i < generated.length; i++) {
            boolean exp = hashSet.add(generated2.get(i));
            boolean act = pathSet.add(generated[i]);
            assertEquals("Failes on " + Arrays.toString(generated[i]), exp, act);
            if (exp) {
                added++;
            }
        }
        System.out.printf("Added %s of %s paths%n", added, generated.length);
    }

    public void testAddSequential() {
        int ADDITIONS = 100000;
        Random r = new Random(4565585L);

        int[][] generated = new int[ADDITIONS][];
        for (int g = 0; g < generated.length; g++) {
            generated[g] = S.get(g);
        }
        List<List<Integer>> generated2 = Utils.toList(generated);

        PathSet pathSet = new PathSet(S);
        HashSet<List<Integer>> hashSet = new HashSet<>(ADDITIONS);

        for (int i = 0; i < generated.length; i++) {
            boolean exp = hashSet.add(generated2.get(i));
            boolean act = pathSet.add(generated[i]);
            assertTrue(act);
            assertTrue("Failes on first add of " + Arrays.toString(generated[i]), exp);
        }
        for (int i = 0; i < generated.length; i++) {
            boolean exp = hashSet.add(generated2.get(i));
            boolean act = pathSet.add(generated[i]);
            assertFalse(act);
            assertFalse("Failes on second add of " + Arrays.toString(generated[i]), exp);
        }

        Collections.shuffle(generated2, r);
        for (int i = 0; i < generated2.size(); i++) {
            List<Integer> ls = generated2.get(i);
            boolean exp = hashSet.add(ls);
            boolean act = pathSet.add(Utils.toArray(ls));
            assertFalse(act);
            assertFalse("Failes on second add of " + Arrays.toString(generated[i]), exp);
        }

        System.out.printf("Added and added again %s paths%n", generated.length);
    }

    public void testToString() throws Exception {
        PathSet ps = new PathSet(S);
        ps.add(new int[]{1, 1});
        System.out.println(ps.toString());

        ps = new PathSet(S);
        ps.add(new int[]{1, 2});
        System.out.println(ps.toString());

        ps = new PathSet(S);
        ps.add(new int[]{1, 3});
        System.out.println(ps.toString());
    }

    public void testToFullString() throws Exception {
        PathSet ps = new PathSet(S);
        ps.add(new int[]{0});
        System.out.println(ps);

        ps = new PathSet(S);
        ps.add(new int[]{1, 2});
        System.out.println(ps);

        ps = new PathSet(S);
        ps.add(new int[]{1, 3});
        System.out.println(ps);
    }

    public void testBitString() throws Exception {
        IntSet is = new IntSet();
        assertEquals("IntSet()", is.toString());
        is.add(0);
        assertEquals("IntSet(10000000000000000000000000000000)", is.toString());
        is.add(1);
        assertEquals("IntSet(11000000000000000000000000000000)", is.toString());
        is.clear();

        System.out.println(IntSet.toString(31));
        System.out.println(IntSet.toString(32));
        System.out.println(IntSet.toString(33));

        for (int i = 0; i <= 4; i++) {
            System.out.println(i + ": " + IntSet.toString(i));
        }
        for (int i = 20; i < Integer.SIZE; i++) {
            System.out.println(i + ": " + IntSet.toString(1 << i));
        }
    }
}