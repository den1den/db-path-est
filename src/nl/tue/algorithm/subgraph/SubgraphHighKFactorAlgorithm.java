package nl.tue.algorithm.subgraph;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.subgraph.estimator.AcceptNode;
import nl.tue.algorithm.subgraph.estimator.SubgraphEstimatorsWithHighKFactors;
import nl.tue.io.Parser;

/**
 * Created by Nathan on 6/8/2016.
 */
public class SubgraphHighKFactorAlgorithm extends Algorithm {
    protected SubgraphEstimatorsWithHighKFactors withHighKFactors = null;

    private final AcceptNode acceptor;

    public SubgraphHighKFactorAlgorithm() {
        acceptor = AcceptNode.everySecondOrThird();
    }

    public SubgraphHighKFactorAlgorithm(AcceptNode acceptor) {
        this.acceptor = acceptor;
    }

    @Override
    public void buildSummary(Parser p, int maximalPathLength, long budget) {
        withHighKFactors = new SubgraphEstimatorsWithHighKFactors(this.acceptor);
        withHighKFactors.buildSummary(p, maximalPathLength, budget);
    }

    @Override
    public long query(int[] query) {
        return withHighKFactors.estimate(query);
    }

    @Override
    public long getBytesUsed() {
        return withHighKFactors.getBytesUsed();
    }

    @Override
    public String getOutputName() {
        return getClass().getSimpleName();
    }
}
