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

    private List<int[]> subgraph;

    /**
     * Builds a subgraph of the given graph and attempts to fit as much of it as possible into memory.
     */
    public void buildSummary(Parser p, int k, double b) {
        int maxEdges = (int)Math.ceil(b / 5);

        AdjacencyList graph = new AdjacencyList(p);

        subgraph = new ArrayList<>();

        outerloop:
        for(int node : graph.getNodes().keySet()) {
            if(node % 3 == 0 || node % 2 == 0 ) {
                Map<Integer, Set<Integer>> integerSetMap = graph.getNodes().get(node);

                for(int label : integerSetMap.keySet()) {
                    if(label < integerSetMap.keySet().size() / 2) {
                        for (int rightNode : integerSetMap.get(label)) {
                            subgraph.add(new int[]{node, label, rightNode});

                            if (subgraph.size() == maxEdges) {
                                break outerloop;
                            }

                        }
                    }
                }
            }
        }
    }

    public int estimate(int[] query) {

        Parser parser = new Parser();
        parser.parse(this.subgraph);

        AdjacencyList graph = new AdjacencyList(parser, false);


        return graph.solvePathQuery(query).size();

    }

    @Override
    public long getBytesUsed() {
        return 0;
    }

}
