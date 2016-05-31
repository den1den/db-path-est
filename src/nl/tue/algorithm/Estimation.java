package nl.tue.algorithm;

import nl.tue.Utils;

import java.util.List;

/**
 * Created by dennis on 24-5-16.
 */
public abstract class Estimation {
    /**
     *
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
     * If this query is exact the exact (non null) query should be given
     * Note: multiple constraints in Java Generics is not allowed
     *
     * @return exact query of null
     */
    public abstract int[] getQuery();

    /**
     * {@see getQuery}
     * @return hashable query representation
     */
    public List<Integer> getQueryObj(){
        return Utils.toList(getQuery());
    }

}
