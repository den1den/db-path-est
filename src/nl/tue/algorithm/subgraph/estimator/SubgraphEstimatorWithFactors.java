package nl.tue.algorithm.subgraph.estimator;

import nl.tue.algorithm.subgraph.SubgraphCompressor;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static nl.tue.algorithm.subgraph.estimator.SubgraphEstimatorsWithHighKFactors.computeFactorForK;

/**
 * Created by Nathan on 6/7/2016.
 */
public class SubgraphEstimatorWithFactors extends SubgraphEstimator {

    private static final int OVERHEAD = 1;


    private byte k;

    @Override
    public void buildSummary(Parser p, int k, long b) {
        super.buildSummary(p, k, b - OVERHEAD);

        this.k = (byte) k;

        int storageLeft = storage.length - subgraphLength;

        Parser subgraphParser = parserFromStorage();

        AdjacencyList subGraph = new AdjacencyList(subgraphParser, false, labels);

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


}
