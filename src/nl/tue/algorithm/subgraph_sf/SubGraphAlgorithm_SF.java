package nl.tue.algorithm.subgraph_sf;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.dynamicprogramming.DCombiner;
import nl.tue.algorithm.dynamicprogramming.DInput;
import nl.tue.algorithm.dynamicprogramming.DynamicProgrammingSearch;
import nl.tue.algorithm.histogram.HistogramOfShorts;
import nl.tue.algorithm.histogram.JoinResult;
import nl.tue.algorithm.histogram.Joiner;
import nl.tue.algorithm.paths.PathsOrdering;
import nl.tue.algorithm.subgraph.estimator.SubgraphEstimator;
import nl.tue.io.Parser;

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
        return storedToResult(query, dpResult);
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
        return this.getClass().getSimpleName() + "-" + histogram.calcNEstimations() + "-" + subgraph.size();
    }

    @Override
    public Short concatEstimations(Short headEstimation, Short tailEstimation) {
        if(headEstimation == null){
            if(tailEstimation == null){
                System.err.println("Error combining unknown estimations...");
                return -1;
            }
            System.err.println("Very rough estimation (1) ...");
            return tailEstimation;
        } else if (tailEstimation == null) {
            System.err.println("Very rough estimation (2) ...");
            return tailEstimation;
        } else {
            return (short) Math.min(headEstimation, tailEstimation);
        }
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

    public Short whatToStore(int actualResult, int subGraphResult) {
        short r;
        if (actualResult == 0) {
            return null;
        } else if (subGraphResult != 0) {
            double factorOfPrediction = (double) subGraphResult / actualResult;
            r = (short) (factorOfPrediction * Short.MAX_VALUE);
            if(r == 0){
                r = 1;
            }
        } else {
            //Factor will be infinite, so we replace it with the proportion
            double partOfTotal = (double) actualResult / NODES;
            r = (short) (partOfTotal * Short.MIN_VALUE);
            if(r == 0){
                r = -1;
            }
        }
        return r;
    }

    private int storedToResult(int[] query, Short dpResult) {
        if (dpResult == 0) {
            return 0;
        } else if (dpResult > 0) {
            // Factor with subgraph
            int estimate = subgraph.estimate(query);
            if(estimate == 0){
                System.err.println("subgraph returns zero while non zero was expected, do wild guess");
                return (int) (((double) dpResult / Short.MAX_VALUE)*NODES);
            }
            double factor = (double) dpResult / Short.MAX_VALUE;
            return (int) (estimate * factor);
        } else {
            // Subgraph will return 0, while actual is bigger
            assert subgraph.estimate(query) == 0;
            double factor = (double) dpResult / Short.MIN_VALUE;
            return (int) (factor * NODES);
        }
    }
}
