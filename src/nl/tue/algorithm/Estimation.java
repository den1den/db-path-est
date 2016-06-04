package nl.tue.algorithm;

/**
 * Estimation class used to provide an estimation to the dynamic programming approach
 */
public abstract class Estimation {

    /**
     * An notion of precision.
     *
     * @return The higher the better, with Double.MAX_VALUE being exact
     */
    public abstract double getPrecision();

    /**
     * The estimate itself
     * @return number of tuples for this estimation
     */
    public abstract int getTuples();

    /**
     * The subject of the estimation
     * @return a path query
     */
    public abstract int[] getQuery();
}
