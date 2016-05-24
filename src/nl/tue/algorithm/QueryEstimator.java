package nl.tue.algorithm;

import nl.tue.io.Parser;

import java.util.List;

/**
 * Created by Nathan on 5/24/2016.
 */
public interface QueryEstimator {

    /**
     * Instructs the estimator to build a summary of the given graph for a certain k and b.
     * @param p
     * @param k The maximum path length of a query that should be estimated.
     * @param b The amount of memory that this class is allowed to store.
     */
    void buildSummary(Parser p, int k, double b);

    int query(List<Long> query);

    long getBytesUsed();
}
