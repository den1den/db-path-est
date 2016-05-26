package nl.tue.algorithm;

import nl.tue.algorithm.pathindex.PathIndex;
import nl.tue.algorithm.query.QueryTree;

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

        public DynamicProgram(int[] query) {
            this.query = query;
            Collection<E> exactEstimations = inMemoryEstimator.retrieveAllExactEstimations();
            cache = new HashMap<>(exactEstimations.size());
            for (E e : exactEstimations) {
                cache.put(new PathIndex(e.getQuery()), e);
            }
        }

        E getBest() {
            return getBest(query);
        }

        E getBest(int[] query) {
            E best = cache.get(new PathIndex(query));
            if (best != null) {
                return best;
            }
            // query is not known, split it in different ways
            int tailIndex = query.length / 2 + query.length % 2; //aaacbbb
            int offset = 0;
            int sign = 1;
            do {
                int[] head = new int[tailIndex + offset];
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

                offset += (offset + 1) * sign;
                sign *= -1;
            } while (tailIndex + offset < query.length);
            return best;
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
