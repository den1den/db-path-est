package nl.tue.algorithm;

import nl.tue.algorithm.astar.AStart;
import nl.tue.algorithm.histogram.BruteHistogram;
import nl.tue.algorithm.histogram.Joiner;
import nl.tue.algorithm.paths.LabelSequence;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;
import nl.tue.io.graph.DirectedBackEdgeGraph;

/**
 * @author dennis
 */
public class Algorithm_Histogram extends Algorithm<BruteHistogram> implements Joiner<PathResult> {

    @Override
    protected BruteHistogram build(Parser p, int maximalPathLength, long budget) {
        BruteHistogram.BruteHistogramBuilder builder = new BruteHistogram.BruteHistogramBuilder();
        int labels = p.getNLabels();
        AStart aStar = new AStart(labels, maximalPathLength);
        LabelSequence labelSequence = new LabelSequence(labels, maximalPathLength);
        int[] indexedResults = new int[labelSequence.getMaxIndex()];

        DirectedBackEdgeGraph graph = new AdjacencyList(p);

        return builder.build(labelSequence, graph, this, aStar.simpleIterator());
    }

    @Override
    public int query(int[] query) {
        return inMemory.getEstimate(query).tuples;
    }

    @Override
    protected long bytesOverhead() {
        return 0;
    }

    @Override
    public PathResult joinLeft(PathResult subject, PathResult to) {
        return joinRight(to, subject);
    }

    @Override
    public PathResult joinRight(PathResult to, PathResult subject) {
        if (Math.abs(to.tuples - subject.tuples) < 10) {
            return new PathResult((to.tuples + subject.tuples) / 2);
        }
        return null;
    }

    @Override
    public PathResult join(PathResult left, PathResult subject, PathResult right) {
        PathResult r = joinRight(left, subject);
        PathResult l = joinLeft(left, subject);
        if (r == null || l == null) {
            return null;
        }
        return new PathResult((r.tuples + l.tuples) / 2);
    }
}
