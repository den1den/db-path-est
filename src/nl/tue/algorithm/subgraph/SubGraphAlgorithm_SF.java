package nl.tue.algorithm.subgraph;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.Estimator;
import nl.tue.algorithm.histogram.AbstractHistogram;
import nl.tue.algorithm.histogram.AbstractHistogramBuilder;
import nl.tue.algorithm.histogram.Joiner;
import nl.tue.algorithm.paths.PathsOrdering;
import nl.tue.algorithm.paths.PathsOrderingLexicographical;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;
import nl.tue.io.graph.DirectedBackEdgeGraph;

/**
 * Subgraph algorithm, with specific factors
 * Created by Dennis on 8-6-2016.
 */
public class SubGraphAlgorithm_SF extends Algorithm<AbstractHistogram.Short> {
    PathsOrdering pathsOrdering;
    /**
     * Stored the percentages
     * @param p
     * @param maximalPathLength
     * @param budget
     * @return
     */
    @Override
    protected AbstractHistogram.Short build(Parser p, int maximalPathLength, long budget) {
        int labels = p.getNLabels();
        pathsOrdering = new PathsOrderingLexicographical(labels, maximalPathLength);
        Joiner.SingleNumberJoiner joiner = new Joiner.Basic();
        AbstractHistogramBuilder.Short builder = new AbstractHistogramBuilder.Short(joiner);

        DirectedBackEdgeGraph graph = new AdjacencyList(p);
        Estimator<Double> graph2 = Estimator.map(graph);
        AbstractHistogram.Short inMemory = builder.build(graph2, pathsOrdering);
        return inMemory;
    }

    @Override
    public int query(int[] query) {
        throw new UnsupportedOperationException("TODO, add dynamic pgorammins class");
    }

    @Override
    protected long bytesOverhead() {
        return pathsOrdering.BYTES;
    }
}
