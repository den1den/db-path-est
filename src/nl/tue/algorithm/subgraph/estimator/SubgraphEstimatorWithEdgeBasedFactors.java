package nl.tue.algorithm.subgraph.estimator;

import nl.tue.algorithm.subgraph.SubgraphCompressor;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathan on 6/10/2016.
 */
public class SubgraphEstimatorWithEdgeBasedFactors extends SubgraphEstimator {

    private static final int OVERHEAD = 0;

    @Override
    public void buildSummary(Parser p, int k, double b) {
        super.buildSummary(p, k, b - OVERHEAD);

        Parser subgraphParser = parserFromStorage();

        AdjacencyList subgraph = new AdjacencyList(subgraphParser);
        AdjacencyList graph = new AdjacencyList(p);

        List<Double> edgeFactors = new ArrayList<>();

        for(int i = 0; i < labels; i++) {
            double factor = (double)graph.getOutgoingIndex().get(i).size() / (double)subgraph.getOutgoingIndex().get(i).size();

            edgeFactors.add(factor);
        }


        storeEdgeFactors(edgeFactors);
    }

    @Override
    public int estimate(int[] query) {
        int res = super.estimate(query);

        List<Double> factors = edgeFactorsFromStorage();

        for(int edge : query) {
            res *= factors.get(edge);
        }

        return res;
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
}
