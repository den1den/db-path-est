package nl.tue.algorithm.pathindex;

import nl.tue.Main;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by Nathan on 5/24/2016.
 */
public class PathIndexQueryEstimatorTest {

    private static File file = new File(AdjacencyList.class.getClassLoader().getResource("biblio.txt").getFile());

    private static Parser parser;

    private static AdjacencyList verifier;

    private static IndexQueryEstimator estimator;

    @BeforeClass
    public static void loadFile() throws IOException {
        parser = new Parser();

        parser.parse(file);

        verifier = new AdjacencyList(parser);

        estimator = new IndexQueryEstimator();
        estimator.buildSummary(parser, 1, verifier.getNodes().keySet().size() * 8);
    }

    @Test
    public void testIndexedPaths() {

        /**
         * Indexes 132? paths
         */

        int[] query = Main.translateTextQueryToDomainQuery("+ 1", parser);

        Assert.assertEquals(verifier.solvePathQuery(query).size(), estimator.getEstimation(query));
    }

    @Test
    public void testNonIndexedPaths() {

        int[] query = Main.translateTextQueryToDomainQuery("+ 5 + 0 + 4 + 1 + 2", parser);

        PathSummary res = estimator.getEstimation(query);

        Assert.assertNull(res);
    }


}
