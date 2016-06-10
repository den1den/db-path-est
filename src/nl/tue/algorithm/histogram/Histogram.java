package nl.tue.algorithm.histogram;

import nl.tue.MemoryConstrained;

import java.util.Arrays;

/**
 * Histogram that stores short values for each path
 */
public class Histogram implements MemoryConstrained {
    /**
     * startRanges[0] = x
     * startRanges[1] = y
     * ...
     */
    private int[] startRanges;

    /**
     * estimations[0] = Estimation of bucket x to x + estimationLengths[0]
     * estimations[0] = Estimation of bucket y to y + estimationLengths[1]
     * ...
     * estimations[i] = Estimation of bucket startRanges[i] to startRanges[i] + estimationLengths[i]
     */
    private short[] estimations;

    /**
     * Length of the buckets
     */
    private short[] estimationLengths;

    public Histogram(int[] startRanges, short[] estimations, short[] estimationLengths) {
        this.startRanges = startRanges;
        this.estimations = estimations;
        this.estimationLengths = estimationLengths;
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
            int lowRange = -index - 2;
            int start = startRanges[lowRange];
            int end = start + estimationLengths[lowRange];
            if(queryIndex <= end){
                return estimations[lowRange];
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
                + estimations.length * Integer.BYTES + Integer.BYTES;
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
}
