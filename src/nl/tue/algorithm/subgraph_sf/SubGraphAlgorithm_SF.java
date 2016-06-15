package nl.tue.algorithm.subgraph_sf;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.dynamicprogramming.DCombiner;
import nl.tue.algorithm.dynamicprogramming.DInput;
import nl.tue.algorithm.dynamicprogramming.DynamicProgrammingSearch;
import nl.tue.algorithm.histogram.HistogramEntry;
import nl.tue.algorithm.histogram.HistogramOfShorts;
import nl.tue.algorithm.histogram.JoinResult;
import nl.tue.algorithm.histogram.Joiner;
import nl.tue.algorithm.paths.PathsOrdering;
import nl.tue.algorithm.subgraph.estimator.SubgraphEstimator;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Subgraph algorithm, with specific factors
 * Created by Dennis on 8-6-2016.
 */
public class SubGraphAlgorithm_SF extends Algorithm implements DCombiner<Short>, DInput<Short> {
    public boolean printing = false;
    HistogramOfShorts histogram;
    SubgraphEstimator subgraph;
    PathsOrdering pathsOrdering;
    Joiner<Double, JoinResult.NumberJoinResult> joiner;
    int NODES;
    double A, B, C;

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
    public long query(int[] query) {
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

    long y(int x) {
        double y = (A * x) * x + B * x + C;
        return Math.round(y);
    }

    int x(int y) {
        double wD = Math.sqrt(B * B - 4 * A * (C - y));
        double x = (-B + wD) / (2 * A);
        long round = Math.round(x);
        return Math.toIntExact(round);
    }

    /**
     * "store factor". Returns value to store in the histogram to calculate the result later on.
     *
     * @param subGraphResult Result during querying
     * @param actualResult   Real calculated result
     * @return value to go in histogram
     */
    Short whatToStore(int subGraphResult, int actualResult) {
        Short r;
        if (actualResult == 0) {
            r = 0;
        } else if (subGraphResult != 0) {
            double factorOfPrediction = (double) subGraphResult / actualResult;
            r = (short) (factorOfPrediction * Short.MAX_VALUE);
            if(r == 0){
                r = 1;
            }
        } else { // subGraphResult = 0
            //Factor will be infinite, so we replace it with the proportion
            // A*x^2 + B*x + C = y form a curve with:
            // y(1) = 1
            // y(2) = 2
            // y(Short.MIN_VALUE) = NODES^2
            // And a < b \wedge a >= 1 => y(x=a) < y(x=b)
            int toBeStored = actualResult;
            int x = -x(toBeStored);
            if (x > -1 || x < Short.MIN_VALUE) {
                throw new Error();
            }
            r = (short) x;
        }

        //Printing
        if (printing)
            System.out.printf("subGraphResult: %-10d actualResult %-10d storing: %-10d%n", subGraphResult, actualResult, r);
        return r;
    }

    /**
     * "multiply by factor". Given some value from the histogram, calculate the actual value.
     *
     * @param query    the query
     * @param dpResult value in histogram
     * @return actual tuples estimation (possibly using subgraph)
     */
    long storedToResult(int[] query, Short dpResult) {
        long result;
        if (dpResult == 0) {
            result = 0;
        } else if (dpResult > 0) {
            // Factor with subgraph
            int estimate = subgraph.estimate(query);
            if(estimate == 0){
                System.err.println("subgraph returns zero while non zero was expected, do wild guess");
                result = (int) (((double) dpResult / Short.MAX_VALUE)*NODES);
            } else {
                double factor = (double) dpResult / Short.MAX_VALUE;
                result = (int) (estimate * factor);
            }
        } else { // dpResult < 0
            // Subgraph will return 0, while actual is bigger
            int x = dpResult;
            result = y(x);
        }
        return result;
    }

    public String toCSVTableFullProcessed(PathsOrdering ordering, AdjacencyList REAL, int NODES) {
        StringBuilder sb = new StringBuilder();
        sb.append("1st-query; 1st-index; bucket; bucket-size; bucket-value; subgraph-estimate; real; NODES^2").append(System.lineSeparator());

        long NODES2 = NODES2();
        HistogramOfShorts histogram = getHistogram();
        Iterator<HistogramEntry> iterator = histogram.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            HistogramEntry next = iterator.next();
            int e = next.low;
            int[] query = ordering.get(e);
            int sge = subgraph.estimate(query);
            sb.append(Arrays.toString(query)).append(';') //1st-query
                    .append(e).append(';') //1st-index
                    .append(i).append(';') //bucket
                    .append(next.length()).append(';') //bucket-size
                    .append(next.value).append(';') //bucket-value
                    .append(sge).append(';') //subgraph-estimate
                    .append(REAL.getEstimation(query).getTuples()).append(';') //real
                    .append(NODES2).append(System.lineSeparator()); //NODES^2
            i++;
        }
        return sb.toString();
    }

    public long NODES2() {
        return Math.multiplyExact((long) NODES, NODES);
    }
}
