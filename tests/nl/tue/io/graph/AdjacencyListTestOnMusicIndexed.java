package nl.tue.io.graph;

import nl.tue.io.Parser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Nathan on 6/1/2016.
 */
public class AdjacencyListTestOnMusicIndexed {
    private static File file = new File(AdjacencyList.class.getClassLoader().getResource("generatedmusicdata.txt").getFile());

    private static AdjacencyList list;

    private static Parser parser;

    @BeforeClass
    public static void loadFile() throws IOException {
        parser = new Parser();

        parser.parse(file);

        list = new AdjacencyList(parser);

        list.solvePathQuery(new int[]{5, 10});
        list.solvePathQuery(new int[]{1, 7});

    }

    @Test
    public void testLongTimePathQuery_3() throws IOException {
        Collection<NodePair> nodes = list.solvePathQuery(new int[]{5, 10, 1, 7});

        Assert.assertEquals(45006900, nodes.size());
    }
}
