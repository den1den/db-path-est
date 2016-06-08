package nl.tue.algorithm.paths;

import nl.tue.MemoryConstrained;

/**
 * Created by Dennis on 7-6-2016.
 */
public abstract class PathsOrdering implements Iterable<int[]>, MemoryConstrained {
    protected final int LABELS;
    protected final int MAX_INDEX;

    public PathsOrdering(int labels, int maxPathLength) {
        this.LABELS = labels;
        this.MAX_INDEX = PathsOrdering.maxNumber(labels, maxPathLength);
        if (labels <= 0) {
            throw new IllegalArgumentException("At least 1 label is required");
        }
        if (maxPathLength <= 0) {
            throw new IllegalArgumentException("At least a path of length 1 is required");
        }
    }

    public static int maxNumber(int LABELS, int MAX_PATH_LENGT) {
        double MAX = (Math.pow(LABELS, MAX_PATH_LENGT + 1) - 1) / (LABELS - 1);
        MAX -= 1; // Excluding the root
        if (MAX > Integer.MAX_VALUE) {
            throw new ArithmeticException(String.format("labels, maxPathLength combo is to large for an integer by %.1f%", 100 * MAX / Integer.MAX_VALUE));
        }
        return (int) MAX;
    }

    abstract public int[] get(int index);

    abstract public int get(int[] path);

    abstract public int getMaxIndex();

    public static final int BYTES = Integer.BYTES + Integer.BYTES;

    @Override
    public long getBytesUsed() {
        return BYTES;
    }
}
