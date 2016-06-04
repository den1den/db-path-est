package nl.tue.comparison;

import nl.tue.algorithm.*;
import nl.tue.algorithm.pathindex.IndexQueryEstimator;
import nl.tue.algorithm.pathindex.PathSummary;
import nl.tue.algorithm.subgraph.SubgraphEstimator;
import nl.tue.depricated.Algorithm_2;
import nl.tue.io.graph.AdjacencyList;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.omg.CORBA.Environment;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Nathan on 5/25/2016.
 */
@RunWith(Parameterized.class)
public class ComparisonExecutor {

    private static List<TestEnvironment> environments;
    private final TestEnvironment env;

    public static void before() {
        environments = new ArrayList<>();

        List<String> biblioQueries = Arrays.asList("+ 5 + 0 + 4 + 1 + 2", "+ 5 + 0 + 4 + 1 - 1", "+ 5 + 0 + 4 + 3",
                "+ 5 + 0 + 1 + 2", "+ 0 + 4 + 1 + 2", "+ 5 + 0 - 4 + 1 + 2", "- 5 + 0 + 4 + 1 - 1",
                "- 5 + 0 - 4 + 3", "+ 0 - 4 + 1 + 2", "+ 5 + 0 + 1 + 2", "- 5 + 5 + 0", "+ 0 + 4 - 4",
                "+ 0 + 1 + 2", "+ 0 + 1 + 2", "+ 0 - 4 + 1", "+ 0 + 4 + 1", "- 5 + 0 + 3","- 3 + 4 - 0",
                "- 3 - 4 - 0", "+ 5 + 5 + 4", "+ 5 + 0", "+ 0 - 4", "+ 0 + 1", "- 1 + 3", "+ 1 + 2",
                "+ 3 + 1", "- 3 - 0");

        File biblioFile = new File(AdjacencyList.class.getClassLoader().getResource("biblio.txt").getFile());

        TestEnvironment biblio = new TestEnvironment(biblioQueries, biblioFile, "Biblio");

        environments.add(biblio);

        List<String> musicQueries = Arrays.asList("+ 3 - 4 + 5 - 5 + 6", "- 2 - 2 + 5 - 5 + 6", "- 6 - 2 + 5 - 5 + 6",
                "- 4 + 5 - 5 + 6", "- 1 - 2 + 5 - 5 + 6", "+ 3 - 4 - 2 + 6", "+ 4 - 4 - 2 + 6", "- 4 + 5 - 5 + 6",
                "- 2 + 5 - 5 + 6", "- 6 - 2 - 2", "+ 5 - 5 - 2", "+ 2 + 2 + 2", "+ 3 - 4 + 6", "- 4 - 2 + 6", "+ 4 - 3",
                "- 6 - 2", "- 6 + 5", "+ 5 - 5", "- 1 + 5", "- 2 - 2", "+ 2 - 2", "+ 2 - 3", "+ 1", "+ 2", "+ 3",
                "+ 5");

        File musicFile = new File(AdjacencyList.class.getClassLoader().getResource("generatedmusicdata.txt").getFile());

        TestEnvironment music = new TestEnvironment(musicQueries, musicFile, "Music");

        environments.add(music);
    }

    @Parameterized.Parameters
    public static Object[] getParams() {
        before();
        return environments.toArray();
    }

    public ComparisonExecutor(TestEnvironment env) {
        this.env = env;
    }

    @Test
    public void testNaiveIndexAndJoin() {
        Algorithm<PathSummary, IndexQueryEstimator> algo = new Algorithm_2(new IndexQueryEstimator());

        reportSingleEnv(algo, env);
    }

    @Test
    public void testAlgorithm_1() {
        Algorithm<PathSummary, IndexQueryEstimator> algo = new Algorithm_PS<>(new IndexQueryEstimator());

        reportSingleEnv(algo, this.env);
    }

    @Test
    public void testSubGraph() {
        Algorithm<PathSummary, SubgraphEstimator> algo = new SubGraphAlgorithm(new SubgraphEstimator());

        reportSingleEnv(algo, this.env);
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
            System.out.println(String.format("%s:", env.getName()));

            for(ComparisonResult compRes : comparisonResults) {
                System.out.println(String.format("\t\tQuery '%s' Expected: %d Estimated: %d Accuracy %f", compRes.getIndex().getPath(),
                        compRes.getResult(), compRes.getEstimation(), compRes.getAccuracy()));
            }

            System.out.println(String.format("\tAccuracy for environment: '%s' is %f", env.getName(), envAccuracy));
        }

       double accAverage = computeAverage(res.stream().map(ComparisonResult::getAccuracy).collect(Collectors.toList()));

        System.out.println(String.format("Total average accuracy over all environment is: %f", accAverage));
    }

    private static void reportSingleEnv(Algorithm<? extends Estimation, ? extends Estimator<? extends Estimation>> algo,
                                        TestEnvironment env) {
        List<ComparisonResult> comparisonResults = env.execute(algo);
        double envAccuracy = computeAverage(comparisonResults.stream().
                map(ComparisonResult::getAccuracy).collect(Collectors.toList()));

        System.out.println(String.format("%s:", env.getName()));

        for(ComparisonResult compRes : comparisonResults) {
            System.out.println(String.format("\t\tQuery '%s' Expected: %d Estimated: %d Accuracy %f", compRes.getIndex().getPath(),
                    compRes.getResult(), compRes.getEstimation(), compRes.getAccuracy()));
        }

        System.out.println(String.format("\tAccuracy for environment: '%s' is %f", env.getName(), envAccuracy));
    }

    private static double computeAverage(List<Double> in) {
        double percentageSum = 0;

        for(double i : in) {
            percentageSum += i;
        }

       return percentageSum / ((double)in.size());
    }

}
