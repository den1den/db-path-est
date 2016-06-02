package nl.tue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Dennis on 31-5-2016.
 */
public class Utils {
    private Utils(){ assert false; }

    public static Set<Integer> toSet(int[] arr){
        return Arrays.stream(arr).boxed().collect(Collectors.toSet());
    }

    public static List<Integer> toList(int[] query) {
        List<Integer> queryObj = new ArrayList<Integer>();
        for (int index = 0; index < query.length; index++)
        {
            queryObj.add(query[index]);
        }
        return queryObj;
    }
}
