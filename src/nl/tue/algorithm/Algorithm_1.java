package nl.tue.algorithm;

import nl.tue.algorithm.astar.AStart;
import nl.tue.io.Parser;

import java.util.List;
import java.util.Map;

/**
 * Created by dennis on 19-5-16.
 */
public class Algorithm_1 extends Algorithm {

    public Algorithm_1(Parser p, long maxPathLength, long maxMemoryUsage) {
        super(p, maxPathLength, maxMemoryUsage);

        AStart aStart = new AStart(p.getNLabels(), (int) maxPathLength);
        AStart.AStartIterator iterator = aStart.iterator();

        int[] next;
        do {
            next = iterator.next();
            double heuristic = process(next);
            aStart.setHeuristic(heuristic);
        } while (getBytesUsed() < maxMemoryUsage && iterator.hasNext());
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
        //TODO
        return 0;
    }

    @Override
    public long getBytesUsed() {
        return 0;
    }

    Map<QPath, QPathInfo> optimizedGraph;

    private static class QPath {

    }

    private static class QPathInfo {

    }
}
