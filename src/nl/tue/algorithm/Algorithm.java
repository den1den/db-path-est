package nl.tue.algorithm;

import nl.tue.io.Parser;

import java.util.List;

/**
 * This class stores all the data that is available in memory.
 * It should also be able to perform an query
 */
public interface Algorithm {

    /**
     * The current number of bytes used
     *
     * @return
     */
    long getBytesUsed();

    /**
     * Estimates the number of expected unique start and end tuples of a query.
     *
     * @param query the path query, with all the indices of each label
     * @return expected number of tuples in end result
     */
    int query(List<Long> query);

    /**
     * Instructs the estimator to build a summary of the given graph for a certain k and b.
     *
     * @param p
     * @param k The maximum path length of a query that should be estimated.
     * @param b The amount of memory that this class is allowed to store.
     */
    void buildSummary(Parser p, int k, double b);

}
