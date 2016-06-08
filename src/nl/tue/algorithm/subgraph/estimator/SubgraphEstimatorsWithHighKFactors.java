package nl.tue.algorithm.subgraph.estimator;

import nl.tue.algorithm.pathindex.PathIndex;
import nl.tue.algorithm.subgraph.SubgraphCompressor;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Differs from the factors approach by only looking at the difference between the result of the subgraph and
 * original graph for queries of length >= 3 to determine the factor per path length. Below that, it used the
 * difference in relations between both graphs.
 * Created by Nathan on 6/8/2016.
 */
public class SubgraphEstimatorsWithHighKFactors extends SubgraphEstimator {
    private static final int OVERHEAD = 1;

    /**
     * The amount of queries executed to determine a factor.
     */
    private static final int QUERY_LIMIT_FOR_FACTOR = 2;


    private byte k;

    @Override
    public void buildSummary(Parser p, int k, double b) {
        super.buildSummary(p, k, b - OVERHEAD);

        byte[] compressed = new byte[subgraphLength];

        System.arraycopy(storage, 0, compressed, 0, subgraphLength);

        this.k = (byte) k;

        int storageLeft = storage.length - subgraphLength;

        Parser subgraphParser = new Parser();

        subgraphParser.parse(SubgraphCompressor.decompressSubgraph(compressed));

        AdjacencyList subGraph = new AdjacencyList(subgraphParser);

        AdjacencyList graph = new AdjacencyList(p);

        if (storageLeft > 8 * k) {
            List<Double> factorList = new ArrayList<>();

            factorList.add(p.tuples.size() / (double)subgraphParser.tuples.size());
            factorList.add(factorList.get(0) * 1.2);

            for (int i = 3; i <= k; i++) {
                factorList.add(computeFactorForK(i, p.getNLabels(), graph, subGraph));
            }

            byte[] factorCompressed = SubgraphCompressor.doublesToByteArray(factorList);

            System.arraycopy(factorCompressed, 0, storage, subgraphLength, factorCompressed.length);
        }
    }

    @Override
    public int estimate(int[] query) {
        int res = super.estimate(query);

        byte[] factorListCompr = new byte[k * 8];

        System.arraycopy(storage, subgraphLength, factorListCompr, 0, k * 8);

        List<Double> factorList = SubgraphCompressor.byteArrayToDoubles(factorListCompr);

        return (int) (factorList.get(query.length - 1) * (double) res);


    }

    public static double computeFactorForK(int k, int labels, AdjacencyList original, AdjacencyList subGraph) {
        int labelSpace = original.getNodes().get(original.getNodes().keySet().iterator().next()).size();
        Random random = new Random();

        List<Double> factors = new ArrayList<>();

        int edgeCount = original.totalEdges();

        List<Double> buckets = new ArrayList<>();

        for (int i = 0; i < labels; i++) {
            double fraction = (double) original.getOutgoingIndex().get(i).size() / edgeCount;

            if (i == 0) {
                buckets.add(fraction);
            } else {

                buckets.add(fraction + buckets.get(i - 1));
            }
        }

        do {

            int[] path = new int[k];

            for (int i = 0; i < path.length; i++) {
                double randVal = random.nextDouble();

                if(randVal < .66) {

                    randVal = random.nextDouble();

                    int label = 0;

                    for (int j = 0; j < labels; j++) {
                        if (buckets.get(j) < randVal) {
                            label = j;
                        } else {
                            label++;
                            break;
                        }
                    }

                    path[i] = label;

                    if(i != path.length - 1) {
                        randVal = random.nextDouble();

                        i++;

                        if(randVal < .33) {
                            if(label < labels/2) {
                                path[i] = label + labels/2;
                            } else {
                                path[i] = label - labels/2;
                            }

                        }
                    }

                } else {
                    path[i] = random.nextInt(labels);
                }
            }

            int subGraphRes = subGraph.solvePathQuery(path, 60).size();

            if (subGraphRes == 0) {
                continue;
            }

            int properRes = original.solvePathQuery(path, 60).size();

            if (properRes > 0) {
                factors.add((double) properRes / (double) subGraphRes);
            }
        } while (factors.size() < QUERY_LIMIT_FOR_FACTOR);

        /**
         * I know a double stream is weird but apparently it's the fault of the JDK developers.
         *
         * http://stackoverflow.com/questions/23106093/how-to-get-a-stream-from-a-float
         */
        return (factors.stream().mapToDouble(Double::doubleValue).sum() / (double) factors.size());

    }
}
