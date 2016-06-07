package nl.tue.algorithm.pathsummeryalgorithms;

/**
 * Partitioned StringPath Summery
 * Created by dennis on 2-6-16.
 */
public class PPathStat extends PathStat {
    public static final int BYTES = PathStat.BYTES + Integer.BYTES;

    final int partition;

    public PPathStat(int tuples, int dStartTuples, int dEndTuples, int partition) {
        super(tuples, dStartTuples, dEndTuples);
        this.partition = partition;
    }
}
