package nl.tue.algorithm.histogram;

import nl.tue.MemoryConstrained;
import nl.tue.algorithm.paths.PathsOrdering;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Histogram that stores short values for each path
 */
public class Histogram implements MemoryConstrained {
    /**
     * startRanges[0] = x iff bucket 0 starts at index x
     * ...
     * startRanges[i] = z iff bucket i starts at index z
     */
    private int[] startRanges;

    /**
     * estimations[0] = Estimation of bucket x
     * ...
     * estimations[i] = Estimation of bucket startRanges[i]
     */
    private short[] estimations;

    /**
     * Length of the buckets
     * estimationLengths[0] = Size of bucket 0 s.t. bucket = (x, x + estimations[0] - 1)
     * ...
     * estimationLengths[i] = Size of bucket i s.t. bucket = (startRanges[i], startRanges[i] + estimations[i] - 1)
     */
    private short[] estimationLengths;

    public Histogram(int[] startRanges, short[] estimations, short[] estimationLengths) {
        this.startRanges = startRanges;
        this.estimations = estimations;
        this.estimationLengths = estimationLengths;
        for (int i = 0; i < estimationLengths.length; i++) {
            assert estimationLengths[i] >= 1;
        }
    }

    /**
     * Get a stored estimate
     * @param queryIndex the index of the path
     * @return estimate or null when it is not in memory
     */
    public Short getEstimate(int queryIndex) {
        int index = Arrays.binarySearch(startRanges, queryIndex);
        if (index >= 0) {
            // Hit exact starting range
            return estimations[index];
        } else if (index < -1) {
            // Hit past a range
            int bucketCandidate = -index - 2;
            int bucketCandidateStart = startRanges[bucketCandidate];
            int bucketCandidateEnd = bucketCandidateStart + estimationLengths[bucketCandidate];
            if(queryIndex <= bucketCandidateEnd){
                return estimations[bucketCandidate];
            } else {
                return null;
            }
        } else {
            // Hit before first range
            return null;
        }
    }

    @Override
    public long getBytesUsed() {
        return startRanges.length * Integer.BYTES + Integer.BYTES
                + estimations.length * Short.BYTES + Integer.BYTES
                + estimationLengths.length * Short.BYTES + Integer.BYTES;
    }

    public int calcSize() {
        int total = 0;
        for (int i = 0; i < estimationLengths.length; i++) {
            total += estimationLengths[i];
        }
        return total;
    }

    public class HistogramEntry{
        int index;
        short value;

        public HistogramEntry(int index, short value) {
            this.index = index;
            this.value = value;
        }
    }

    public String toCSVTable(PathsOrdering ordering){
        StringBuilder sb = new StringBuilder();
        sb.append("1st-query; 1st-index; bucket; bucket-size; estimations").append(System.lineSeparator());
        for (int i = 0; i < this.estimations.length; i++) {
            int e = this.startRanges[i];
            sb.append(Arrays.toString(ordering.get(e))).append(';') //1st-query
                    .append(startRanges[i] + e).append(';') //index
                    .append(i).append(';') //bucket
                    .append(estimationLengths[i]).append(';') //bucket-size
                    .append(estimations[i]).append(System.lineSeparator()); //estimation
        }
        return sb.toString();
    }

    public String toCSVTableExplcicitly(PathsOrdering ordering){
        StringBuilder sb = new StringBuilder();
        sb.append("query; index; bucket; estimations").append(System.lineSeparator());
        for (int i = 0; i < this.estimations.length; i++) {
            for (int e = 0; e < estimationLengths[i]; e++) {
                sb.append(Arrays.toString(ordering.get(e))).append(';') //query
                        .append(startRanges[i] + e).append(';') //index
                        .append(i).append(';') //bucket
                        .append(estimations[i]).append(System.lineSeparator()); //estimation
            }
        }
        return sb.toString();
    }
}
