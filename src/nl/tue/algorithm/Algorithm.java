package nl.tue.algorithm;

import nl.tue.MemoryConstrained;
import nl.tue.io.Parser;

/**
 * Generic wrapper for the execution and memory management of the algorithm
 */
public abstract class Algorithm implements MemoryConstrained {

    public Algorithm() {
    }

    public abstract void buildSummary(Parser p, int maximalPathLength, long budget);

    public abstract int query(int[] query);

    @Override
    public abstract long getBytesUsed();

    public abstract String getOutputName();
}
