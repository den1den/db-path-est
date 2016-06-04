package nl.tue.algorithm.brute;

import nl.tue.algorithm.Estimation;

/**
 * Created by dennis on 2-6-16.
 */
public class PathResult extends Estimation {
    final static int BYTES = Integer.BYTES;

    final int tuples;
    final int[] query;

    public PathResult(int tuples, int[] query) {
        this.tuples = tuples;
        this.query = query;
    }

    @Override
    public double getPrecision() {
        return 1;
    }

    @Override
    public int getTuples() {
        return tuples;
    }

    @Override
    public int[] getQuery() {
        return query;
    }
}
