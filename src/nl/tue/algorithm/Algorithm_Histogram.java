package nl.tue.algorithm;

import nl.tue.algorithm.astar.AStart;
import nl.tue.algorithm.histogram.BruteHistogram;
import nl.tue.algorithm.histogram.Joiner;
import nl.tue.algorithm.paths.PathsOrdering;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;
import nl.tue.io.graph.DirectedBackEdgeGraph;

/**
 * @author dennis
 */
public class Algorithm_Histogram extends Algorithm<BruteHistogram> {

    private Joiner<PathResult> joiner = new BasicJoiner();

    @Override
    protected BruteHistogram build(Parser p, int maximalPathLength, long budget) {
        BruteHistogram.BruteHistogramBuilder builder = new BruteHistogram.BruteHistogramBuilder();
        int labels = p.getNLabels();
        AStart aStar = new AStart(labels, maximalPathLength);
        PathsOrdering pathsOrdering = new PathsOrdering(labels, maximalPathLength);
        int[] indexedResults = new int[pathsOrdering.getMaxIndex()];

        DirectedBackEdgeGraph graph = new AdjacencyList(p);

        return builder.build(pathsOrdering, graph, joiner, aStar.simpleIterator());
    }

    @Override
    public int query(int[] query) {
        return inMemory.getEstimate(query).tuples;
    }

    @Override
    protected long bytesOverhead() {
        return 0;
    }

    private class BasicJoiner extends Joiner<PathResult> {
        @Override
        public PathResult calcJoin(int leftTuples, PathResult leftEstimate, PathResult newEstimate, int rightTuples, PathResult rightEstimate) {
            joinLeft = false;
            joinRight = false;
            if(leftEstimate != null){
                joinLeft = Math.abs(leftEstimate.tuples - newEstimate.tuples) < 10;
            }
            if(rightEstimate != null){
                joinRight = Math.abs(rightEstimate.tuples - newEstimate.tuples) < 10;
            }
            long tuples = newEstimate.tuples;
            double result;
            if(joinLeft && joinRight){
                result = (leftEstimate.tuples + tuples + rightEstimate.tuples) / (leftTuples + 1 + rightTuples);
            } else if (joinLeft){
                result = (leftEstimate.tuples + tuples) / (leftTuples + 1);
            } else if (joinRight){
                result = (rightEstimate.tuples + tuples) / (rightTuples + 1);
            } else {
                result = tuples;
            }
            return new PathResult((int) result);
        }
    }
}
