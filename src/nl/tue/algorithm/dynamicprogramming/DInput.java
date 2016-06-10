package nl.tue.algorithm.dynamicprogramming;

/**
 * Created by Dennis on 10-6-2016.
 */
public interface DInput<E> {
    /**
     * Get a stored result
     * @param query
     * @return Estimation iff its in memory, null otherwise
     */
    E getStored(int[] query);
}
