package nl.tue.algorithm.pathindex;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Nathan on 5/24/2016.
 */
public class PathIndexTest {

    @Test
    public void intArrayToPathTestPathLengthOne() {
        int[] path = new int[] {1};

        PathIndex index = new PathIndex(path);

        Assert.assertEquals("1", index.getPath());
    }

    @Test
    public void intArrayToPathTestPathLengthTwo() {
        int[] path = new int[] {1, 3};

        PathIndex index = new PathIndex(path);

        Assert.assertEquals("1/3", index.getPath());
    }

    @Test
    public void intArrayToPathTestPathLengthThree() {
        int[] path = new int[] {1, 3, 6};

        PathIndex index = new PathIndex(path);

        Assert.assertEquals("1/3/6", index.getPath());
    }

    @Test
    public void intArrayTopathTestBackPath() {
        int[] path = new int[] {1, -2, 4};

        PathIndex index = new PathIndex(path);

        Assert.assertEquals("1/-2/4", index.getPath());
    }
}
