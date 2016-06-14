package nl.tue.algorithm.subgraph_sf;//import static org.junit.Assert.*;

import junit.framework.TestCase;
import nl.tue.Utils;
import nl.tue.algorithm.histogram.HistogramOfShorts;
import nl.tue.algorithm.histogram.JoinResult;
import nl.tue.algorithm.histogram.Joiner;
import nl.tue.comparison.ComparisonExecutor;
import nl.tue.io.Parser;
import nl.tue.io.TupleList;
import nl.tue.io.graph.AdjacencyList;

import java.io.IOException;

/**
 * Created by Dennis on 13-6-2016.
 */
public class SubGraphAlgorithm_SFTest extends TestCase {
    static Parser biblio = new Parser();
    static Parser cineast = new Parser();
    static {
        try {
            biblio.parse(ComparisonExecutor.biblioFile);
            cineast.parse(ComparisonExecutor.cineastFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ERROR Found
     */
    public void testEst0Real2(){
        int[] query = new int[]{3, 3, 2};
        Parser p = cineast;
        int NODES = 61774;
        int LABELSFORWARDBACKWARD = 4 * 2;

        SGA_SF_Builder builder = new SGA_SF_Builder(algorithm, p, maxPathLength - 2, LABELSFORWARDBACKWARD, NODES);
        builder.build(.5, NODES * 2*Integer.BYTES, 10);

        System.out.println("Factors:");
        System.out.print(algorithm.getHistogram().toCSVTable(algorithm.getPathsOrdering()));

        int result = algorithm.query(query);
        System.out.println("Estm: "+result);
        System.out.println("Real: "+new AdjacencyList(p).solvePathQuery(query).size());
    }

    Joiner<Double, JoinResult.NumberJoinResult> baiscJoiner = new Joiner.BasicJoiner();
    SubGraphAlgorithm_SF algorithm = new SubGraphAlgorithm_SF(baiscJoiner);
    int maxPathLength = 8;

    public void testdeepBuildTestCSV() throws IOException {
        Parser p = biblio;
        int NODES = 837;
        int LABELSFORWARDBACKWARD = 6 * 2;

        SGA_SF_Builder builder = new SGA_SF_Builder(algorithm, p, maxPathLength, LABELSFORWARDBACKWARD, NODES);

        String testFileContents = SGA_SF_Builder.toCSVHeader();
        for (double timeLimit = 0.1; timeLimit <= 6.4; timeLimit *= 2){
            for (double budgetFactor = 1; budgetFactor <= 3; budgetFactor += 0.5) {
                long budget = (long) (NODES * budgetFactor * Integer.BYTES);
                for (double sgIndex = 0; sgIndex <= 1; sgIndex += 1d / 7){
                    double sgSize = (1 - Math.cos(sgIndex * Math.PI)) / 2;
                    builder.build(sgSize, budget, timeLimit);

                    testFileContents += System.lineSeparator() + builder.toCSV();
                }
            }
        }
        //Utils.writeToFile("deepBuildTest"+System.currentTimeMillis()+".csv", testFileContents);
    }
}