package nl.tue.algorithm;

import nl.tue.algorithm.query.Evaluator;
import nl.tue.io.Parser;

/**
 * Created by dennis on 17-5-16.
 */
public abstract class Algorithm implements Evaluator {

    final Parser input;
    final long maxPathLength;
    final long maxMemoryUsage;


    public Algorithm(Parser p, long maxPathLength, long maxMemoryUsage) {
        this.input = p;
        this.maxPathLength = maxPathLength;
        this.maxMemoryUsage = maxMemoryUsage;
    }

    abstract public long getBytesUsed();
}
