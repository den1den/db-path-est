package nl.tue.algorithm.subgraph;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.subgraph.estimator.SubgraphEstimator;
import nl.tue.io.Parser;

/**
 * Created by Nathan on 6/4/2016.
 */
public class SubGraphAlgorithm extends Algorithm<SubgraphEstimator> {

    public SubGraphAlgorithm() {
    }

    @Override
    protected SubgraphEstimator build(Parser p, int maximalPathLength, long budget) {
        SubgraphEstimator estimator = new SubgraphEstimator();
        estimator.buildSummary(p, maximalPathLength, budget);
        return estimator;
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
