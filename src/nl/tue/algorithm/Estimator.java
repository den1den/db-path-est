package nl.tue.algorithm;

import java.util.List;

/**
 * Created by dennis on 24-5-16.
 */
public interface Estimator extends MemoryConstrained {

    /**
     * Gives an estimate of a query
     *
     * @param query
     * @return Estimation of a query, or null when it is not known
     */
    Estimation query(List<Long> query);

    Estimation combineEstimations(Estimation left, Estimation right);
}
