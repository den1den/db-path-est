package nl.tue.algorithm.subgraph;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.subgraph.estimator.AverageSubgraphEstimator;
import nl.tue.io.Parser;

/**
 * Created by Nathan on 6/13/2016.
 */
public class SubgraphAverageOfThree extends Algorithm {

    AverageSubgraphEstimator esti;


    @Override
    public void buildSummary(Parser p, int maximalPathLength, long budget) {
        esti = new AverageSubgraphEstimator();

        esti.buildSummary(p, maximalPathLength, budget);

    }

    @Override
    public int query(int[] query) {
        return esti.estimate(query);
    }

    @Override
    public long getBytesUsed() {
        return 0;
    }


    @Override
    public String getOutputName() {
        return this.getClass().getSimpleName();
    }
}
