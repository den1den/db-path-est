package nl.tue.io.graph;

import nl.tue.io.Parser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Nathan on 5/21/2016.
 */
public class AdjacencyListTestOnBiblio {

    private static File file = new File(AdjacencyList.class.getClassLoader().getResource("biblio.txt").getFile());

    private static AdjacencyList list;

    @BeforeClass
    public static void loadFile() throws IOException {
        Parser p = new Parser();

        p.parse(file);

        list = new AdjacencyList(p);

    }

    @Test
    public void testFileIsLoaded() throws IOException {
        Assert.assertEquals(837, list.getNodes().keySet().size());
    }

    @Test
    public void testCitiesQuery() throws IOException {
        Set<NodePair> nodes = list.solvePathQuery(new int[]{5});

        Assert.assertEquals(366, nodes.size());
    }

    @Test
    public void testLongerPathQuery() throws IOException {
        Set<NodePair> nodes = list.solvePathQuery(new int[]{5, 0, 3});

        Assert.assertEquals(47, nodes.size());
    }
}
