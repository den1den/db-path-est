package nl.tue.algorithm.subgraph_sf;//import static org.junit.Assert.*;

import junit.framework.TestCase;
import nl.tue.Utils;
import nl.tue.algorithm.histogram.JoinResult;
import nl.tue.algorithm.histogram.Joiner;
import nl.tue.comparison.ComparisonExecutor;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;

import java.io.IOException;

/**
 * Created by Dennis on 13-6-2016.
 */
public class SubGraphAlgorithm_SFTest extends TestCase {
    private static Parser biblio;
    private static Parser cineast;

    static {
        try {
            biblio = new Parser();
            biblio.parse(ComparisonExecutor.biblioFile);
            cineast = new Parser();
            cineast.parse(ComparisonExecutor.cineastFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Parser PARSER;
    private AdjacencyList REAL;
    private int NODES;
    private int LABELS_FORWARDS_BACKWARDS;
    private int maxPathLength;
    private long BUDGET;
    private SubGraphAlgorithm_SF algorithm;
    private SGA_SF_Builder builder;

    public SubGraphAlgorithm_SFTest() {
        PARSER = cineast;
        REAL = new AdjacencyList(PARSER);
        NODES = 61774;
        BUDGET = NODES * 2 * Integer.BYTES;
        maxPathLength = 6;
        LABELS_FORWARDS_BACKWARDS = 4 * 2;
    }

    @Override
    public void setUp() throws Exception {
        Joiner<Double, JoinResult.NumberJoinResult> basicJoiner = new Joiner.BasicJoiner();
        algorithm = new SubGraphAlgorithm_SF(basicJoiner);
        builder = new SGA_SF_Builder(algorithm, PARSER, maxPathLength, LABELS_FORWARDS_BACKWARDS, NODES);
    }

    public void testZeroCase() {
        // Query in histogram
        int[] query = new int[]{0, 0};
        assertEquals(0, REAL.solvePathQuery(query).size());
        builder.build(.5, BUDGET, 10);
        assertEquals(0, algorithm.query(query));
    }

    public void testZeroCaseNotInHistogram() {
        // Query not in histogram
        int[] query = new int[]{0, 0, 0, 0, 0};
        assertEquals(0, REAL.solvePathQuery(query).size());
        builder.build(.5, BUDGET, 10);
        assertEquals(0, algorithm.query(query));
    }

    public void testEst0Real2() {
        int[] query = new int[]{3, 3, 2};

        builder.printing = true;
        builder.build(.5, NODES * 2 * Integer.BYTES, 20);

        //Utils.writeToFile("testEst0Real2.csv", algorithm.toCSVTableProcessed(algorithm.getPathsOrdering(), REAL));
        Utils.writeToFile("testEst0Real2Full.csv", algorithm.toCSVTableFullProcessed(algorithm.getPathsOrdering(), REAL));

        assertEquals(REAL.solvePathQuery(query).size(), algorithm.query(query), 1);
    }

    public void testdeepBuildTestCSV() throws IOException {
        PARSER = biblio;
        AdjacencyList REAL = new AdjacencyList(PARSER);
        int NODES = 837;
        int LABELSFORWARDBACKWARD = 6 * 2;

        SGA_SF_Builder builder = new SGA_SF_Builder(algorithm, PARSER, maxPathLength, LABELSFORWARDBACKWARD, NODES);

        String testFileContents = SGA_SF_Builder.toCSVHeader();
        for (double timeLimit = 0.1; timeLimit <= 6.4; timeLimit *= 2) {
            for (double budgetFactor = 1; budgetFactor <= 3; budgetFactor += 0.5) {
                long budget = (long) (NODES * budgetFactor * Integer.BYTES);
                for (double sgIndex = 0; sgIndex <= 1; sgIndex += 1d / 7) {
                    double sgSize = (1 - Math.cos(sgIndex * Math.PI)) / 2;
                    builder.build(sgSize, budget, timeLimit);

                    testFileContents += System.lineSeparator() + builder.toCSV();
                }
            }
        }
        //Utils.writeToFile("deepBuildTest"+System.currentTimeMillis()+".csv", testFileContents);
    }

    public void testCurvePoints() throws Exception {
        buildEmpty();
        assertTrue(algorithm.NODES2() > 2);
        assertEquals(1, algorithm.y(-1));
        assertEquals(2, algorithm.y(-2)); // linear trend
        assertEquals(algorithm.NODES2(), algorithm.y(Short.MIN_VALUE)); // ends at N^2
    }

    public void testCurveInverse() throws Exception {
        buildEmpty();
        assertTrue(algorithm.NODES2() > 2);
        assertEquals(-1, algorithm.x(1));
        assertEquals(-2, algorithm.x(2));
        assertEquals(Short.MIN_VALUE, algorithm.x(algorithm.NODES2())); // linear trend
        assertEquals(algorithm.NODES2(), algorithm.y(Short.MIN_VALUE)); // ends at N^2
    }

    private void buildEmpty() {
        builder.build(0, 0, 0);
    }
}