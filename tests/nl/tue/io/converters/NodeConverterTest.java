package nl.tue.io.converters;//import static org.junit.Assert.*;

import junit.framework.TestCase;
import nl.tue.io.TupleList;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Dennis on 14-6-2016.
 */
public class NodeConverterTest extends TestCase{
    @Test
    public void testFilterNodes1() throws Exception {
        TupleList tl = new TupleList(Collections.singleton(new int[]{1, 10, 1}));
        test111(tl);
    }

    @Test
    public void testFilterNodes1121() throws Exception {
        TupleList tl = new TupleList(Arrays.asList(new int[]{1, 10, 1}, new int[]{2, 20, 1}));
        test111(tl);
    }

    private void test111(TupleList tl) {
        NodeConverter converter = new NodeConverter(tl);
        TupleList c2 = converter.filterNodes(1);

        assert Arrays.equals(new int[]{1, 10, 1}, c2.get(0));
    }

    @Test
    public void testFilterNodes1122() throws Exception {
        TupleList tl = new TupleList(Arrays.asList(new int[]{1, 10, 1}, new int[]{2, 20, 2}));
        NodeConverter converter = new NodeConverter(tl);
        TupleList c2 = converter.filterNodes(0.5);

        assert c2.size() == 1;
        int[] ints = c2.get(0);
        assert Arrays.equals(new int[]{1, 10, 1}, ints) ^ Arrays.equals(new int[]{2, 20, 2}, ints);
    }
}