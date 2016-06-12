package nl.tue.algorithm.subgraph.estimator;

import nl.tue.algorithm.subgraph.SubgraphCompressor;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;

import java.util.ArrayList;
import java.util.List;

import static nl.tue.algorithm.subgraph.estimator.SubgraphEstimatorsWithHighKFactors.computeFactorForK;

/**
 * Created by Nathan on 6/10/2016.
 */
public class AverageSubgraphEstimator extends SubgraphEstimator {
/*
    private static final int OVERHEAD = 1;

    private byte k;

    @Override
    public void buildSummary(Parser p, int k, double b) {
        super.buildSummary(p, k, b - OVERHEAD);

        Parser subgraphParser = parserFromStorage();

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

        factorList.remove(0);
        factorList.remove(1);

        factorList.add(0, );
        factorList.add(1, )

    }

    @Override
    public int estimate(int[] query) {
        int res = super.estimate(query);

        int avgRes = SubgraphEstimatorWithEdgeBasedFactors.estimateWithAverage(query, res, edgeFactorsFromStorage());
        int highKRes = ;
        int factorRes = ;

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
*/
}
