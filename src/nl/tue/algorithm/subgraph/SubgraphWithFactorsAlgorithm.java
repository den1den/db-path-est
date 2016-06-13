package nl.tue.algorithm.subgraph;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.subgraph.estimator.SubgraphEstimatorWithFactors;
import nl.tue.io.Parser;

/**
 * Created by Nathan on 6/7/2016.
 */
public class SubgraphWithFactorsAlgorithm extends Algorithm<SubgraphEstimatorWithFactors> {
    protected SubgraphEstimatorWithFactors estimatorWithFactors = null;

    @Override
    public void buildSummary(Parser p, int maximalPathLength, long budget) {
        estimatorWithFactors = new SubgraphEstimatorWithFactors();
        estimatorWithFactors.buildSummary(p, maximalPathLength, budget);
    }

    @Override
    public int query(int[] query) {
        return estimatorWithFactors.estimate(query);
    }

    @Override
    public long getBytesUsed() {
        return estimatorWithFactors.getBytesUsed();
    }

    @Override
    public String getOutputName() {
        return getClass().getSimpleName();
    }
}
