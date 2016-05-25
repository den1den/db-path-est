package nl.tue;

import nl.tue.io.Parser;

/**
 * Created by dennis on 25-5-16.
 */
public interface MemoryConstrained {

    /**
     * Instructs the estimator to build a summary of the given graph for a certain k and b.
     *
     * @param p
     * @param k The maximum path length of a getEstimation that should be estimated.
     * @param b The amount of memory that this class is allowed to store.
     */
    void buildSummary(Parser p, int k, double b);

    /**
     * The current number of bytes used
     *
     * @return
     */
    long getBytesUsed();

}
