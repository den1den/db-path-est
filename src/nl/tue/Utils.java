package nl.tue;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Utils
 */
public class Utils {
    private Utils() {
        assert false;
    }

    public static Set<Integer> toSet(int[] arr) {
        return Arrays.stream(arr).boxed().collect(Collectors.toSet());
    }

    public static List<Integer> toList(int[] ints) {
        List<Integer> queryObj = new ArrayList<>();
        for (int anInt : ints) {
            queryObj.add(anInt);
        }
        return queryObj;
    }

    public static long hashMapBytesUsed(Map map, int keySize, int valueSize) {
        return (long) map.size() * (32 + keySize + valueSize);
    }

    public static List<List<Integer>> toList(int[][] intss) {
        List<List<Integer>> generated2 = new ArrayList<>(intss.length);
        for (int[] ints : intss) {
            generated2.add(toList(ints));
        }
        return generated2;
    }

    public static int[] generate(int LABELS, int maxSize, Random r) {
        int[] ls = new int[r.nextInt(maxSize - 1) + 1];
        for (int i = 0; i < ls.length; i++) {
            ls[i] = r.nextInt(LABELS);
        }
        return ls;
    }

    public static int[] toArray(List<Integer> ints) {
        int[] r = new int[ints.size()];
        for (int i = 0; i < r.length; i++) {
            r[i] = ints.get(i);
        }
        return r;
    }
}
