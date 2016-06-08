package nl.tue.algorithm.subgraph;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.subgraph.estimator.SubgraphEstimatorWithFactors;
import nl.tue.io.Parser;

/**
 * Created by Nathan on 6/7/2016.
 */
public class SubgraphWithFactorsAlgorithm extends Algorithm<SubgraphEstimatorWithFactors> {
    @Override
    protected SubgraphEstimatorWithFactors build(Parser p, int maximalPathLength, long budget) {
        SubgraphEstimatorWithFactors algo = new SubgraphEstimatorWithFactors();

        algo.buildSummary(p, maximalPathLength, budget);

        return algo;
    }

    @Override
    public int query(int[] query) {
        return inMemory.estimate(query);
    }

    @Override
    protected long bytesOverhead() {
        return 0;
    }
}
