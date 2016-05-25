package nl.tue.algorithm;

import nl.tue.algorithm.query.DPO;
import nl.tue.algorithm.query.Optimizer;
import nl.tue.io.Parser;

import java.util.List;

public class Algorithm_1 implements Algorithm {

    Estimator inMemoryEstimator;
    Optimizer currentOptimizer;

    @Override
    public void buildSummary(Parser p, int k, double b) {
        inMemoryEstimator.buildSummary(p, k, b);

        Estimator precissionEvaluator = null; //TODO
        currentOptimizer = new DPO(precissionEvaluator);
    }

    @Override
    public int query(List<Long> query) {
        int total = 0;
        if (query.isEmpty()) {
            return total;
        }
        Optimizer.Plan order = currentOptimizer.getExecutionOrder(query);
        Estimation estimate = getEstimate(order);
        int tuples = estimate.getTuples();
        return tuples;
    }

    private Estimation getEstimate(Optimizer.SubResult query) {
        Estimation left;
        Estimation right;
        if (query.leftIsLeaf()) {
            List<Long> subQuery = ((Optimizer.Result) query.left()).getSubQuery();
            left = inMemoryEstimator.query(subQuery);
            assert left != null; // Assert estimation of the real joins is in memory
        } else {
            left = getEstimate(query.left());
        }
        if (query.rightIsLeaf()) {
            List<Long> subQuery = ((Optimizer.Result) query.right()).getSubQuery();
            right = inMemoryEstimator.query(subQuery);
            assert right != null; // Assert estimation of the real joins is in memory
        } else {
            right = getEstimate(query.right());
        }
        return inMemoryEstimator.combineEstimations(left, right);
    }

    @Override
    public long getBytesUsed() {
        return inMemoryEstimator.getBytesUsed();
    }

}
