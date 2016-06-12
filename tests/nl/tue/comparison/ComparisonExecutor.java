package nl.tue.comparison;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.Algorithm_Brute;
import nl.tue.algorithm.NaiveJoinAlgorithm;
import nl.tue.algorithm.histogram.JoinResult;
import nl.tue.algorithm.histogram.Joiner;
import nl.tue.algorithm.subgraph.*;
import nl.tue.io.graph.AdjacencyList;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nathan on 5/25/2016.
 */
@RunWith(Parameterized.class)
public class ComparisonExecutor {

    /**
     * Shoudl write:
     * graph_name, nodes, labels, build_time, query, query_time, algorithm_id, estimation_tuples, expected_tuples
     * Example:
     * "biblio", 500, 5, 1480, "+ 1 - 5", 149, algorithm.getOutputName(), 50, 55
     */
    private static final String OUTPUT_FILE = "results.csv";

    private final TestEnvironment env;

    public ComparisonExecutor(TestEnvironment env) {
        this.env = env;
    }

    @BeforeClass
    public static void before() throws IOException {
        File outputFile = new File(OUTPUT_FILE);

        if (!outputFile.exists()) {
            outputFile.createNewFile();
            PrintWriter writer = new PrintWriter(outputFile);

            writer.println("graph, nodes, labels, buildTime, query, queryTime, algoId, estimation, expected");
            writer.flush();
            writer.close();
        }
    }

    @Parameterized.Parameters
    public static Object[] getParams() {
        List<TestEnvironment> environments = new ArrayList<>();

        List<String> biblioQueries = Arrays.asList("+ 5 + 0 + 4 + 1 + 2", "+ 5 + 0 + 4 + 1 - 1", "+ 5 + 0 + 4 + 3",
                "+ 5 + 0 + 1 + 2", "+ 0 + 4 + 1 + 2", "+ 5 + 0 - 4 + 1 + 2", "- 5 + 0 + 4 + 1 - 1",
                "- 5 + 0 - 4 + 3", "+ 0 - 4 + 1 + 2", "+ 5 + 0 + 1 + 2", "- 5 + 5 + 0", "+ 0 + 4 - 4",
                "+ 0 + 1 + 2", "+ 0 + 1 + 2", "+ 0 - 4 + 1", "+ 0 + 4 + 1", "- 5 + 0 + 3", "- 3 + 4 - 0",
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

        List<String> cineastQueries = Arrays.asList("+ 4 - 4 + 4 - 4 + 4", "+ 4 - 3 + 4 - 3 + 4", "+ 4 - 3 - 4 - 3 + 4",
                "+ 1 - 3 + 4 - 3 + 4", "+ 4 - 4 + 4 - 4", "+ 3 - 3 + 3 - 3", "+ 1 - 4 + 4 - 3", "+ 1 - 3 + 4 - 3",
                "+ 1 - 3 + 3 - 3", "+ 2 + 1 - 1", "+ 2 + 2 + 1", "+ 2 + 1 - 4", "+ 1 - 4 + 4", "+ 3 - 4 + 3", "+ 3 - 3 + 3",
                "+ 4 - 4 + 4", "+ 2 + 1", "- 2 + 1", "- 3 + 3", "+ 3 - 3", "+ 3", "+ 4", "+ 2");

        File cineastFile = new File(AdjacencyList.class.getClassLoader().getResource("cineasts.txt").getFile());

        TestEnvironment cineasts = new TestEnvironment(cineastQueries, cineastFile, "Cineast");

        environments.add(cineasts);

        return environments.toArray();
    }

    private static double computeAverage(List<Double> in) {
        double percentageSum = 0;

        for (double i : in) {
            percentageSum += i;
        }

        return percentageSum / ((double) in.size());
    }

    public static void reportSingleEnv(Algorithm algo,
                                       TestEnvironment env, String methodName) {
        List<ComparisonResult> comparisonResults = env.execute(algo);
        double envAccuracy = computeAverage(comparisonResults.stream().
                map(ComparisonResult::getAccuracy).collect(Collectors.toList()));

        System.out.println(String.format("%s:", env.getName()));

        for (ComparisonResult compRes : comparisonResults) {
            System.out.println(String.format("\t\tQuery '%s' Expected: %d Estimated: %d Accuracy: %f Time: %d", compRes.getIndex().getPath(),
                    compRes.getResult(), compRes.getEstimation(), compRes.getAccuracy(), compRes.getQueryTime()));
        }

        System.out.println(String.format("\tAccuracy for environment: '%s' is %f Building the summmary took: %d",
                env.getName(), envAccuracy, env.getSummaryTime()));

        try {
            writeToFile(comparisonResults, env, methodName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeToFile(List<ComparisonResult> results, TestEnvironment env, String methodName) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(OUTPUT_FILE, true));

        for (ComparisonResult res : results) {
            writer.println(String.format("%s, %d, %d, %d, %s, %d, %s, %d, %d", env.getName(),  env.getNodes(),
                    env.getLabels(), env.getSummaryTime(), res.getIndex().getPath(), res.getQueryTime(), methodName,
                    res.getEstimation(), res.getResult()));
        }

        writer.flush();
        writer.close();
    }

    @Test
    public void testAlgorithm_NaiveIndexAndJoin() {
        Algorithm algo = new NaiveJoinAlgorithm();
        reportSingleEnv(algo, env, "NaiveIndex");
    }

    @Test
    public void testAlgorithm_Brute() {
        reportSingleEnv(new Algorithm_Brute(), this.env, "Brute");
    }

    @Test
    public void testAlgorithm_Subgraph() {
        reportSingleEnv(new SubGraphAlgorithm(), this.env, "Subgraph");
    }

    @Test
    public void testAlgorithm_SubgraphWithFactors() {
        reportSingleEnv(new SubgraphWithFactorsAlgorithm(), this.env, "SubgraphKFactors");
    }

    @Test
    public void testAlgorithm_SubgraphWithHighKFactors() {
        reportSingleEnv(new SubgraphHighKFactorAlgorithm(), this.env, "SubgraphHighK");
    }

    @Test
    public void testAlgorithm_SubgraphWithEdgeBasedFactors() {
        reportSingleEnv(new SubgraphWithEdgeFactorAlgorithm(), this.env, "SubgraphEdgeBased");
    }

    @Test
    public void testAlgorithm_SubgraphWithSpecificFactors() {
        Joiner<Double, JoinResult.NumberJoinResult> baiscJoiner = new Joiner.BasicJoiner();
        double subGraphSize = 0.9;
        reportSingleEnv(new SubGraphAlgorithm_SF(baiscJoiner, subGraphSize), this.env, "SubgraphSF_basicJoiner_" + subGraphSize);
    }
}
