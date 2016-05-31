package nl.tue.algorithm;

import nl.tue.MemoryConstrained;
import nl.tue.io.Parser;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.List;

/**
 * Generic wrapper for the execution and memory management of the algorithm
 * @param <E> class used for Estimations
 * @param <R> class used to store create the Estimations
 */
public abstract class Algorithm<E extends Estimation, R extends Estimator<E>> implements MemoryConstrained {

    protected R inMemoryEstimator;
    private int ALL_VALUES = 0;

    public Algorithm(R estimator) {
        this.inMemoryEstimator = estimator;
    }

    /**
     * Estimates the number of expected unique start and end tuples of a getEstimation.
     *
     * @param query the path getEstimation, with all the indices of each label
     * @return expected number of tuples in end result
     */
    final public int query(int[] query) {
        if (query.length == 0) {
            return ALL_VALUES;
        }
        return executeQuery(query);
    }

    protected abstract int executeQuery(int[] query);

    final public int queryRaw(int[] rawQuery) {

        return executeQuery(rawQuery);
    }

    /**
         * Instructs the estimator to build a summary of the given graph for a certain k and b.
         *
         * @param p
         * @param k The maximum path length of a getEstimation that should be estimated.
         * @param b The amount of memory that this class is allowed to store.
         */
    public void buildSummary(Parser p, int k, double b) {
        this.ALL_VALUES = p.getNEdges() * p.getNEdges();
        inMemoryEstimator.buildSummary(p, k, b);
    }

    @Override
    public long getBytesUsed() {
        return inMemoryEstimator.getBytesUsed();
    }
}
