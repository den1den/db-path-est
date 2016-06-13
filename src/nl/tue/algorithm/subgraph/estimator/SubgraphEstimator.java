package nl.tue.algorithm.subgraph.estimator;

import nl.tue.MemoryConstrained;
import nl.tue.algorithm.subgraph.SubgraphCompressor;
import nl.tue.io.Parser;
import nl.tue.io.TupleList;
import nl.tue.io.graph.AdjacencyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * TODO Dennis, subclass this to add a new approach.
 *
 * Created by Nathan on 6/4/2016.
 */
public class SubgraphEstimator implements MemoryConstrained {

    private static final int OVERHEAD = 16 * 2 + 4 + 1 + 4 + 16 + 4;

    protected byte[] storage;

    protected byte labels;

    protected int subgraphLength;

    private AcceptNode accepter;

    public SubgraphEstimator() {
        accepter = node -> node % 3 == 0 || node % 2 == 0;
    }

    public SubgraphEstimator(AcceptNode acceptor) {
        this.accepter = acceptor;
    }

    public void buildSummary(AdjacencyList graph, int maximalPathLength, long budget, int labels) {
        this.labels = (byte) labels;

        List<int[]> subgraph = new ArrayList<>();

        outerloop:
        for (int node : graph.getNodes().keySet()) {
            if (accepter.acceptNode(node)) {
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

        int lBudget = (int) budget - OVERHEAD;
        if(lBudget <= 0){
            storage = new byte[0];
            this.subgraphLength = storage.length;
        } else {
            byte[] compressed = SubgraphCompressor.compressWithLimit(subgraph, lBudget);

            storage = new byte[(int) Math.min(budget - OVERHEAD, 0)];

            System.arraycopy(compressed, 0, storage, 0, compressed.length);

            this.subgraphLength = compressed.length;
        }
    }

    /**
     * Builds a subgraph of the given graph and attempts to fit as much of it as possible into memory.
     */
    public void buildSummary(Parser p, int k, long b) {
        AdjacencyList graph = new AdjacencyList(p);
        buildSummary(graph, k, b, p.getNLabels());
    }

    protected Parser parserFromStorage() {
        TupleList tupleList = decompressTupleList();
        Parser parser = new Parser();
        parser.parse(tupleList);
        return parser;
    }

    public TupleList decompressTupleList() {
        byte[] compressed = new byte[subgraphLength];
        System.arraycopy(storage, 0, compressed, 0, subgraphLength);
        return SubgraphCompressor.decompressSubgraph(compressed);
    }

    public int estimate(int[] query) {

        Parser parser = parserFromStorage();

        AdjacencyList graph = new AdjacencyList(parser, false, this.labels);

        return graph.solvePathQuery(query).size();

    }

    @Override
    public long getBytesUsed() {
        return subgraphLength + OVERHEAD;
    }

    public int size() {
        return storage.length;
    }
}
