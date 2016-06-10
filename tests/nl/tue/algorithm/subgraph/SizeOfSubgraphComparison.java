package nl.tue.algorithm.subgraph;

import nl.tue.algorithm.subgraph.estimator.SubgraphEstimator;
import nl.tue.comparison.ComparisonExecutor;
import nl.tue.comparison.TestEnvironment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Created by Nathan on 6/9/2016.
 */
@RunWith(Parameterized.class)
public class SizeOfSubgraphComparison {


    private final TestEnvironment environment;

    public SizeOfSubgraphComparison(TestEnvironment environment) {
        this.environment = environment;
    }

    @Parameterized.Parameters
    public static Object[] getParams() {
        return ComparisonExecutor.getParams();
    }

    @Test
    public void testDefault() {
        SubGraphAlgorithm algo = new SubGraphAlgorithm();

        ComparisonExecutor.reportSingleEnv(algo, environment, "Default");
    }

    @Test
    public void testEverySecondNode() {
        SubGraphAlgorithm algo = new SubGraphAlgorithm(new SubgraphEstimator(node -> node % 2 ==0));


        ComparisonExecutor.reportSingleEnv(algo, environment, "EverySecond");
    }

    @Test
    public void testEveryFourthNode() {
        SubGraphAlgorithm algo = new SubGraphAlgorithm(new SubgraphEstimator(node -> node % 4 ==0));


        ComparisonExecutor.reportSingleEnv(algo, environment, "EveryFourth");
    }

    @Test
    public void testEverySecondThirdFifthNode() {
        SubGraphAlgorithm algo = new SubGraphAlgorithm(new SubgraphEstimator(node -> node % 2 ==0 || node % 3== 0 ||
        node % 5 == 0));


        ComparisonExecutor.reportSingleEnv(algo, environment, "EverySecondThirdFifth");
    }


}
