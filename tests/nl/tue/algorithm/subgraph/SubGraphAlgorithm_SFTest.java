package nl.tue.algorithm.subgraph;//import static org.junit.Assert.*;

import junit.framework.TestCase;
import nl.tue.Utils;
import nl.tue.algorithm.histogram.Histogram;
import nl.tue.algorithm.histogram.JoinResult;
import nl.tue.algorithm.histogram.Joiner;
import nl.tue.comparison.ComparisonExecutor;
import nl.tue.io.Parser;

import java.io.IOException;

/**
 * Created by Dennis on 13-6-2016.
 */
public class SubGraphAlgorithm_SFTest extends TestCase {
    static Parser biblio = new Parser();
    static {
        try {
            biblio.parse(ComparisonExecutor.biblioFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void testHistogramBiblioCSV() throws IOException {
        Joiner<Double, JoinResult.NumberJoinResult> baiscJoiner = new Joiner.BasicJoiner();
        double subGraphSize = 0.5;
        SubGraphAlgorithm_SF algorithm = new SubGraphAlgorithm_SF(baiscJoiner, subGraphSize);

        long buget = 10000L;
        algorithm.buildSummary(biblio, 8, buget);

        Histogram histogram = algorithm.getHistogram();

        //new AdjacencyList(algorithm.getSubgraph().parserFromStorage()).solvePathQuery(new int[]{1})
        String toTable = histogram.toCSVTable(algorithm.getPathsOrdering());
        //Utils.writeToFile("testHistogramBiblioCSV"+System.currentTimeMillis()+".csv", toTable);
    }
}