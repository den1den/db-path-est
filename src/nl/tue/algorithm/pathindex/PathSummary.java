package nl.tue.algorithm.pathindex;

import nl.tue.Utils;
import nl.tue.algorithm.dynamicprogramming.Estimation;

import java.util.List;

/**
 * Created by Nathan on 5/24/2016.
 */
public class PathSummary implements Estimation {

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

    /**
     * An notion of precision.
     *
     * @return The higher the better, with Double.MAX_VALUE being exact
     */
    public double getPrecision() {
        return Double.MAX_VALUE;
    }

    /**
     * The estimate itself
     * @return number of tuples for this estimation
     */
    public int getTuples() {
        return summary.getTuples();
    }

    /**
         * The subject of the estimation
     * @return a path query
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
