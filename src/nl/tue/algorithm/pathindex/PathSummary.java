package nl.tue.algorithm.pathindex;

import nl.tue.Utils;
import nl.tue.algorithm.Estimation;

import java.util.List;

/**
 * Created by Nathan on 5/24/2016.
 */
public class PathSummary extends Estimation {

    public final int joins;
    private final PathIndex index;
    private final Summary summary;
    private final double precision;

    /**
     * Exact estimation
     * @param index
     * @param summary
     */
    public PathSummary(PathIndex index, Summary summary) {
        joins = 0;
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
    public PathSummary(PathIndex index, Summary summary, double precision, int joins) {
        this.joins = joins;
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

    /**
     * If this query is exact the exact (non null) query should be given
     * Note: because multiple constraints in Java Generics is not allowed
     *
     * @return exact query of null
     */
    public int[] getQuery() {
        return index.getPathAsIntArray();
    }

    /**
     * {@see getQuery}
     * @return hashable query representation
     */
    public List<Integer> getQueryObj() {
        return Utils.toList(getQuery());
    }
}
