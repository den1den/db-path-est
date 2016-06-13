package nl.tue.algorithm.dynamicprogramming;

import nl.tue.Utils;
import nl.tue.algorithm.paths.PathSplitter;

import java.util.*;

/**
 * Dynamic programming search
 * @param <E> Estimation input
 */
public class DynamicProgrammingSearch<E> {

    protected DCombiner<E> combiner;
    protected DInput<E> exact;

    public DynamicProgrammingSearch(DCombiner<E> combiner, DInput<E> exact) {
        this.combiner = combiner;
        this.exact = exact;
    }

    Map<List<Integer>, E> calculatedCache = new HashMap<>();

    public E query(int[] query) {
        return getBest(query);
    }

    protected E getPreCalculated(int[] query){
        List<Integer> queryObj = Utils.toList(query);

        E best = calculatedCache.get(queryObj);
        if (best != null) {
            return best;
        }
        return exact.getStored(query);
    }

    /**
     * This recursive call does not seem to terminate when subpaths need to be joined.
     *
     * @param query TODO: Could be a single object with range instead of int[]?
     */
    protected E getBest(int[] query) {
        E best = getPreCalculated(query);
        if(best != null){
            // Stopping criteria
            return best;
        }

        if (query.length <= 1) {
            // Cannot split further
            throw new RuntimeException("Cannot split up a query into a smaller query " +
                    "and query estimate is not know exactly. query = " + Arrays.toString(query));
            // Could use guesstimate here?
        }
        PathSplitter splitter = new PathSplitter(query);

        do {
            int[][] next = splitter.next();
            int[] head = next[0];
            int[] tail = next[1];

            E headEstimation = getBest(head);
            E tailEstimation = getBest(tail);
            E combined = combiner.concatEstimations(headEstimation, tailEstimation);
            if (best == null || combiner.compare(combined, best) > 0) {
                // Maximizing value
                best = combined;
            }
        } while (splitter.hasNext());

        return best;
    }

    /**
     *
     */
    public static class DP2<E> extends DynamicProgrammingSearch<E> {

        public DP2(DCombiner<E> combiner, DInput<E> exact) {
            super(combiner, exact);
        }

        public List<E> queryFirstLevelList(int[] query){
            return new ArrayList<>(queryFirstLevel(query));
        }

        public TreeSet<E> queryFirstLevel(int[] query) {
            TreeSet<E> bests = new TreeSet<>();
            // First level is different

            E cached = getPreCalculated(query);
            if(cached != null){
                // Stopping criteria
                bests.add(cached);
                return bests;
            }


            if (query.length <= 1) {
                throw new RuntimeException("Cannot split up a query into a smaller query " +
                        "and query estimate is not know exactly. query = " + Arrays.toString(query));
            }
            PathSplitter splitter = new PathSplitter(query);

            do {
                int[][] next = splitter.next();
                int[] head = next[0];
                int[] tail = next[1];

                E headEstimation = getBest(head);
                E tailEstimation = getBest(tail);
                E combined = combiner.concatEstimations(headEstimation, tailEstimation);
                bests.add(combined);
            } while (splitter.hasNext());
            return bests;
        }
    }
}
