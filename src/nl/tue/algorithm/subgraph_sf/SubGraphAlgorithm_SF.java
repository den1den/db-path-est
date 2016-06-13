package nl.tue.algorithm.subgraph_sf;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.dynamicprogramming.DCombiner;
import nl.tue.algorithm.dynamicprogramming.DInput;
import nl.tue.algorithm.dynamicprogramming.DynamicProgrammingSearch;
import nl.tue.algorithm.histogram.AbstractHistogramBuilder;
import nl.tue.algorithm.histogram.HistogramOfShorts;
import nl.tue.algorithm.histogram.JoinResult;
import nl.tue.algorithm.histogram.Joiner;
import nl.tue.algorithm.paths.PathsOrdering;
import nl.tue.algorithm.paths.PathsOrderingLexicographical;
import nl.tue.algorithm.subgraph.estimator.SubgraphEstimator;
import nl.tue.io.Parser;
import nl.tue.io.TupleList;
import nl.tue.io.graph.AdjacencyList;

import java.util.Iterator;
import java.util.List;

/**
 * Subgraph algorithm, with specific factors
 * Created by Dennis on 8-6-2016.
 */
public class SubGraphAlgorithm_SF extends Algorithm implements DCombiner<Short>, DInput<Short> {
    HistogramOfShorts histogram;
    SubgraphEstimator subgraph;
    PathsOrdering pathsOrdering;
    Joiner<Double, JoinResult.NumberJoinResult> joiner;
    int NODES;

    /**
     * Number of seconds to attempt histogram building
     */
    public static int STOPPING_TIME = 10;

    public SubGraphAlgorithm_SF(Joiner<Double, JoinResult.NumberJoinResult> joiner) {
        this.joiner = joiner;
    }

    @Override
    public void buildSummary(Parser p, int maximalPathLength, long budget) {
        SGA_SF_Builder builder = new SGA_SF_Builder(this, p, maximalPathLength);
        int timeLimit = 10;
        double sgSize = 0.9;
        builder.build(sgSize, budget, timeLimit);
    }

    @Override
    public int query(int[] query) {
        DynamicProgrammingSearch<Short> search = new DynamicProgrammingSearch<>(this, this);
        Short dpResult = search.query(query);
        if (dpResult == 0) {
            return 0;
        } else if (dpResult > 0) {
            // Factor with subgraph
            int estimate = subgraph.estimate(query);
            double factor = (double) dpResult / Short.MAX_VALUE;
            return (int) (estimate * factor);
        } else {
            // Subgraph will return 0, while actual is bigger
            assert subgraph.estimate(query) == 0;
            double factor = (double) dpResult / Short.MIN_VALUE;
            return (int) (factor * NODES);
        }
    }

    @Override
    public long getBytesUsed() {
        return histogram.getBytesUsed()
                + subgraph.getBytesUsed()
                + joiner.getBytesUsed()
                + Integer.BYTES // this.NODES
                + 16L;
    }

    @Override
    public String getOutputName() {
        return this.getClass().getSimpleName() + "-" + histogram.calcSize() + "-" + subgraph.size();
    }

    @Override
    public Short concatEstimations(Short headEstimation, Short tailEstimation) {
        return (short) Math.min(headEstimation, tailEstimation);
    }

    @Override
    public int compare(Short o1, Short o2) {
        return Short.compare(o1, o2);
    }

    @Override
    public Short getStored(int[] query) {
        int qIndex = pathsOrdering.get(query);
        return histogram.getEstimate(qIndex);
    }

    public PathsOrdering getPathsOrdering() {
        return pathsOrdering;
    }

    public HistogramOfShorts getHistogram() {
        return histogram;
    }
}