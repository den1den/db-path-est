package nl.tue.algorithm;

import nl.tue.MemoryConstrained;

import java.util.Collection;

/**
 * Created by dennis on 24-5-16.
 */
public interface Estimator<E extends Estimation> extends MemoryConstrained {

    E combineEstimations(E left, E right);

    Collection<E> retrieveAllExactEstimations();

    /**
     * Instructs the estimator to build a summary of the given graph for a certain k and b.
     *
     * @param p
     * @param k The maximum path length of a getEstimation that should be estimated.
     * @param b The amount of memory that this class is allowed to store.
     */
    void buildSummary(Parser p, int k, double b);
}
