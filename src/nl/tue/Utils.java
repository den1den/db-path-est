package nl.tue;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Dennis on 31-5-2016.
 */
public class Utils {
    private Utils() {
        assert false;
    }

    public static Set<Integer> toSet(int[] arr) {
        return Arrays.stream(arr).boxed().collect(Collectors.toSet());
    }

    public static List<Integer> toList(int[] ints) {
        List<Integer> queryObj = new ArrayList<Integer>();
        for (int index = 0; index < ints.length; index++) {
            queryObj.add(ints[index]);
        }
        return queryObj;
    }

    public static long hashMapBytesUsed(Map map, int keySize, int valueSize) {
        return (long) map.size() * (32 + keySize + valueSize);
    }
}
