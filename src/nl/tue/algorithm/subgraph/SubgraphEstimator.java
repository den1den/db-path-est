package nl.tue.algorithm.subgraph;

import nl.tue.algorithm.Estimator;
import nl.tue.algorithm.pathindex.PathSummary;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 * Created by Nathan on 6/4/2016.
 */
public class SubgraphEstimator implements Estimator<PathSummary> {

    private List<int[]> subgraph;

    /**
     * Builds a subgraph of the given graph and attempts to fit as much of it as possible into memory.
     */
    @Override
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
    public PathSummary concatEstimations(PathSummary left, PathSummary right) {
        throw new NotImplementedException();
    }

    @Override
    public int combineEstimations(List<PathSummary> sortedEs) {
        throw new NotImplementedException();
    }

    @Override
    public Collection<PathSummary> retrieveAllExactEstimations() {
        throw new NotImplementedException();
    }

    @Override
    public long getBytesUsed() {
        return 0;
    }
}
