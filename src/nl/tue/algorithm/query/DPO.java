package nl.tue.algorithm.query;

import nl.tue.algorithm.Estimation;
import nl.tue.algorithm.Estimator;

import java.util.HashMap;
import java.util.List;

/**
 * Dynamic Programming Optimizer
 * Created by dennis on 24-5-16.
 */
public class DPO implements Optimizer {
    /**
     * Dynamic Programming Optimizer
     *
     * @param precessionEvaluator measure for the precession of a join
     */
    public DPO(Estimator precessionEvaluator) {
        this.precessionEvaluator = precessionEvaluator;
    }

    HashMap<List<Long>, D> precision = new HashMap<>();

    @Override
    public Plan getExecutionOrder(List<Long> query) {
        D dynamicResult = execute(query);
    }

    private D execute(List<Long> query) {
        D best = precision.get(query);
        if (best.plan != null) {
            return best;
        }

    }


    private class D {
        Estimation estimation;
        Plan plan;
    }
}
