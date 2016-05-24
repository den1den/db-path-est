package nl.tue.algorithm.pathindex;

import nl.tue.algorithm.astar.AStart;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;
import nl.tue.io.graph.DirectedBackEdgeGraph;
import nl.tue.io.graph.NodePair;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Nathan on 5/24/2016.
 */
public class IndexQueryEstimatorFactory {

    public static Queue<PathSummary> construct(Parser p, int k, int noOfPaths) {
        Queue<PathSummary> output = new LinkedList<>();

        DirectedBackEdgeGraph graph = new AdjacencyList(p);

        AStart aStart = new AStart(p.getNLabels(), k);

        for(int[] path : aStart) {
            if(output.size() >= noOfPaths) {
                break;
            }

            output.add(estimationForPath(graph, path, aStart));
        }

        return output;
    }

    /**
     *
     * @param graph Graph used to compute the path summary.
     * @param path Path for which a summary should be computed
     * @param astart Used to give a heuristic value to the path for which a summary just has been computed.
     * @return
     */
    public static PathSummary estimationForPath(DirectedBackEdgeGraph graph, int[] path, AStart astart) {
        Set<NodePair> result = graph.solvePathQuery(path);

        Set<Integer> distinctStart = new HashSet<>();
        Set<Integer> distinctEnd = new HashSet<>();

        for(NodePair pair : result) {
            distinctStart.add(pair.getLeft());
            distinctEnd.add(pair.getRight());
        }

        Summary summary = new Summary(distinctStart.size(), result.size(), distinctEnd.size());

        PathIndex index = new PathIndex(path);

        astart.setHeuristic(10);

        return new PathSummary(index, summary);
    }
}
