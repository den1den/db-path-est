package nl.tue.algorithm;

/**
 * Created by dennis on 24-5-16.
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

}
