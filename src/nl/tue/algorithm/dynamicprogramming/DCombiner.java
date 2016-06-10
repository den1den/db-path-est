package nl.tue.algorithm.dynamicprogramming;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Dennis on 10-6-2016.
 */
public interface DCombiner<E> extends Comparator<E>{
    /**
     * Concat two estimations during the dynamic programming
     * @param headEstimation
     * @param tailEstimation
     * @return
     */
    E concatEstimations(E headEstimation, E tailEstimation);

    /**
     * Combine (single or list of) estimations to a final result of tuples
     * @param list
     * @return
     */
    int estimationsToResult(List<E> list);
}
