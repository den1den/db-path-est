package nl.tue.algorithm;

import nl.tue.algorithm.pathindex.PathIndex;
import nl.tue.algorithm.query.QueryTree;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 * Split the query up using Dynamic Programming
 */
public class Algorithm_1<E extends Estimation, R extends Estimator<E>> extends Algorithm<E, R> {

    public Algorithm_1(R estimator) {
        super(estimator);
    }

    @Override
    protected int executeQuery(int[] query) {
        DynamicProgram dynamicProgram = new DynamicProgram(query);
        E best = dynamicProgram.getBest();
        return best.getTuples();
    }

    class DynamicProgram {
        final int[] query;
        HashMap<PathIndex, E> cache; // start and end node -> D

        DynamicProgram(int[] query) {
            this.query = query;
        }

        E getBest() {
            Collection<E> exactEstimations = inMemoryEstimator.retrieveAllExactEstimations();
            cache = new HashMap<>(exactEstimations.size());
            for (E e : exactEstimations) {
                cache.put(new PathIndex(e.getQuery()), e);
            }
            return getBest(query);
        }

        /**
         * This recursive call does not seem to terminate when subpaths need to be joined.
         * @param query
         * @return
         */
        E getBest(int[] query) {
            E best = cache.get(new PathIndex(query));
            if (best != null) {
                return best;
            }
            // query is not known, split it in different ways
            int index = 0;
            while (canSplit(index)) {
                int splitIndex = getSplitIndex(index);

                int[] head = new int[splitIndex];
                System.arraycopy(query, 0, head, 0, head.length);
                int[] tail = new int[query.length - head.length];
                System.arraycopy(query, head.length, tail, 0, tail.length);

                // head and tail
                E headEstimation = getBest(head);
                E tailEstimation = getBest(tail);
                E combined = inMemoryEstimator.combineEstimations(headEstimation, tailEstimation);
                if (best == null) {
                    best = combined;
                } else if (combined.getPrecision() > best.getPrecision()) {
                    best = combined;
                }
            }
            if (best == null) {
                throw new RuntimeException("Cannot split up a query into a smaller query, " +
                        "and query estimate is not know. Subject: " + Arrays.toString(query));
            }
            return best;
        }

        int getSplitIndex(int index) {
            final int HALF = query.length / 2 + query.length % 2;
            if (index == 0) {
                return HALF;
            }
            int offset;
            if (index % 2 == 0) {
                if (query.length % 2 == 0) {
                    offset = -(index + 1) / 2;
                } else {
                    offset = (index + 1) / 2;
                }
            } else {
                if (query.length % 2 == 0) {
                    offset = (index + 1) / 2;
                } else {
                    offset = -(index + 1) / 2;
                }
            }
            return HALF + offset;
        }

        boolean canSplit(int index) {
            return index < query.length;
        }
    }

    @Deprecated
    private class D {
        E estimation;
        QueryTree plan;

        D(E estimation, QueryTree plan) {
            this.estimation = estimation;
            this.plan = plan;
        }

        D(E estimation, int[] exactQuery) {
            this.estimation = estimation;
            this.plan = new QueryTree.Leaf(exactQuery);
        }

        D combine(D right) {
            E cEstimation = inMemoryEstimator.combineEstimations(estimation, right.estimation);
            QueryTree cPlan = new QueryTree(plan, right.plan);
            return new D(cEstimation, cPlan);
        }
    }
}
