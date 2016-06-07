package nl.tue.algorithm;

import nl.tue.MemoryConstrained;

/**
 * Created by Dennis on 4-6-2016.
 */
public interface Estimator<E> extends MemoryConstrained {
    E getEstimation(int[] path);
}
