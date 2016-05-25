package nl.tue.algorithm;

import nl.tue.MemoryConstrained;

import java.util.Collection;

/**
 * Created by dennis on 24-5-16.
 */
public interface Estimator<E extends Estimation> extends MemoryConstrained {

    E combineEstimations(E left, E right);

    Collection<E> retrieveAllExactEstimations();
}
