package nl.tue.algorithm.pathindex;

import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathan on 5/24/2016.
 */
public class PathIndexQueryEstimatorTest {

    private static File file = new File(AdjacencyList.class.getClassLoader().getResource("biblio.txt").getFile());

    private static Parser parser;

    private static AdjacencyList verifier;



    @BeforeClass
    public static void loadFile() throws IOException {
        parser = new Parser();

        parser.parse(file);

        verifier = new AdjacencyList(parser);
    }

    @Test
    public void testIndexedPaths() {
        IndexQueryEstimator estimator = new IndexQueryEstimator();

        /**
         * Indexes 10? paths
         */
        estimator.buildSummary(parser, 1, 32 + 8*10);

        List<Long> query = new ArrayList<>();

        query.add(1l);

        Assert.assertEquals(verifier.solvePathQuery(new int[]{1}).size(), estimator.query(query));
    }
}
