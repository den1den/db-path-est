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

        List<Long> query = new ArrayList<>();

        query.add(1l);

        Assert.assertEquals(verifier.solvePathQuery(new int[]{1}).size(), estimator.query(query));
    }

    @Test
    public void testNonIndexedPaths() {

        List<Long> query = new ArrayList<>();

        query.add(5l);
        query.add(0l);
        query.add(4l);
        query.add(1l);
        query.add(2l);

        int res = estimator.query(query);

        System.out.println(String.format("Estimated %s actual %s", res, 71));
    }

    @Test
    public void testNonIndexedPaths_2() {

        List<Long> query = new ArrayList<>();

        query.add(5l);
        query.add(0l);
        query.add(4l);
        query.add(3l);

        int res = estimator.query(query);

        System.out.println(String.format("Estimated %s actual %s", res, 49));
    }

    @Test
    public void testNonIndexedPaths_3() {

        List<Long> query = new ArrayList<>();

        query.add(5l);
        query.add(0l);
        query.add(1l);
        query.add(2l);

        int res = estimator.query(query);

        //THE OLD DATA IS DEAD, LONG LIVE THE NEW DATA
        System.out.println(String.format("Estimated %s actual %s", res, 51));
    }
}
