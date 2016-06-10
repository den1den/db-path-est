package nl.tue.algorithm.subgraph;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.subgraph.estimator.SubgraphEstimator;
import nl.tue.algorithm.subgraph.estimator.SubgraphEstimatorWithEdgeBasedFactors;
import nl.tue.algorithm.subgraph.estimator.SubgraphEstimatorsWithHighKFactors;
import nl.tue.io.Parser;

/**
 * Created by Nathan on 6/10/2016.
 */
public class SubgraphWithEdgeFactorAlgorithm extends Algorithm<SubgraphEstimatorWithEdgeBasedFactors> {
    @Override
    protected SubgraphEstimatorWithEdgeBasedFactors build(Parser p, int maximalPathLength, long budget) {
        SubgraphEstimatorWithEdgeBasedFactors esti = new SubgraphEstimatorWithEdgeBasedFactors();
        esti.buildSummary(p, maximalPathLength, budget);
        return esti;

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
