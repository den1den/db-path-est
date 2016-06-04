package nl.tue.algorithm.paths;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Lexicographical label ordering: path -> index, index -> path
 */
public class LabelSequence implements Iterable<int[]>{
    private final int LABELS;
    private final int MAX_PATH_LENGT;
    private final int MAX_INDEX;

    /**
     * Assumes the labels go from 0 to labels-1
     *
     * @param labels number of labels
     */
    public LabelSequence(int labels, int maxPathLength) {
        if(labels <= 0){
            throw new IllegalArgumentException("At least 1 label is required");
        }
        if(maxPathLength <= 0){
            throw new IllegalArgumentException("At least a path of length 1 is required");
        }
        double maxIndex = (Math.pow(labels, maxPathLength + 1) - 1) / (labels - 1);
        if(maxIndex > Integer.MAX_VALUE){
            throw new ArithmeticException("labels, maxPathLength combo is to large for an integer");
        }
        this.MAX_INDEX = (int) maxIndex;
        this.LABELS = labels;
        this.MAX_PATH_LENGT = maxPathLength;
    }

    /**
     * Returns the labels via a lexigraphical index
     * Example: a, b, c, aa, ab, ac, ba, ...
     *
     * @param index Lexigraphical index
     * @return the subquery of labels corresponding to a index
     */
    public int[] get(int index) {
        int length = getLength(index);
        int[] result = new int[length];
        int indexFloor = getFloorIndex(length);
        int combinationIndex = index - indexFloor;

        int radix = 1;
        for (int i = length - 1; i >= 0; i--) {
            result[i] = Math.floorDiv(combinationIndex, radix) % LABELS;
            radix *= LABELS;
        }
        return result;
    }

    public int get(int[] path) {
        if (path.length == 0) {
            throw new IllegalArgumentException("Path length cannot be zero");
        }
        if (this.LABELS == 1) {
            return path.length - 1;
        }
        double base = (Math.pow(LABELS, path.length) - LABELS) / (LABELS - 1);

        long index = (long) base;
        long radix = 1;
        for (int i = path.length - 1; i >= 0; i--) {
            int nthLabel = path[i];
            if (nthLabel >= LABELS) {
                throw new IllegalArgumentException("path label out of bounds: " + Arrays.toString(path));
            }
            index += radix * nthLabel;
            radix = Math.multiplyExact(radix, LABELS);
        }
        return Math.toIntExact(index);
    }

    int getLength(int index) {
        if (this.LABELS == 1) {
            return index + 1;
        }
        double base = Math.log(LABELS + index * (LABELS - 1)) / Math.log(LABELS);
        return (int) Math.floor(base);
    }

    int getFloorIndex(int length) {
        return (int) ((Math.pow(LABELS, length) - LABELS) / (LABELS - 1));
    }

    public static Comparator<int[]> lexicalGraphicOrder = new Comparator<int[]>() {
        @Override
        public int compare(int[] o1, int[] o2) {
            int i = o1.length - 1;
            int cmp = 0;
            while (i >= 0) {
                int valA = o1[i];
                int valB = o2[i];
                cmp = Integer.compare(valA, valB);
                if (cmp != 0) {
                    return cmp;
                }
                i--;
            }
            return cmp;
        }
    };

    public int getLabels() {
        return LABELS;
    }

    public int getMaxIndex(int pathLength){
        int[] max = new int[pathLength];
        Arrays.fill(max, LABELS - 1);
        return get(max);
    }

    @Override
    public Iterator<int[]> iterator() {
        return new LSIterator();
    }

    public class LSIterator implements Iterator<int[]> {
        int index = 0;

        public LSIterator() {
        }

        @Override
        public boolean hasNext() {
            return index < MAX_INDEX;
        }

        @Override
        public int[] next() {
            return get(index++);
        }
    }
}
