package nl.tue.algorithm.brute;

import nl.tue.algorithm.Estimation;

/**
 * Created by dennis on 2-6-16.
 */
public class PathResult extends Estimation {
    final static int BYTES = Integer.BYTES;

    final int tuples;

    public PathResult(int tuples) {
        this.tuples = tuples;
    }

    @Override
    public double getPrecision() {
        return 1;
    }

    @Override
    public int getTuples() {
        return tuples;
    }
}
