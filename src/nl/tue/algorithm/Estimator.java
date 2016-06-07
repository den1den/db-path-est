package nl.tue.algorithm;

import nl.tue.depricated.Estimation;

import java.util.Collection;

/**
 * Created by Dennis on 4-6-2016.
 */
public interface Estimator<E> {
    E getEstimation(int[] path);
}
