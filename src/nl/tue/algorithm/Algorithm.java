package nl.tue.algorithm;

import nl.tue.io.Parser;

import java.util.List;

/**
 * Created by dennis on 17-5-16.
 */
public abstract class Algorithm {

    final Parser input;
    final long maxPathLength;
    final long maxMemoryUsage;


    public Algorithm(Parser p, long maxPathLength, long maxMemoryUsage) {
        this.input = p;
        this.maxPathLength = maxPathLength;
        this.maxMemoryUsage = maxMemoryUsage;
    }

    /**
     * Estimates the number of expected unique start and end tuples of a query.
     * @param query the path query, with all the indices of each label
     * @return expected number of tuples in end result
     */
    abstract public int query(List<Long> query);

    abstract public long getBytesUsed();
}
