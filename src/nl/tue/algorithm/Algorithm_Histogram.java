package nl.tue.algorithm;

import nl.tue.algorithm.histogram.BruteHistogram;
import nl.tue.algorithm.histogram.Joiner;
import nl.tue.algorithm.paths.PathsOrdering;
import nl.tue.algorithm.paths.PathsOrderingLexicographical;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;

import java.util.Iterator;

/**
 * @author dennis
 */
public class Algorithm_Histogram extends Algorithm<BruteHistogram> {
    PathsOrdering pathsOrdering;
    @Override
    protected BruteHistogram build(Parser p, int maximalPathLength, long budget) {
        int labels = p.getNLabels();
        pathsOrdering = new PathsOrderingLexicographical(labels, maximalPathLength);
        Joiner.AbstractJoiner<PathResult> joiner = new BasicJoiner();
        BruteHistogram.BruteHistogramBuilder builder = new BruteHistogram.BruteHistogramBuilder(joiner);

        AdjacencyList graph = new AdjacencyList(p);
        return builder.build(graph, pathsOrdering);
    }

    @Override
    public int query(int[] query) {
        int i = pathsOrdering.get(query);
        return inMemory.getEstimate(i).tuples;
    }

    @Override
    protected long bytesOverhead() {
        return pathsOrdering.getBytesUsed();
    }

    private class BasicJoiner extends Joiner.AbstractJoiner<PathResult> {
        @Override
        public PathResult join(PathResult estimate) {
            throw new UnsupportedOperationException();
        }
    }
}
