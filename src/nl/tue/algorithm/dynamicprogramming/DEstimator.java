package nl.tue.algorithm.dynamicprogramming;

import nl.tue.algorithm.Estimator;

import java.util.Collection;
import java.util.List;

/**
 * Created by Dennis on 7-6-2016.
 */
public interface DEstimator<E extends Estimation> extends Estimator<E> {
    Collection<E> retrieveAllExactEstimations();

    E concatEstimations(E headEstimation, E tailEstimation);

    int combineEstimations(List<E> sameLevel);
}
