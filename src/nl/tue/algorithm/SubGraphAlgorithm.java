package nl.tue.algorithm;

import nl.tue.algorithm.pathindex.PathSummary;
import nl.tue.algorithm.subgraph.SubgraphEstimator;

/**
 * Created by Nathan on 6/4/2016.
 */
public class SubGraphAlgorithm extends Algorithm<PathSummary, SubgraphEstimator> {

    public SubGraphAlgorithm(SubgraphEstimator estimator) {
        super(estimator);
    }

    @Override
    protected int executeQuery(int[] query) {
        return inMemoryEstimator.estimate(query);
    }
}
