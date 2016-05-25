package nl.tue.algorithm;

/**
 * Created by dennis on 24-5-16.
 */
public interface Estimation {
    /**
     * An notion of precision.
     *
     * @return The higher the better, with Double.MAX_VALUE being exact
     */
    double getPrecision();

    /**
     * The estimate itself
     * @return number of tuples for this estimation
     */
    int getTuples();

    /**
     * If this query is exact the exact (non null) query should be given
     * Note: multiple constraints in Java Generics is not allowed
     *
     * @return exact query of null
     */
    int[] getQuery();
}
