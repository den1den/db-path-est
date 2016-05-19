package nl.tue.io.graph;

import nl.tue.io.Parser;

import java.util.*;

/**
 * Created by Nathan on 5/19/2016.
 */
public class AdjacencyList {

    private final Map<Integer, Map<Integer, Set<Integer>>> nodes;

    public AdjacencyList(Parser parser) {


        nodes = new HashMap<>();

        for(long[] tuple : parser.tuples) {
            if(tuple.length != 3) {
                System.err.println("While reading results from parser an unexpected tuple has been encountered");
                continue;
            }

            /**
             * Operates under the assumption that all labels between and including lowestlabel and highest label exist.
             */
            if(!nodes.containsKey((int)tuple[0])) {
               nodes.put((int)tuple[0], emptyEdges(parser.lowestLabel, parser.highestLabel));
            }
            if(!nodes.containsKey((int)tuple[2])) {
                nodes.put((int)tuple[2], emptyEdges(parser.lowestLabel, parser.highestLabel));
            }

            Set<Integer> edgesForLabel = nodes.get((int)tuple[0]).get((int)tuple[1]);

            edgesForLabel.add((int)tuple[2]);
        }
    }

    private Map<Integer, Set<Integer>> emptyEdges(long lowestLabel, long highestLabel) {
        Map<Integer, Set<Integer>> edges = new HashMap<>();
        for(long i = lowestLabel; i <= highestLabel; i++) {
            edges.put((int)i, new HashSet<>());
        }

       return edges;
    }

    public Map<Integer, Map<Integer, Set<Integer>>> getNodes() {
        return nodes;
    }
}
