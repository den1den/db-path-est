package nl.tue.algorithm.subgraph_sf;

import nl.tue.algorithm.histogram.AbstractHistogramBuilder;
import nl.tue.algorithm.paths.PathsOrderingLexicographical;
import nl.tue.algorithm.subgraph.estimator.SubgraphEstimator;
import nl.tue.io.Parser;
import nl.tue.io.TupleList;
import nl.tue.io.graph.AdjacencyList;

import java.util.Iterator;

/**
 * Builder class for:
 * SubGraphAlgorithm_SF (Specfic Factors)
 */
public class SGA_SF_Builder {
    final SubGraphAlgorithm_SF BUILD_TO;
    final TupleList.Meta INPUT_META_DATA;
    final int MAX_PATH_LENGTH;
    final AdjacencyList GRAPH;

    public boolean timeout;
    public double timeH, timeSG, timeTotal;

    public SGA_SF_Builder(SubGraphAlgorithm_SF BUILD_TO, Parser p, int maximalPathLength) {
        this.BUILD_TO = BUILD_TO;

        this.MAX_PATH_LENGTH = maximalPathLength;
        GRAPH = new AdjacencyList(p);
        INPUT_META_DATA = p.calcMetadata();
    }

    private void resetTime(){
        timeout = false;
        timeH = Double.NaN;
        timeSG = Double.NaN;
        timeTotal = Double.NaN;
    }

    public void build(double sgSize, long budget, double histogramRelativeTimeLimit){
        resetTime();
        double t0 = System.currentTimeMillis();
        BUILD_TO.pathsOrdering = new PathsOrderingLexicographical(nLabels(), MAX_PATH_LENGTH);

        // Building subgraph
        long subGraphSize = (long) (budget * sgSize);
        buildSubgraph(MAX_PATH_LENGTH, subGraphSize);

        // Building histogram
        long histogramSize = budget - subGraphSize - BUILD_TO.pathsOrdering.getBytesUsed();
        int histogramTimeLimit = (int) Math.ceil((timeH * histogramRelativeTimeLimit));
        buildHistogram(histogramSize, histogramTimeLimit);

        timeTotal = (System.currentTimeMillis() - t0) / 1000;
        System.out.printf("%30s build in %.2f seconds, and used %.2f%% of %s bytes%n",
                BUILD_TO.getClass().getSimpleName(),
                timeTotal,
                ((double) BUILD_TO.getBytesUsed()) / budget * 100,
                budget);
    }

    private int nLabels() {
        return this.INPUT_META_DATA.labels.size();
    }

    private int nNodes() {
        return this.INPUT_META_DATA.nodes.size();
    }

    void buildHistogram(long BYTES, int STOPPING_TIME) {
        double t0;
        double t1;
        t0 = System.currentTimeMillis();

        AbstractHistogramBuilder.Short histogramBuilder = new AbstractHistogramBuilder.Short(BUILD_TO.joiner);
        Iterator<int[]> pathsIterator = BUILD_TO.pathsOrdering.iterator();
        while (pathsIterator.hasNext()) {
            t1 = System.currentTimeMillis();
            if (t1 - t0 > STOPPING_TIME * 1000) {
                //Takes more then 2 seconds
                System.out.printf("%30s building prematurely stopped at %.1f seconds%n",
                        getClass().getSimpleName(),
                        (t1 - t0) / 1000);
                timeout = true;
                break;
            }

            int[] next = pathsIterator.next();
            int nextIndex = BUILD_TO.pathsOrdering.get(next);

            int subGraphResult = BUILD_TO.subgraph.estimate(next);
            int actualResult = GRAPH.getEstimation(next).getTuples();
            Short storedShortVal;

            if (actualResult == 0) {
                if (subGraphResult != 0) {
                    System.err.println("Error: subgraph gave non zero result, while GRAPH is zero."
                            + Thread.currentThread().getStackTrace()[2]);
                }
                continue;
            } else if (subGraphResult == 0) {
                //Factor will be infinite, so we replace it with the proportion
                double partOfTotal = (double) actualResult / nNodes();
                storedShortVal = (short) (partOfTotal * Short.MIN_VALUE);
            } else {
                double factorOfPrediction = (double) subGraphResult / actualResult;
                storedShortVal = (short) (factorOfPrediction * Short.MAX_VALUE);
            }
            histogramBuilder.addEstimation(Double.valueOf(storedShortVal), nextIndex);

            if (histogramBuilder.estMemUsage() >= BYTES) {
                break;
            }
        }
        BUILD_TO.histogram = histogramBuilder.toHistogram();
        t1 = System.currentTimeMillis();

        timeH = (t1 - t0) / 1000;
        System.out.printf("%30s build in %.2f seconds, and used %.2f%% of %s bytes%n",
                BUILD_TO.histogram.getClass().getSimpleName(),
                timeH,
                ((double) BUILD_TO.histogram.getBytesUsed()) / BYTES * 100,
                BYTES);
    }

    void buildSubgraph(int maximalPathLength, long subGraphSize) {
        double t0;
        double t1;
        t0 = System.currentTimeMillis();
        BUILD_TO.subgraph = new SubgraphEstimator();
        BUILD_TO.subgraph.buildSummary(GRAPH, maximalPathLength, subGraphSize, nLabels());
        t1 = System.currentTimeMillis();
        timeSG = (t1 - t0) / 1000;
        System.out.printf("%30s build in %.1f seconds, and used %.2f%% of %s bytes.%n",
                BUILD_TO.subgraph.getClass().getSimpleName(),
                timeSG,
                ((double) BUILD_TO.subgraph.getBytesUsed()) / subGraphSize * 100,
                subGraphSize);
    }
}
