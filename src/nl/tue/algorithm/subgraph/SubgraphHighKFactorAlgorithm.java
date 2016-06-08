package nl.tue.algorithm.subgraph;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.subgraph.estimator.SubgraphEstimatorWithFactors;
import nl.tue.algorithm.subgraph.estimator.SubgraphEstimatorsWithHighKFactors;
import nl.tue.io.Parser;

/**
 * Created by Nathan on 6/8/2016.
 */
public class SubgraphHighKFactorAlgorithm extends Algorithm<SubgraphEstimatorsWithHighKFactors> {
    @Override
    protected SubgraphEstimatorsWithHighKFactors build(Parser p, int maximalPathLength, long budget) {
        SubgraphEstimatorsWithHighKFactors algo = new SubgraphEstimatorsWithHighKFactors();

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
