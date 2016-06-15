package nl.tue.algorithm.subgraph;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.subgraph.estimator.SubgraphEstimatorWithEdgeBasedFactors;
import nl.tue.io.Parser;

/**
 * Created by Nathan on 6/10/2016.
 */
public class SubgraphWithEdgeFactorAlgorithm extends Algorithm {
    protected SubgraphEstimatorWithEdgeBasedFactors withEdgeBasedFactors = null;

    @Override
    public void buildSummary(Parser p, int maximalPathLength, long budget) {
        withEdgeBasedFactors = new SubgraphEstimatorWithEdgeBasedFactors();
        withEdgeBasedFactors.buildSummary(p, maximalPathLength, budget);
    }

    @Override
    public long query(int[] query) {
        return withEdgeBasedFactors.estimate(query);
    }

    @Override
    public long getBytesUsed() {
        return withEdgeBasedFactors.getBytesUsed();
    }

    @Override
    public String getOutputName() {
        return getClass().getSimpleName();
    }
}
