package nl.tue.comparison;

import nl.tue.algorithm.pathindex.PathIndex;

/**
 * Created by Nathan on 5/25/2016.
 */
public class ComparisonResult {

    private final PathIndex index;
    private final int result;
    private final int estimation;

    public ComparisonResult(PathIndex index, int result, int estimation) {
        this.index = index;
        this.result = result;
        this.estimation = estimation;
    }

    public PathIndex getIndex() {
        return index;
    }

    public int getResult() {
        return result;
    }

    public int getEstimation() {
        return estimation;
    }

    /**
     * Returns the accuracy of this test case in percentage.
     * @return
     */
    public double getAccuracy() {
        double result = this.result;
        double estimation = this.estimation;

        double delta = Math.abs(result - estimation);

        return (delta/result)*100;
    }
}
