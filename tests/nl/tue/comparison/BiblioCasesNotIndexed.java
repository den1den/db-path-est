package nl.tue.comparison;

import nl.tue.Main;
import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.NaiveJoinAlgorithm;
import nl.tue.algorithm.pathindex.IndexQueryEstimator;
import nl.tue.algorithm.pathindex.PathSummary;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nathan on 5/25/2016.
 */
public class BiblioCasesNotIndexed {

    private static File biblioFile = new File(AdjacencyList.class.getClassLoader().getResource("biblio.txt").getFile());

    private static Parser parser;

    private static Algorithm<PathSummary, IndexQueryEstimator> algo;

    /**
     * These paths will not be indexed if byte allocation is set to nodes * 2.
     */
    private static List<String> paths = Arrays.asList("+ 5 + 0 + 4 + 1 + 2", "+ 5 + 0 + 4 + 3",
            "+ 5 + 0 + 1 + 2", "+ 0 + 4 + 1 + 2", "+ 5 + 0 - 4 + 1 + 2", "- 5 + 0 + 4 + 1 - 1",
            "- 5 + 0 - 4 + 3", "+ 0 - 4 + 1 + 2");

    @BeforeClass
    public static void beforeClass() throws IOException {
        parser = new Parser();

        parser.parse(biblioFile);

        IndexQueryEstimator estimator = new IndexQueryEstimator();

        estimator.buildSummary(parser, 3, 837*8);

        algo = new NaiveJoinAlgorithm(estimator);
    }

    @Test
    public void execute() {
        List<int[]> intArrPaths = paths.stream().map(path -> Main.translateTextQueryToDomainQuery(path, parser)).collect(Collectors.toList());
        List<ComparisonResult> res = ComparisonExecutor.executeComparisonsForPaths(intArrPaths, algo, new AdjacencyList(parser));

        double percentageSum = 0;

        for(ComparisonResult comparisonResult : res) {
            percentageSum += comparisonResult.getAccuracy();
        }

        double accAverage = percentageSum / ((double)res.size());

        System.out.println(String.format("Default method with dynamic joining of paths and naive joining " +
                "has an accuracy of %s", accAverage));
    }
}
