package nl.tue.algorithm.pathindex;

import nl.tue.algorithm.astar.AStart;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;
import nl.tue.io.graph.DirectedBackEdgeGraph;
import nl.tue.io.graph.NodePair;

import java.util.*;

/**
 * TODO make this non static, allow it to maintain state and make a querynext method.
 * Created by Nathan on 5/24/2016.
 */
public class IndexQueryEstimatorFactory {

    public static Queue<PathSummary> construct(Parser p, int k, int noOfPaths) {
        Queue<PathSummary> output = new LinkedList<>();

        DirectedBackEdgeGraph graph = new AdjacencyList(p);

        AStart aStart = new AStart(p.getNLabels(), k);

        List<PathSummary> zeroLengthPaths = new ArrayList<>();

        for(int[] path : aStart) {
            if(output.size() >= noOfPaths) {
                break;
            }

            /**
             * If this is a bona fide zero path we already know about then we skip it, and set heuristic to zero.
             */
            if(pathInList(zeroLengthPaths, path)) {
                aStart.setHeuristic(0);
                continue;
            }

            PathSummary summary = estimationForPath(graph, path, aStart);

            if(summary.getSummary().getTuples() == 0) {
                zeroLengthPaths.add(summary);
            }

            System.out.println(String.format("Indexed %s", summary.getIndex().getPath()));

            output.add(summary);
        }

        return output;
    }

    /**
     *
     * @param graph Graph used to compute the path summary.
     * @param path StringPath for which a summary should be computed
     * @param astart Used to give a heuristic value to the path for which a summary just has been computed.
     * @return
     */
    public static PathSummary estimationForPath(DirectedBackEdgeGraph graph, int[] path, AStart astart) {
        Collection<NodePair> result = graph.solvePathQuery(path);

        Set<Integer> distinctStart = new HashSet<>();
        Set<Integer> distinctEnd = new HashSet<>();

        for(NodePair pair : result) {
            distinctStart.add(pair.getLeft());
            distinctEnd.add(pair.getRight());
        }

        Summary summary = new Summary(distinctStart.size(), result.size(), distinctEnd.size());

        PathIndex index = new PathIndex(path);

        if(result.size() == 0) {
            astart.setHeuristic(0);
        } else {
            astart.setHeuristic(10);
        }

        return new PathSummary(index, summary);
    }

    private static boolean pathInList(List<PathSummary> items, int[] path) {
        for(PathSummary summary : items) {
            if(Collections.indexOfSubList(Arrays.asList(path), Arrays.asList(summary.getIndex().getPathAsIntArray()))
                    != -1) {
                return true;
            }
        }

        return false;
    }
}
