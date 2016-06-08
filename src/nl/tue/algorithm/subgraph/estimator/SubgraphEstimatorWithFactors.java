package nl.tue.algorithm.subgraph.estimator;

import nl.tue.algorithm.subgraph.SubgraphCompressor;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Nathan on 6/7/2016.
 */
public class SubgraphEstimatorWithFactors extends SubgraphEstimator {

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

            for (int i = 1; i <= k; i++) {
                factorList.add(computeFactorForK(i, p.getNLabels(), graph, subGraph));
            }

            byte[] factorCompressed = SubgraphCompressor.doublesToByteArray(factorList);

            System.arraycopy(factorCompressed, 0, storage, subgraphLength, factorCompressed.length);
        }
    }

    @Override
    public int estimate(int[] query) {
        int res = super.estimate(query);

        byte[] factorListCompr = new byte[k*8];

        System.arraycopy(storage, subgraphLength, factorListCompr, 0, k*8);

        List<Double> factorList = SubgraphCompressor.byteArrayToDoubles(factorListCompr);

        return (int) (factorList.get(query.length - 1) * (double)res);


    }

    private static double computeFactorForK(int k, int labels, AdjacencyList original, AdjacencyList subGraph) {
        int labelSpace = original.getNodes().get(original.getNodes().keySet().iterator().next()).size();
        Random random = new Random();

        List<Double> factors = new ArrayList<>();

        int edgeCount = original.totalEdges();

        List<Double> buckets = new ArrayList<>();

        for(int i = 0; i < labels; i++) {
            double fraction = (double)original.getOutgoingIndex().get(i).size() / edgeCount;

            if(i == 0) {
                buckets.add(fraction);
            } else {

                buckets.add(fraction + buckets.get(i - 1));
            }
        }

        do {

            int[] path = new int[5];

            for (int i = 0; i < path.length; i++) {
                double randVal = random.nextDouble();

                int label = 0;

                for(int j = 0; j < labels; j++) {
                    if(buckets.get(j) < randVal) {
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
            }

            int subGraphRes = subGraph.solvePathQuery(path).size();

            if(subGraphRes == 0) {
                continue;
            }

            int properRes = original.solvePathQuery(path).size();

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
