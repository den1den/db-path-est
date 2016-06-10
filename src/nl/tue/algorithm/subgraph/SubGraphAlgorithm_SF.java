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
    private SubgraphEstimator estimator = new SubgraphEstimator();
    private PathsOrdering pathsOrdering;
    private Joiner<Double, JoinResult.NumberJoinResult> joiner;
    private double sgSize;

    public SubGraphAlgorithm_SF(Joiner<Double, JoinResult.NumberJoinResult> joiner, double sgSize) {
        this.joiner = joiner;
        assert sgSize >= 0 && sgSize <= 1;
        this.sgSize = sgSize;
    }

    @Override
    protected Histogram build(Parser p, int maximalPathLength, long budget) {
        double t0 = System.currentTimeMillis();
        int labels = p.getNLabels();
        pathsOrdering = new PathsOrderingLexicographical(labels, maximalPathLength);
        long subGraphSize = (long) (budget * sgSize);
        long histogramSize = budget - subGraphSize - pathsOrdering.getBytesUsed();

        estimator.buildSummary(p, maximalPathLength, subGraphSize);
        AdjacencyList fullGraph = new AdjacencyList(p);
        AbstractHistogramBuilder.Short builder = new AbstractHistogramBuilder.Short(joiner);

        double t1 = System.currentTimeMillis();
        System.out.printf("Subgraph constructed in %.1f seconds%n", (t1 - t0) / 1000);
        t0 = t1;

        Iterator<int[]> pathsIterator = pathsOrdering.iterator();
        while (pathsIterator.hasNext()){
            int[] next = pathsIterator.next();
            int nextIndex = pathsOrdering.get(next);

            int subGraphResult = estimator.estimate(next);
            int realGraphresult = fullGraph.getEstimation(next).getTuples();

            /**
             * Stored value is between 0 and Short.MAX_VALUE
             */
            double storedShortVal = (double) subGraphResult / realGraphresult * Short.MAX_VALUE;
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

        System.out.print("Building histogram... ");
        Histogram histogram = builder.toHistogram();
        System.out.printf("%s build: %s, with size: %d of %d%n", histogram.getClass().getSimpleName(), histogram, histogram.getBytesUsed(), histogramSize);
        return histogram;
    }

    @Override
    public int query(int[] query) {
        DynamicProgrammingSearch search = new DynamicProgrammingSearch<>(this, this);
        return search.query(query);
    }

    @Override
    protected long bytesOverhead() {
        return pathsOrdering.getBytesUsed() + estimator.getBytesUsed() + joiner.getBytesUsed() + 16L;
    }

    @Override
    public String getOutputName() {
        return this.getClass().getSimpleName() + "-" + inMemory.calcSize() + "-" + estimator.size();
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
        return inMemory.getEstimate(qIndex);
    }
}
