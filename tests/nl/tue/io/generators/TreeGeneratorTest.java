package nl.tue.io.generators;//import static org.junit.Assert.*;

import nl.tue.io.TupleList;
import org.junit.Test;

/**
 * Created by Dennis on 10-6-2016.
 */
public class TreeGeneratorTest {
    @Test
    public void testCretion() throws Exception {
        TreeGenerator t = new TreeGenerator(2, 4, 4, 2, new double[]{0.9, 0.05, 0.05});
        TupleList tuples = t.construct();
        tuples.writeToCSVFile("test.csv");
    }
}