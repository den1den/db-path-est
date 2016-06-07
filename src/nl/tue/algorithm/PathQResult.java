package nl.tue.algorithm;

import nl.tue.MemoryConstrained;

/**
 * Created by Dennis on 4-6-2016.
 */
public class PathQResult extends PathResult implements MemoryConstrained {
    final int[] query;

    public PathQResult(int tuples, int[] query) {
        super(tuples);
        this.query = query;
    }

    /**
     * The subject of the estimation
     *
     * @return a path query
     */
    public int[] getQuery() {
        return query;
    }

    @Override
    public long getBytesUsed() {
        return PathResult.BYTES + query.length * Integer.BYTES + Integer.BYTES;
    }
}
