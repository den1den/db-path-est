package nl.tue.algorithm.paths;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Some query ordering
 */
public class PathsOrderingLexicographical extends PathsOrdering {

    /**
     * Assumes the labels go from 0 to labels-1
     *
     * @param labels number of labels
     */
    public PathsOrderingLexicographical(int labels, int maxPathLength) {
        super(labels, maxPathLength);
    }

    /**
     * Returns the labels via a lexigraphical index
     * Example: a, b, c, aa, ab, ac, ba, ...
     *
     * @param index Lexigraphical index
     * @return the subquery of labels corresponding to a index
     */
    @Override
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

    @Override
    public int get(int[] path) {
        if (path.length == 0) {
            throw new IllegalArgumentException("StringPath length cannot be zero");
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

    int getFloorIndex(int length) {
        return (int) ((Math.pow(LABELS, length) - LABELS) / (LABELS - 1));
    }

    public int getLabels() {
        return LABELS;
    }

    @Override
    public int getMaxIndex() {
        return MAX_INDEX;
    }

    @Override
    public Iterator<int[]> iterator() {
        return new LSIterator();
    }

    /**
     * Naive
     */
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
