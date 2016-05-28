package nl.tue.io.graph;

import nl.tue.io.Parser;

import java.util.*;

/**
 * Created by Nathan on 5/19/2016.
 */
public class AdjacencyList implements DirectedBackEdgeGraph {

    private final Map<Integer, Map<Integer, Set<Integer>>> nodes;

    public AdjacencyList(Parser parser) {

        nodes = new HashMap<>();

        for(long[] tuple : parser.tuples) {
            if(tuple.length != 3) {
                System.err.println("While reading results from parser an unexpected tuple has been encountered");
                continue;
            }

            if(!nodes.containsKey((int)tuple[0])) {
               nodes.put((int)tuple[0], emptyEdges(0, parser.getNLabels() - 1));
            }
            if(!nodes.containsKey((int)tuple[2])) {
                nodes.put((int)tuple[2], emptyEdges(0, parser.getNLabels() - 1));
            }

            Set<Integer> edgesForLabel = nodes.get((int)tuple[0]).get(parser.getEdgeMappings().get("+" + tuple[1]));
            Set<Integer> backEdgesForLabel = nodes.get((int)tuple[2]).get(parser.getEdgeMappings().get("-" + tuple[1]));

            edgesForLabel.add((int)tuple[2]);
            backEdgesForLabel.add((int)tuple[0]);
        }
    }

    /**
     * Adds empty hashsets for both all the edges in between lowestlabel and highestlabel and addesd empty hashsets for
     * all the empty hashsets between 0 - lowestlabel and 0 - highesetlabel. Operates under the assumption that all
     * lables in between and including those two numbers are utilized.
     *
     * @param lowestLabel
     * @param highestLabel
     * @return
     */
    private Map<Integer, Set<Integer>> emptyEdges(long lowestLabel, long highestLabel) {
        Map<Integer, Set<Integer>> edges = new HashMap<>();
        for(long i = lowestLabel; i <= highestLabel; i++) {
            edges.put((int)i, new HashSet<>());
        }

       return edges;
    }

    public Set<NodePair> solvePathQuery(int[] path) {
        if(path.length  <= 0) {
            throw new IllegalArgumentException(String.format("A path length of %d does not make any sense", path.length));
        }

        Set<NodePair> out = new HashSet<>();

        for(int nodeStart : nodes.keySet()) {
            for(int nodeEnd : tracePath(nodeStart, path)) {
                out.add(new NodePair(nodeStart, nodeEnd));
            }
        }

        return out;
    }

    private Set<Integer> tracePath(int node, int[] path) {
        if(path.length <= 0) {
            throw new IllegalArgumentException(String.format("A path length of %d does not make any sense", path.length));
        }
        Set<Integer> out = new HashSet<>();

        int start = path[0];

        if(path.length > 1) {
            Set<Integer> outgoingOverStart = nodes.get(node).get(start);

            for(int nodeAfterStart : outgoingOverStart) {
                out.addAll(tracePath(nodeAfterStart, Arrays.copyOfRange(path, 1, path.length)));
            }

            return out;
        } else {
            out = nodes.get(node).get(start);

            return out;
        }
    }

    public Map<Integer, Map<Integer, Set<Integer>>> getNodes() {
        return nodes;
    }
}
