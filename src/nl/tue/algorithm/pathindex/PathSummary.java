package nl.tue.algorithm.pathindex;

import nl.tue.algorithm.Estimation;

/**
 * Created by Nathan on 5/24/2016.
 */
public class PathSummary extends Estimation {

    private final PathIndex index;
    private final Summary summary;
    private final double precision;

    /**
     * Exact estimation
     * @param index
     * @param summary
     */
    public PathSummary(PathIndex index, Summary summary) {
        super(0);
        this.index = index;
        this.summary = summary;
        precision = Double.MAX_VALUE;
    }

    /**
     * Combined estimation
     * @param index
     * @param summary
     * @param precision
     */
    public PathSummary(PathIndex index, Summary summary, double precision, int JOINS) {
        super(JOINS);
        this.index = index; // Not needed?
        this.summary = summary;
        this.precision = precision;
    }

    public PathIndex getIndex() {
        return index;
    }

    public Summary getSummary() {
        return summary;
    }

    @Override
    public double getPrecision() {
        return Double.MAX_VALUE;
    }

    @Override
    public int getTuples() {
        return summary.getTuples();
    }

    @Override
    public int[] getQuery() {
        return index.getPathAsIntArray();
    }
}
