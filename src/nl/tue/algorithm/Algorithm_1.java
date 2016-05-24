package nl.tue.algorithm;

import nl.tue.algorithm.astar.AStart;
import nl.tue.algorithm.query.DPO;
import nl.tue.algorithm.query.Estimator;
import nl.tue.algorithm.query.Optimizer;
import nl.tue.io.Parser;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Algorithm_1 implements Algorithm {

    Optimizer currentOptimizer;

    @Override
    public void buildSummary(Parser p, int k, double b) {
        AStart aStart = new AStart(p.getNLabels(), k);
        AStart.AStartIterator iterator = aStart.iterator();

        int[] next;
        do {
            next = iterator.next();
            double heuristic = process(next);
            aStart.setHeuristic(heuristic);
        } while (getBytesUsed() < b && iterator.hasNext());

        Estimator precissionEvaluator = null; //TODO
        currentOptimizer = new DPO(precissionEvaluator);
    }


    /**
     * Store information of a subQuery in memory
     *
     * @param subQuery
     * @return heuristic value of this subQuery, higher is better
     */
    private double process(int[] subQuery) {
        //TODO
        return 1;
    }

    @Override
    public int query(List<Long> query) {
        if (query.isEmpty()) {
            return 0;
        }
        Iterator<List<Long>> order = currentOptimizer.getExecutionOrder(query).iterator();

        int total = evalSubQuery(order.next());
        while (order.hasNext()) {
            List<Long> joinedSubQuery;
            //TODO: Dennis
        }
        return 0;
    }

    int evalSubQuery(List<Long> subQuery) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public long getBytesUsed() {
        return 0;
    }

    // Depricated?
    Map<QPath, QPathInfo> optimizedGraph;

    private static class QPath {

    }

    private static class QPathInfo {

    }
}
