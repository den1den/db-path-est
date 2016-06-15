package nl.tue.comparison;

import nl.tue.algorithm.pathindex.PathIndex;

/**
 * Created by Nathan on 5/25/2016.
 */
public class ComparisonResult {

    private final PathIndex index;
    private final int result;
    private final long estimation;
    private final int queryTime;

    public ComparisonResult(PathIndex index, int result, long estimation, int queryTime) {
        this.index = index;
        this.result = result;
        this.estimation = estimation;
        this.queryTime = queryTime;
    }

    public PathIndex getIndex() {
        return index;
    }

    public int getResult() {
        return result;
    }

    public long getEstimation() {
        return estimation;
    }

    public int getQueryTime() {
        return queryTime;
    }

    /**
     * Returns the accuracy of this test case in percentage.
     * @return
     */
    public double getAccuracy() {

        if(this.result == 0 && this.estimation == 0) {
            return 0;
        }

        double result = this.result;
        double estimation = this.estimation;

        double delta = Math.abs(result - estimation);

        return (delta/result)*100;
    }
}
