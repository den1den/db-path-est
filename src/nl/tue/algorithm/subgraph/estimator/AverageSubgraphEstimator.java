package nl.tue.algorithm.subgraph.estimator;

import nl.tue.algorithm.subgraph.SubgraphCompressor;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static nl.tue.algorithm.subgraph.estimator.SubgraphEstimatorsWithHighKFactors.computeFactorForK;

/**
 * Created by Nathan on 6/10/2016.
 */
public class AverageSubgraphEstimator extends SubgraphEstimator {

    private static final int OVERHEAD = 1;

    private byte k;

    @Override
    public void buildSummary(Parser p, int k, long b) {
        super.buildSummary(p, k, b - OVERHEAD);

        Parser subgraphParser = parserFromStorage();

        this.k = (byte) k;

        AdjacencyList subgraph = new AdjacencyList(subgraphParser);
        AdjacencyList graph = new AdjacencyList(p);

        List<Double> edgeFactors = new ArrayList<>();

        for (int i = 0; i < labels; i++) {
            double factor = (double) graph.getOutgoingIndex().get(i).size() / (double) subgraph.getOutgoingIndex().get(i).size();

            edgeFactors.add(factor);
        }


        storeEdgeFactors(edgeFactors);

        List<Double> factorList = new ArrayList<>();

        for (int i = 1; i <= k; i++) {
            factorList.add(computeFactorForK(i, p.getNLabels(), graph, subgraph));
        }

        byte[] factorCompressed = SubgraphCompressor.doublesToByteArray(factorList);

        System.arraycopy(factorCompressed, 0, storage, subgraphLength + labels * 8, factorCompressed.length);

        double nodeBasedFactork1 = (double) p.size() / subgraphParser.size();
        double nodeBasedFactork2 = nodeBasedFactork1 * 1.4;

        byte[] kFactorArr = SubgraphCompressor.doublesToByteArray(new ArrayList<>(Arrays.asList(nodeBasedFactork1, nodeBasedFactork2)));

        System.arraycopy(kFactorArr, 0, storage, subgraphLength + labels * 8 + k * 8, kFactorArr.length);

    }

    @Override
    public int estimate(int[] query) {
        int res = super.estimate(query);

        int avgRes = SubgraphEstimatorWithEdgeBasedFactors.estimateWithAverage(query, res, edgeFactorsFromStorage());

        byte[] kFactorArr = new byte[k * 8];

        System.arraycopy(storage, subgraphLength + labels * 8, kFactorArr, 0, kFactorArr.length);

        List<Double> factorList = SubgraphCompressor.byteArrayToDoubles(kFactorArr);

        int highKRes = (int) (res * factorList.get(query.length - 1));

        byte[] nodeBasedFactor = new byte[16];

        System.arraycopy(storage, subgraphLength + labels * 8 + kFactorArr.length, nodeBasedFactor, 0, 16);

        List<Double> lowKFactor = SubgraphCompressor.byteArrayToDoubles(nodeBasedFactor);

        factorList.remove(0);
        factorList.remove(0);

        factorList.add(0, lowKFactor.get(0));
        factorList.add(1, lowKFactor.get(1));

        int factorRes = (int) (res * factorList.get(query.length - 1));

        return (avgRes + highKRes + factorRes) / 3;
    }


    private void storeEdgeFactors(List<Double> factors) {
        byte[] factorCompressed = SubgraphCompressor.doublesToByteArray(factors);

        System.arraycopy(factorCompressed, 0, storage, subgraphLength, factorCompressed.length);
    }

    private List<Double> edgeFactorsFromStorage() {
        byte[] factorListCompr = new byte[labels * 8];

        System.arraycopy(storage, subgraphLength, factorListCompr, 0, labels * 8);

        return SubgraphCompressor.byteArrayToDoubles(factorListCompr);

    }

    @Override
    public long getBytesUsed() {
        long base = super.getBytesUsed();

        return base + OVERHEAD + labels * 8 + (k + 2) * 8;
    }
}
