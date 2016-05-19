package nl.tue.algorithm;

import nl.tue.io.Parser;

import java.util.List;
import java.util.Map;

/**
 * Created by dennis on 19-5-16.
 */
public class Algorithm_1 extends Algorithm {

    public Algorithm_1(Parser p, long maxPathLength, long maxMemoryUsage) {
        super(p, maxPathLength, maxMemoryUsage);
        // TOOD: Construct OG
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

    public class OptimizedGraph {
        Map<QPath, QPathInfo> map;

        public OptimizedGraph(Map<QPath, QPathInfo> map) {
            this.map = map;
        }
    }

    private static class QPath {

    }

    private static class QPathInfo {

    }
}
