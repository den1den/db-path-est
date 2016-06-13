package nl.tue.algorithm.subgraph;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.dynamicprogramming.DCombiner;
import nl.tue.algorithm.dynamicprogramming.DInput;
import nl.tue.algorithm.dynamicprogramming.DynamicProgrammingSearch;
import nl.tue.algorithm.histogram.AbstractHistogramBuilder;
import nl.tue.algorithm.histogram.Histogram;
import nl.tue.algorithm.histogram.JoinResult;
import nl.tue.algorithm.histogram.Joiner;
import nl.tue.algorithm.paths.PathsOrdering;
import nl.tue.algorithm.paths.PathsOrderingLexicographical;
import nl.tue.algorithm.subgraph.estimator.SubgraphEstimator;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;

import java.util.Iterator;
import java.util.List;

/**
 * Subgraph algorithm, with specific factors
 * Created by Dennis on 8-6-2016.
 */
public class SubGraphAlgorithm_SF extends Algorithm<Histogram> implements DCombiner<Short>,DInput<Short> {
    protected Histogram histogram;
    protected SubgraphEstimator subgraph;
    private PathsOrdering pathsOrdering;
    private Joiner<Double, JoinResult.NumberJoinResult> joiner;
    /**
     * Relative size of subgraph relative to the histogram
     * Is only used for building the summery
     */
    private double sgSize;

    public SubGraphAlgorithm_SF(Joiner<Double, JoinResult.NumberJoinResult> joiner, double sgSize) {
        this.joiner = joiner;
        assert sgSize >= 0 && sgSize <= 1;
        this.sgSize = sgSize;
    }

    @Override
    public void buildSummary(Parser p, int maximalPathLength, long budget) {
        int labels = p.getNLabels();
        pathsOrdering = new PathsOrderingLexicographical(labels, maximalPathLength);
        long subGraphSize = (long) (budget * sgSize);

        //Building subgraph
        buildSubgraph(p, maximalPathLength, subGraphSize);

        // Calculate size for histogram
        long histogramSize = budget - subGraphSize - pathsOrdering.getBytesUsed();

        buildHistogram(p, histogramSize);

        System.out.printf("%s builded a summery with %.2f%% of %s bytes used",
                getClass().getSimpleName(),
                ((double)getBytesUsed())/budget*100,
                budget);
    }

    private void buildHistogram(Parser p, long histogramSize) {
        double t0;
        double t1;
        t0 = System.currentTimeMillis();
        AbstractHistogramBuilder.Short builder = new AbstractHistogramBuilder.Short(joiner);
        Iterator<int[]> pathsIterator = pathsOrdering.iterator();
        AdjacencyList fullGraph = new AdjacencyList(p);
        while (pathsIterator.hasNext()){
            int[] next = pathsIterator.next();
            int nextIndex = pathsOrdering.get(next);

            int subGraphResult = subgraph.estimate(next);
            int actualResult = fullGraph.getEstimation(next).getTuples();

            /**
             * Stored value is between 0 and Short.MAX_VALUE
             */
            double storedShortVal = (double) subGraphResult / actualResult * Short.MAX_VALUE;
            builder.addEstimation(storedShortVal, nextIndex);

            if (builder.estMemUsage() >= histogramSize) {
                break;
            }
            t1 = System.currentTimeMillis();
            if(t1 - t0 > 2*1000){
                //Takes more then 2 seconds
                System.out.printf("Histogram building prematurely stopped at %.1f seconds%n", (t1 - t0) / 1000);
                break;
            }
        }
        histogram = builder.toHistogram();
        t1 = System.currentTimeMillis();
        System.out.printf("Histogram build in %s seconds, with size: %d of %d%n", (t1 - t0)/1000, histogram.getBytesUsed(), histogramSize);
    }

    private void buildSubgraph(Parser p, int maximalPathLength, long subGraphSize) {
        double t0;
        double t1;
        t0 = System.currentTimeMillis();
        subgraph = new SubgraphEstimator();
        subgraph.buildSummary(p, maximalPathLength, subGraphSize);
        t1 = System.currentTimeMillis();
        System.out.printf("Subgraph constructed in %.1f seconds%n", (t1 - t0) / 1000);
    }

    @Override
    public int query(int[] query) {
        DynamicProgrammingSearch search = new DynamicProgrammingSearch<>(this, this);
        return search.query(query);
    }

    @Override
    public long getBytesUsed() {
        return histogram.getBytesUsed() + subgraph.getBytesUsed() + pathsOrdering.getBytesUsed() + joiner.getBytesUsed() + 16L;
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
    public int estimationsToResult(List<Short> list) {
        double avg = 0;
        for (Short s : list){
            avg += s;
        }
        return (int) (avg / list.size());
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

    public SubgraphEstimator getSubgraph() {
        return subgraph;
    }

    public PathsOrdering getPathsOrdering() {
        return pathsOrdering;
    }

    public Histogram getHistogram() {
        return histogram;
    }
}
