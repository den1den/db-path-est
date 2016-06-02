package nl.tue.algorithm;

import nl.tue.MemoryConstrained;
import nl.tue.io.Parser;

/**
 * Generic wrapper for the execution and memory management of the algorithm
 * @param <M> class used for whats in memory
 */
public abstract class Algorithm<M extends MemoryConstrained> implements MemoryConstrained {
    M inMemory = null;

    public Algorithm() {
    }

    public void buildSummary(Parser p, int maximalPathLength, long budget) {
        this.inMemory = build(p, maximalPathLength, budget);
    }

    abstract M build(Parser p, int maximalPathLength, long budget);

    public abstract long query(int[] query);
}
