package nl.tue.algorithm.subgraph;

import nl.tue.MemoryConstrained;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Nathan on 6/4/2016.
 */
public class SubgraphEstimator implements MemoryConstrained {

    private static final int OVERHEAD = 16 * 2 + 4 + 1 + 4;

    private byte[] storage;

    private byte labels;

    private int subgraphLength;

    /**
     * Builds a subgraph of the given graph and attempts to fit as much of it as possible into memory.
     */
    public void buildSummary(Parser p, int k, double b) {

        AdjacencyList graph = new AdjacencyList(p);

        List<int[]> subgraph = new ArrayList<>();

        this.labels = (byte) p.getNLabels();

        outerloop:
        for (int node : graph.getNodes().keySet()) {
            if (node % 3 == 0 || node % 2 == 0) {
                Map<Integer, Set<Integer>> integerSetMap = graph.getNodes().get(node);

                for (int label : integerSetMap.keySet()) {
                    if (label < integerSetMap.keySet().size() / 2) {
                        for (int rightNode : integerSetMap.get(label)) {
                            subgraph.add(new int[]{node, label, rightNode});
                        }
                    }
                }
            }
        }

        byte[] compressed = SubgraphCompressor.compressWithLimit(subgraph, (int) b - OVERHEAD);

        storage = new byte[(int) b - OVERHEAD];

        System.arraycopy(compressed, 0, storage, 0, compressed.length);

        this.subgraphLength = compressed.length;

    }

    public int estimate(int[] query) {

        byte[] compressed = new byte[subgraphLength];

        System.arraycopy(storage, 0, compressed, 0, subgraphLength);

        Parser parser = new Parser();
        parser.parse(SubgraphCompressor.decompressSubgraph(compressed));

        AdjacencyList graph = new AdjacencyList(parser, false, this.labels);


        return graph.solvePathQuery(query).size();

    }

    @Override
    public long getBytesUsed() {
        return 0;
    }

}
