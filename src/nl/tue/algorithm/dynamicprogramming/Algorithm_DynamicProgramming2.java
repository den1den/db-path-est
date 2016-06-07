package nl.tue.algorithm.dynamicprogramming;

import nl.tue.Utils;
import nl.tue.algorithm.paths.QuerySplitter;

import java.util.*;

/**
 * Created by Dennis on 7-6-2016.
 */
public abstract class Algorithm_DynamicProgramming2<E extends Estimation, R extends DEstimator<E>> extends Algorithm_DynamicProgramming<E, R> {

    @Override
    int dynamic(int[] query, HashMap<List<Integer>, E> cache) {
        SortedSet<E> best1 = getBestsOfFirstLevel(query, cache);
        ArrayList<E> list = new ArrayList<>(best1);
        return inMemory.combineEstimations(list);
    }

    TreeSet<E> getBestsOfFirstLevel(int[] query, HashMap<List<Integer>, E> cache) {
        TreeSet<E> bests = new TreeSet<>();
        // First level is different

        List<Integer> queryObj = Utils.toList(query);
        E cached = cache.get(queryObj);
        if (cached != null) {
            // Stopping criteria
            bests.add(cached);
            return bests;
        }


        if(queryObj.size() <= 1){
            throw new RuntimeException("Cannot split up a query into a smaller query " +
                    "and query estimate is not know exactly. query = " + Arrays.toString(query));
        }
        QuerySplitter splitter = new QuerySplitter(query);

        do{
            int[][] next = splitter.next();
            int[] head = next[0];
            int[] tail = next[1];

            E headEstimation = getBest(head, cache);
            E tailEstimation = getBest(tail, cache);
            E combined = inMemory.concatEstimations(headEstimation, tailEstimation);
            bests.add(combined);
        }while (splitter.hasNext());
        return bests;
    }
}
