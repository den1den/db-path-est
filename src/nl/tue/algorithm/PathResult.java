package nl.tue.algorithm;

/**
 * Created by dennis on 2-6-16.
 */
public class PathResult {
    final public static int BYTES = Integer.BYTES;

    final int tuples;

    public PathResult(int tuples) {
        this.tuples = tuples;
    }

    /**
         * An notion of precision.
         *
         * @return The higher the better, with Double.MAX_VALUE being exact
         */
    public double getPrecision() {
        return 1;
    }

    /**
         * The estimate itself
         * @return number of tuples for this estimation
         */
    public int getTuples() {
        return tuples;
    }

}
