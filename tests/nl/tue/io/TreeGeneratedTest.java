package nl.tue.io;//import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;

/**
 * Created by Dennis on 10-6-2016.
 */
public class TreeGeneratedTest {
    @Test
    public void testCretion() throws Exception {
        TreeGenerated t = new TreeGenerated(2, 4, 4, 2, new double[]{0.9, 0.05, 0.05});
        List<int[]> tuples = t.construct();
        int[] ints = tuples.get(tuples.size() - 1);
        CSVWriter.write("test.csv", tuples);
    }
}