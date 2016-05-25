package nl.tue.algorithm;

import nl.tue.MemoryConstrained;
import nl.tue.io.Parser;

import java.util.Arrays;
import java.util.List;

/**
 * Generic wrapper for the execution and memory management of the algorithm
 * @param <E> class used for Estimations
 * @param <R> class used to store create the Estimations
 */
public abstract class Algorithm<E extends Estimation, R extends Estimator<E>> implements MemoryConstrained {

    R inMemoryEstimator;
    private int ALL_VALUES = 0;
    private Parser.LabelMapping labelMapping = null;

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

    final public int queryRaw(long[] rawQuery) {
        return query(Arrays.stream(rawQuery).mapToInt(labelMapping).toArray());
    }

    @Override
    public void buildSummary(Parser p, int k, double b) {
        this.ALL_VALUES = p.getNEdges() * p.getNEdges();
        this.labelMapping = p.getLabelMapping();
        inMemoryEstimator.buildSummary(p, k, b);
    }

    @Override
    public long getBytesUsed() {
        return inMemoryEstimator.getBytesUsed();
    }

    public int queryRaw(List<Long> query) {
        return queryRaw(query.stream().mapToLong(i -> i).toArray());
    }
}
