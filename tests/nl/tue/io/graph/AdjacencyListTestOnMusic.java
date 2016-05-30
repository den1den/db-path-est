package nl.tue.io.graph;

import nl.tue.Main;
import nl.tue.io.Parser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Nathan on 5/29/2016.
 */
public class AdjacencyListTestOnMusic {
    private static File file = new File(AdjacencyList.class.getClassLoader().getResource("generatedmusicdata.txt").getFile());

    private static AdjacencyList list;

    private static Parser parser;

    @BeforeClass
    public static void loadFile() throws IOException {
        parser = new Parser();

        parser.parse(file);

        list = new AdjacencyList(parser);

    }

    @Test
    public void testFileIsLoaded() throws IOException {
        Assert.assertEquals(113754, list.getNodes().keySet().size());
    }

    @Test
    public void testShortQuery() throws IOException {
        Set<NodePair> nodes = list.solvePathQuery(Main.translateTextQueryToDomainQuery("+ 3", parser));

        Assert.assertEquals(105000, nodes.size());
    }

    @Test
    public void testLongerPathQuery() throws IOException {
        Set<NodePair> nodes = list.solvePathQuery(Main.translateTextQueryToDomainQuery("+ 5 - 5 + 2", parser));

        Assert.assertEquals(461629, nodes.size());
    }
}
