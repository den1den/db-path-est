package nl.tue.algorithm.dynamicprogramming;

import nl.tue.Utils;
import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.paths.QuerySplitter;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dennis on 7-6-2016.
 */
public abstract class Algorithm_DynamicProgramming<E extends Estimation, R extends DEstimator<E>> extends Algorithm<R> {

    @Override
    public int query(int[] query) {
        Collection<E> exactEstimations = this.inMemory.retrieveAllExactEstimations();
        HashMap<List<Integer>, E> cache = new HashMap<>(exactEstimations.size());
        for (E e : exactEstimations) {
            assert e.getPrecision() == Double.MAX_VALUE;
            List<Integer> queryObj = null;// e.getQueryObj();
            cache.put(queryObj, e);
        }
        return dynamic(query, cache);
    }

    int dynamic(int[] query, HashMap<List<Integer>, E> cache){
        E best = getBest(query, cache);
        return best.getTuples();
    }

    /**
     * This recursive call does not seem to terminate when subpaths need to be joined.
     * @param query TODO: Could be a single object with range instead of int[]?
     */
    protected E getBest(int[] query, HashMap<List<Integer>, E> cache) {
        List<Integer> queryObj = Utils.toList(query);
        E best = cache.get(queryObj);
        if (best != null) {
            // Stopping criteria
            return best;
        }

        if(queryObj.size() <= 1){
            // Cannot split further
            throw new RuntimeException("Cannot split up a query into a smaller query " +
                    "and query estimate is not know exactly. query = " + Arrays.toString(query));
            // Could use guesstimate here?
        }
        QuerySplitter splitter = new QuerySplitter(query);

        do{
            int[][] next = splitter.next();
            int[] head = next[0];
            int[] tail = next[1];

            E headEstimation = getBest(head, cache);
            E tailEstimation = getBest(tail, cache);
            E combined = inMemory.concatEstimations(headEstimation, tailEstimation);
           /* if (best == null || combined.compareTo(best) > 0) {
                // Maximizing value
                best = combined;
            }*/
        }while (splitter.hasNext());

        return best;
    }

    @Override
    protected long bytesOverhead() {
        return 0;
    }
}