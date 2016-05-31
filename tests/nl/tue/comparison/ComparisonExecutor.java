package nl.tue.comparison;

import nl.tue.algorithm.*;
import nl.tue.algorithm.pathindex.IndexQueryEstimator;
import nl.tue.algorithm.pathindex.PathSummary;
import nl.tue.io.graph.AdjacencyList;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Nathan on 5/25/2016.
 */
public class ComparisonExecutor {

    private static List<TestEnvironment> environments;
    private static List<TestEnvironment> smallEnvironments;

    @BeforeClass
    public static void before() {
        environments = new ArrayList<>();
        smallEnvironments = new ArrayList<>();

        List<String> biblioQueries = Arrays.asList("+ 5 + 0 + 4 + 1 + 2", "+ 5 + 0 + 4 + 3",
                "+ 5 + 0 + 1 + 2", "+ 0 + 4 + 1 + 2", "+ 5 + 0 - 4 + 1 + 2", "- 5 + 0 + 4 + 1 - 1",
                "- 5 + 0 - 4 + 3", "+ 0 - 4 + 1 + 2");

        File biblioFile = new File(AdjacencyList.class.getClassLoader().getResource("biblio.txt").getFile());

        TestEnvironment biblio = new TestEnvironment(biblioQueries, biblioFile, "Biblio");

        environments.add(biblio);
        smallEnvironments.add(biblio);

        List<String> musicQueries = Arrays.asList("+ 3 - 4 + 5 - 5 + 6", "- 2 - 2 + 5 - 5 + 6", "- 6 - 2 + 5 - 5 + 6",
                "- 4 + 5 - 5 + 6");

        File musicFile = new File(AdjacencyList.class.getClassLoader().getResource("generatedmusicdata.txt").getFile());

        TestEnvironment music = new TestEnvironment(musicQueries, musicFile, "Music");

        environments.add(music);
    }

    @Test
    public void testNaiveIndexAndJoin() {
        Algorithm<PathSummary, IndexQueryEstimator> algo = new NaiveJoinAlgorithm(new IndexQueryEstimator());

        executeAndReportTests(algo, environments);
    }

    @Test
    public void testAlgorithm_1() {
        Algorithm<PathSummary, IndexQueryEstimator> algo = new Algorithm_1<>(new IndexQueryEstimator());

        executeAndReportTests(algo, environments);
    }

    private static void executeAndReportTests(Algorithm<? extends Estimation, ? extends Estimator<? extends Estimation>> algo,
                                       List<TestEnvironment> envs) {
        List<ComparisonResult> res = new ArrayList<>();
        Map<String, Double> envAcc = new HashMap<>();

        for(TestEnvironment env : envs) {
            List<ComparisonResult> comparisonResults = env.execute(algo);
            double envAccuracy = computeAverage(comparisonResults.stream().
                    map(ComparisonResult::getAccuracy).collect(Collectors.toList()));

            envAcc.put(env.getName(), envAccuracy);

            res.addAll(comparisonResults);

            System.out.println(String.format("%nAccuracy for environment: '%s' is %f", env.getName(), envAccuracy));
        }

       double accAverage = computeAverage(res.stream().map(ComparisonResult::getAccuracy).collect(Collectors.toList()));

        System.out.println(String.format("Total average accuracy over all environment is: %f", accAverage));
    }

    private static double computeAverage(List<Double> in) {
        double percentageSum = 0;

        for(double i : in) {
            percentageSum += i;
        }

       return percentageSum / ((double)in.size());
    }

}
