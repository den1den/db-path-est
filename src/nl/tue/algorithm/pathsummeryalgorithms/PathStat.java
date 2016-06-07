package nl.tue.algorithm.pathsummeryalgorithms;

/**
 * Created by dennis on 2-6-16.
 */
public class PathStat extends PathResult {
    public final static int BYTES = PathResult.BYTES + 2 * Integer.BYTES;

    final int dStartTuples;
    final int dEndTuples;

    public PathStat(int tuples, int dStartTuples, int dEndTuples) {
        super(tuples);
        this.dStartTuples = dStartTuples;
        this.dEndTuples = dEndTuples;
    }
}
