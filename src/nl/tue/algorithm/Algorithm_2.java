package nl.tue.algorithm;

import nl.tue.Utils;
import nl.tue.algorithm.query.QuerySplitter;

import java.util.*;

/**
 * Dynamic Programming, combining the best results
 */
public class Algorithm_2<E extends Estimation, R extends Estimator<E>> extends Algorithm_1<E, R> {
    public Algorithm_2(R inMemoryEstimator) {
        super(inMemoryEstimator);
    }

    @Override
    int dynamic(int[] query, HashMap<List<Integer>, E> cache) {
        SortedSet<E> best1 = getBestsOfFirstLevel(query, cache);
        return inMemoryEstimator.combineEstimations(new ArrayList<>(best1));
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
            E combined = inMemoryEstimator.concatEstimations(headEstimation, tailEstimation);
            bests.add(combined);
        }while (splitter.hasNext());
        return bests;
    }
}
