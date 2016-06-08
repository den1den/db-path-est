package nl.tue.algorithm.histogram;

import nl.tue.MemoryConstrained;
import nl.tue.algorithm.paths.PathsOrdering;

import java.util.Arrays;

/**
 * Created by Dennis on 4-6-2016.
 */
public abstract class AbstractHistogram<E> implements MemoryConstrained {
    /**
     * 0: x
     * 1: y
     * ...
     */
    int[] startRanges;
    /**
     * Estimation of 0 to x-1
     * Estimation of x to y-1
     * Estimation of y to ...
     */
    E[] estimations;

    public AbstractHistogram(int[] startRanges, E[] estimations) {
        this.startRanges = startRanges;
        this.estimations = estimations;
    }

    /**
     * Get a stored estimate
     * @param index the index of the path
     * @return estimate
     */
    public E getEstimate(int index) {
        int EIndex = getEIndex(index, this.startRanges);
        return estimations[EIndex];
    }

    private static int getEIndex(int index, int[] startRanges) {
        int Eindex = Arrays.binarySearch(startRanges, index);
        if (Eindex >= 0) {
            Eindex = Eindex + 1;
        } else if (Eindex < -1) {
            Eindex = -Eindex - 2;
        } else {
            Eindex = 0;
        }
        return Eindex;
    }

    @Override
    public abstract long getBytesUsed();

    /**
     * AbstractHistogram<Short>
     */
    public static class Short implements MemoryConstrained{
        private int[] startRanges;
        private short[] estimations;

        public Short(int[] startRanges, short[] estimations) {
            this.startRanges = startRanges;
            this.estimations = estimations;
        }
        /**
         * Get a stored estimate
         * @param index the index of the path
         * @return estimate
         */
        public short getEstimate(int index) {
            int EIndex = getEIndex(index, this.startRanges);
            return estimations[EIndex];
        }

        @Override
        public long getBytesUsed() {
            return startRanges.length * Integer.BYTES + Integer.BYTES
                    + estimations.length * Integer.BYTES + Integer.BYTES;
        }
    }
}
