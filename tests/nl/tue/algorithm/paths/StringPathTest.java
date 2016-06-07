package nl.tue.algorithm.paths;

import junit.framework.TestCase;
import nl.tue.algorithm.paths.StringPath;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by dennis on 2-6-16.
 */
public class StringPathTest extends TestCase {
    @Test
    public void testPath() throws Exception {
        testPath(new int[0]);
        testPath(new int[]{0});
        testPath(new int[]{1, 0});
        testPath(new int[]{Short.MAX_VALUE, Short.MAX_VALUE});
    }

    public void testPath(int[] path) {
        StringPath p = new StringPath(path);
        assertArrayEquals(path, p.getPath());
    }
}