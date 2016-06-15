package nl.tue.algorithm.subgraph;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.subgraph.estimator.SubgraphEstimator;
import nl.tue.io.Parser;

/**
 * Created by Nathan on 6/4/2016.
 */
public class SubGraphAlgorithm extends Algorithm {

    private final SubgraphEstimator estimator;

    public SubGraphAlgorithm() {
        this.estimator = new SubgraphEstimator();
    }

    public SubGraphAlgorithm(SubgraphEstimator estimator) {
        this.estimator = estimator;
    }

    @Override
    public void buildSummary(Parser p, int maximalPathLength, long budget) {
        estimator.buildSummary(p, maximalPathLength, budget);
    }

    @Override
    public long query(int[] query) {
        return estimator.estimate(query);
    }

    @Override
    public long getBytesUsed() {
        return this.estimator.getBytesUsed();
    }

    @Override
    public String getOutputName() {
        return getClass().getSimpleName();
    }
}
