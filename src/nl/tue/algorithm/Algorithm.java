package nl.tue.algorithm;

import nl.tue.MemoryConstrained;
import nl.tue.io.Parser;

/**
 * Generic wrapper for the execution and memory management of the algorithm
 * @param <M> class used for whats in memory
 */
public abstract class Algorithm<M extends MemoryConstrained> implements MemoryConstrained {
    protected M inMemory = null;

    public Algorithm() {
    }

    public void buildSummary(Parser p, int maximalPathLength, long budget) {
        this.inMemory = build(p, maximalPathLength, budget);
    }

    protected abstract M build(Parser p, int maximalPathLength, long budget);

    public abstract int query(int[] query);

    @Override
    public long getBytesUsed() {
        return inMemory.getBytesUsed() + bytesOverhead();
    }

    protected abstract long bytesOverhead();
}
