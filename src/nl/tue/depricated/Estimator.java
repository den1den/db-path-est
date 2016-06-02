package nl.tue.depricated;

import nl.tue.MemoryConstrained;
import nl.tue.algorithm.Estimation;
import nl.tue.io.Parser;

import java.util.Collection;
import java.util.List;

/**
 * Created by dennis on 24-5-16.
 */
public interface Estimator<E extends Estimation> extends MemoryConstrained {

    E concatEstimations(E left, E right);
    int combineEstimations(List<E> sortedEs);

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
