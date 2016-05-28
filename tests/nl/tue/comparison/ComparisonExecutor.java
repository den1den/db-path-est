package nl.tue.comparison;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.Estimation;
import nl.tue.algorithm.Estimator;
import nl.tue.algorithm.NaiveJoinAlgorithm;
import nl.tue.algorithm.pathindex.IndexQueryEstimator;
import nl.tue.algorithm.pathindex.PathIndex;
import nl.tue.algorithm.pathindex.PathSummary;
import nl.tue.io.graph.AdjacencyList;
import nl.tue.io.graph.DirectedBackEdgeGraph;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nathan on 5/25/2016.
 */
public class ComparisonExecutor {

    private static List<TestEnvironment> environments;

    @BeforeClass
    public static void before() {
        environments = new ArrayList<>();

        List<String> biblioQueries = Arrays.asList("+ 5 + 0 + 4 + 1 + 2", "+ 5 + 0 + 4 + 3",
                "+ 5 + 0 + 1 + 2", "+ 0 + 4 + 1 + 2", "+ 5 + 0 - 4 + 1 + 2", "- 5 + 0 + 4 + 1 - 1",
                "- 5 + 0 - 4 + 3", "+ 0 - 4 + 1 + 2");

        File biblioFile = new File(AdjacencyList.class.getClassLoader().getResource("biblio.txt").getFile());

        TestEnvironment biblio = new TestEnvironment(biblioQueries, biblioFile);

        environments.add(biblio);
    }

    @Test
    public void testNaiveIndexAndJoin() {
        Algorithm<PathSummary, IndexQueryEstimator> algo = new NaiveJoinAlgorithm(new IndexQueryEstimator());

        executeAndReportTests(algo, environments);
    }

    private static void executeAndReportTests(Algorithm<? extends Estimation, ? extends Estimator<? extends Estimation>> algo,
                                       List<TestEnvironment> envs) {
        List<ComparisonResult> res = new ArrayList<>();

        for(TestEnvironment env : envs) {
            res.addAll(env.execute(algo));
        }

        double percentageSum = 0;

        for(ComparisonResult comparisonResult : res) {
            percentageSum += comparisonResult.getAccuracy();
        }

        double accAverage = percentageSum / ((double)res.size());

        System.out.println(String.format("Default method with dynamic joining of paths and naive joining " +
                "has an accuracy of %s", accAverage));
    }

}
