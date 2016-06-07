package nl.tue.comparison;

import junit.framework.TestCase;
import nl.tue.algorithm.*;
import nl.tue.io.graph.AdjacencyList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nathan on 5/25/2016.
 */
@RunWith(Parameterized.class)
public class ComparisonExecutor extends TestCase {

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

        List<String> cineastQueries = Arrays.asList("+ 4 - 4 + 4 - 4 + 4", "+ 4 - 3 + 4 - 3 + 4", "+ 4 - 3 - 4 - 3 + 4",
                "+ 1 - 3 + 4 - 3 + 4", "+ 4 - 4 + 4 - 4", "+ 3 - 3 + 3 - 3", "+ 1 - 4 + 4 - 3", "+ 1 - 3 + 4 - 3",
                "+ 1 - 3 + 3 - 3", "+ 2 + 1 - 1", "+ 2 + 2 + 1", "+ 2 + 1 - 4", "+ 1 - 4 + 4", "+ 3 - 4 + 3", "+ 3 - 3 + 3",
                "+ 4 - 4 + 4", "+ 2 + 1", "- 2 + 1", "- 3 + 3", "+ 3 - 3", "+ 3", "+ 4", "+ 2");

        File cineastFile = new File(AdjacencyList.class.getClassLoader().getResource("cineasts.txt").getFile());

        TestEnvironment cineasts = new TestEnvironment(cineastQueries, cineastFile, "Cineast");

        environments.add(cineasts);
    }

    @Parameterized.Parameters
    public static Object[] getParams() {
        before();
        return environments.toArray();
    }

    public ComparisonExecutor(TestEnvironment env) {
        this.env = env;
    }

    private static void reportSingleEnv(Algorithm algo,
                                        TestEnvironment env) {
        List<ComparisonResult> comparisonResults = env.execute(algo);
        double envAccuracy = computeAverage(comparisonResults.stream().
                map(ComparisonResult::getAccuracy).collect(Collectors.toList()));

        System.out.println(String.format("%s:", env.getName()));

        for(ComparisonResult compRes : comparisonResults) {
            System.out.println(String.format("\t\tQuery '%s' Expected: %d Estimated: %d Accuracy: %f", compRes.getIndex().getPath(),
                    compRes.getResult(), compRes.getEstimation(), compRes.getAccuracy()));
        }

        System.out.println(String.format("\tAccuracy for environment: '%s' is %f", env.getName(), envAccuracy));
    }

    @Test
    public void testAlgorithm_NaiveIndexAndJoin() {
        Algorithm algo = new NaiveJoinAlgorithm();
        reportSingleEnv(algo, env);
    }

    @Test
    public void testAlgorithm_Brute() {
        reportSingleEnv(new Algorithm_Brute(), this.env);
    }

    @Test
    public void testSubGraph() {
        Algorithm algo = new SubGraphAlgorithm();
        reportSingleEnv(algo, this.env);
    }

    @Test
    public void testHisoGram(){
        reportSingleEnv(new Algorithm_Histogram(), this.env);
    }

    private static double computeAverage(List<Double> in) {
        double percentageSum = 0;

        for(double i : in) {
            percentageSum += i;
        }

       return percentageSum / ((double)in.size());
    }

}
