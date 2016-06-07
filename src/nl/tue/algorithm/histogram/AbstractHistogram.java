package nl.tue.algorithm.histogram;

import nl.tue.MemoryConstrained;
import nl.tue.algorithm.paths.LabelSequence;

import java.util.Arrays;

/**
 * Created by Dennis on 4-6-2016.
 */
public abstract class AbstractHistogram<E> implements MemoryConstrained {

    /**
     * path -> integer
     */
    LabelSequence labelSequence;

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

    public AbstractHistogram(LabelSequence labelSequence, int[] startRanges, E[] estimations) {
        this.labelSequence = labelSequence;
        this.startRanges = startRanges;
        this.estimations = estimations;
    }

    /**
     * Get an estimate from the histogram
     *
     * @param path the given path
     * @return null or the stored estimate
     */
    public E getEstimate(int[] path) {
        int index = labelSequence.get(path);
        int Eindex = Arrays.binarySearch(this.startRanges, index);
        if (Eindex >= 0) {
            Eindex = Eindex + 1;
        } else if (Eindex < -1) {
            Eindex = -Eindex - 2;
        } else {
            Eindex = 0;
        }
        E e = estimations[Eindex];
        return e;
    }

    @Override
    public abstract long getBytesUsed();
}
