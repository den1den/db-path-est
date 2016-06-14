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
    final int LABELSFORWARDBACKWARD;
    final SubGraphAlgorithm_SF BUILD_TO;
    final int MAX_PATH_LENGTH;
    final AdjacencyList GRAPH;

    public boolean timeout;
    public double timeH, timeSG, timeTotal;
    public final double parsing;

    public SGA_SF_Builder(SubGraphAlgorithm_SF BUILD_TO, Parser p, int maximalPathLength) {
        this.BUILD_TO = BUILD_TO;

        this.MAX_PATH_LENGTH = maximalPathLength;
        long t0 = System.currentTimeMillis();
        GRAPH = new AdjacencyList(p);
        parsing = toSeconds(t0);
        TupleList.Meta meta = p.calcMetadata();
        this.LABELSFORWARDBACKWARD = meta.labels.size() * 2;
        BUILD_TO.NODES = meta.nodes.size();
    }

    private static double toSeconds(long t0) {
        return (double) (System.currentTimeMillis() - t0) / 1000;
    }

    public SGA_SF_Builder(SubGraphAlgorithm_SF BUILD_TO, Parser p, int MAX_PATH_LENGTH, int LABELSFORWARDBACKWARD, int NODES) {
        this.LABELSFORWARDBACKWARD = LABELSFORWARDBACKWARD;
        BUILD_TO.NODES = NODES;
        this.BUILD_TO = BUILD_TO;
        this.MAX_PATH_LENGTH = MAX_PATH_LENGTH;

        long t0 = System.currentTimeMillis();
        GRAPH = new AdjacencyList(p);
        parsing = toSeconds(t0);
    }

    private void resetTime() {
        timeout = false;
        timeH = Double.NaN;
        timeSG = Double.NaN;
        timeTotal = Double.NaN;
    }

    public void build(double sgSize, long budget, double histogramRelativeTimeLimit) {
        resetTime();
        long t0 = System.currentTimeMillis();
        BUILD_TO.pathsOrdering = new PathsOrderingLexicographical(nLabels(), MAX_PATH_LENGTH);

        // Building subgraph
        long subGraphSize = (long) (budget * sgSize);
        buildSubgraph(MAX_PATH_LENGTH, subGraphSize);

        // Building histogram
        long histogramSize = budget - subGraphSize - BUILD_TO.pathsOrdering.getBytesUsed();
        int histogramTimeLimit = (int) Math.ceil((timeSG * histogramRelativeTimeLimit));
        buildHistogram(histogramSize, histogramTimeLimit);

        timeTotal = toSeconds(t0) + parsing;
        System.out.printf("%30s build in %.2f seconds, and used %.2f%% of %s bytes%n",
                BUILD_TO.getClass().getSimpleName(),
                timeTotal,
                ((double) BUILD_TO.getBytesUsed()) / budget * 100,
                budget);
    }

    private int nLabels() {
        return LABELSFORWARDBACKWARD;
    }

    private int nNodes() {
        return BUILD_TO.NODES;
    }

    void buildHistogram(long BYTES, int STOPPING_TIME) {
        long t0;
        long t1;
        t0 = System.currentTimeMillis();

        AbstractHistogramBuilder.Short histogramBuilder = new AbstractHistogramBuilder.Short(BUILD_TO.joiner);
        Iterator<int[]> pathsIterator = BUILD_TO.pathsOrdering.iterator();
        while (pathsIterator.hasNext()) {
            t1 = System.currentTimeMillis();
            if (t1 - t0 > STOPPING_TIME * 1000) {
                //Takes more then 2 seconds
                System.out.printf("%30s building prematurely stopped at %.1f seconds%n",
                        getClass().getSimpleName(),
                        toSeconds(t0));
                timeout = true;
                break;
            }

            int[] next = pathsIterator.next();
            int nextIndex = BUILD_TO.pathsOrdering.get(next);

            int subGraphResult = BUILD_TO.subgraph.estimate(next);
            int actualResult = GRAPH.getEstimation(next).getTuples();
            Short storedShortVal;

            if(subGraphResult > actualResult){
                System.err.println("Error: subgraph gave non zero result, while GRAPH is zero."
                        + Thread.currentThread().getStackTrace()[1]);
            }
            System.out.printf("subGraphResult: %10d actualResult %10d", subGraphResult, actualResult);

            if (actualResult == 0) {
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

        timeH = toSeconds(t0);
        System.out.printf("%30s build in %.2f seconds, and used %.2f%% of %s bytes%n",
                BUILD_TO.histogram.getClass().getSimpleName(),
                timeH,
                ((double) BUILD_TO.histogram.getBytesUsed()) / BYTES * 100,
                BYTES);
    }

    void buildSubgraph(int maximalPathLength, long subGraphSize) {
        long t0 = System.currentTimeMillis();
        BUILD_TO.subgraph = new SubgraphEstimator();
        BUILD_TO.subgraph.buildSummary(GRAPH, maximalPathLength, subGraphSize, nLabels());
        timeSG = toSeconds(t0);
        System.out.printf("%30s build in %.1f seconds, and used %.2f%% of %s bytes.%n",
                BUILD_TO.subgraph.getClass().getSimpleName(),
                timeSG,
                ((double) BUILD_TO.subgraph.getBytesUsed()) / subGraphSize * 100,
                subGraphSize);
    }

    public static String toCSVHeader() {
        return "total_time; Parser_time; total_memory; total_nodes; " +
                "histogram_time; histogram_timeout; histogram_memory; histogram_tuples; " +
                "subgraph_time; subgraph_memory; subgraph_edges";
    }

    public String toCSV() {
        StringBuilder sb = new StringBuilder()
                .append(timeTotal).append(';')
                .append(BUILD_TO.getBytesUsed()).append(';')
                .append(nNodes()).append(';')
                .append(timeH).append(';')
                .append(timeout).append(';')
                .append(BUILD_TO.histogram.getBytesUsed()).append(';')
                .append(BUILD_TO.histogram.calcNEstimations()).append(';')
                .append(timeSG).append(';')
                .append(BUILD_TO.subgraph.getBytesUsed()).append(';')
                .append(BUILD_TO.subgraph.decompressTupleList().size());
        return sb.toString();
    }
}
