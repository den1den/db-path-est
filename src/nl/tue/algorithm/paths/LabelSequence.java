package nl.tue.algorithm.paths;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Lexicographical label ordering: path -> index, index -> path
 */
public class LabelSequence {
    private final int N_l;

    /**
     * Assumes the labels go from 0 to labels-1
     *
     * @param labels number of labels
     */
    public LabelSequence(int labels) {
        this.N_l = labels;
        assert N_l > 0;
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
            result[i] = Math.floorDiv(combinationIndex, radix) % N_l;
            radix *= N_l;
        }
        return result;
    }

    public int get(int[] path) {
        if (path.length == 0) {
            throw new IllegalArgumentException("Path length cannot be zero");
        }
        if (this.N_l == 1) {
            return path.length - 1;
        }
        double base = (Math.pow(N_l, path.length) - N_l) / (N_l - 1);

        long index = (long) base;
        long radix = 1;
        for (int i = path.length - 1; i >= 0; i--) {
            int nthLabel = path[i];
            if (nthLabel >= N_l) {
                throw new IllegalArgumentException("path label out of bounds: " + Arrays.toString(path));
            }
            index += radix * nthLabel;
            radix = Math.multiplyExact(radix, N_l);
        }
        return Math.toIntExact(index);
    }

    int getLength(int index) {
        if (this.N_l == 1) {
            return index + 1;
        }
        double base = Math.log(N_l + index * (N_l - 1)) / Math.log(N_l);
        return (int) Math.floor(base);
    }

    int getFloorIndex(int length) {
        return (int) ((Math.pow(N_l, length) - N_l) / (N_l - 1));
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
        return N_l;
    }
}
