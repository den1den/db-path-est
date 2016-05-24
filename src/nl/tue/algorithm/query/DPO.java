package nl.tue.algorithm.query;

import java.util.List;

/**
 * Dynamic Programming Optimizer
 * Created by dennis on 24-5-16.
 */
public class DPO implements Optimizer {

    Estimator precessionEvaluator;

    /**
     * Dynamic Programming Optimizer
     *
     * @param precessionEvaluator measure for the precession of a join
     */
    public DPO(Estimator precessionEvaluator) {
        this.precessionEvaluator = precessionEvaluator;
    }

    @Override
    public List<List<Long>> getExecutionOrder(List<Long> query) {
        throw new UnsupportedOperationException("TODO");
    }
}
