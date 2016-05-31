package nl.tue.algorithm;

import nl.tue.Utils;
import nl.tue.algorithm.pathindex.PathIndex;
import nl.tue.algorithm.pathindex.PathSummary;
import nl.tue.algorithm.query.QuerySplitter;

import java.util.*;

/**
 * Unpacks all in memory estimations and uses DynamicProgramming
 * @param <E> Single estimation class
 * @param <R> The class responsible for the estimation itself
 */
public class Algorithm_1<E extends Estimation, R extends Estimator<E>> extends Algorithm<E, R> {

    public Algorithm_1(R estimator) {
        super(estimator);
    }

    @Override
    protected int executeQuery(int[] query) {
        Collection<E> exactEstimations = super.inMemoryEstimator.retrieveAllExactEstimations();
        DynamicProgram dynamicProgram = new DynamicProgram(query, exactEstimations);
        E best = dynamicProgram.getBest();
        return best.getTuples();
    }

    /**
     * Split the query up using Dynamic Programming
     */
    class DynamicProgram {
        final int[] query;
        HashMap<List<Integer>, E> cache;

        /**
         * Dynamic programming approach
         * @param query the input
         * @param baseEstimations the smallest possible way to split up the query, asserts full precision
         */
        DynamicProgram(int[] query, Collection<E> baseEstimations) {
            this.query = query;
            cache = new HashMap<>(baseEstimations.size());
            for (E e : baseEstimations) {
                assert e.getPrecision() == Double.MAX_VALUE;
                List<Integer> queryObj = e.getQueryObj();
                cache.put(queryObj, e);
            }
        }

        E getBest() {
            return getBest(query);
        }

        /**
         * This recursive call does not seem to terminate when subpaths need to be joined.
         * @param query TODO: Could be a faster object
         */
        E getBest(int[] query) {
            List<Integer> queryObj = Utils.toList(query);
            E best = cache.get(queryObj);
            if (best != null) {
                return best;
            }
            if(query.length <= 1){
                throw new RuntimeException("Cannot split up a query into a smaller query " +
                        "and query estimate is not know exactly. query = " + Arrays.toString(query));
                // Could use guesstimate here?
            }
            // query is not known, split it in different ways
            QuerySplitter splitter = new QuerySplitter(query);
            do{
                int[][] next = splitter.next();
                int[] head = next[0];
                int[] tail = next[1];

                E headEstimation = getBest(head);
                E tailEstimation = getBest(tail);
                E combined = inMemoryEstimator.combineEstimations(headEstimation, tailEstimation);
                if (best == null) {
                    best = combined;
                } else if (combined.getPrecision() > best.getPrecision()) {
                    best = combined;
                }
            }while (splitter.hasNext());
            return best;
        }
    }
}
