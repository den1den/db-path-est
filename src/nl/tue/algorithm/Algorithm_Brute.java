package nl.tue.algorithm;

import nl.tue.algorithm.astar.AStart;
import nl.tue.algorithm.brute.BruteTree;
import nl.tue.algorithm.paths.LabelSequence;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;
import nl.tue.io.graph.DirectedBackEdgeGraph;

/**
 * Algorithm based on StringPath Summaries
 * <ol>
 * <li>Stores PathInfo for each subQuery until some memory limit is reached</li>
 * </ol>
 *
 * @author dennis
 */
public class Algorithm_Brute extends Algorithm<BruteTree> {

    @Override
    protected BruteTree build(Parser p, int maximalPathLength, long budget) {
        int labels = p.getNLabels();
        AStart aStar = new AStart(labels, maximalPathLength);
        LabelSequence sequence = new LabelSequence(labels, maximalPathLength);
        int[] indexedResults = new int[sequence.getMaxIndex()];

        DirectedBackEdgeGraph graph = new AdjacencyList(p);
        for (int[] path : aStar) {
            int index = sequence.get(path);
            indexedResults[index] = graph.solvePathQuery(path).size();
            aStar.setHeuristic(0);
        }
        return new BruteTree(indexedResults, sequence);
    }

    @Override
    public int query(int[] query) {
        return inMemory.exact(query);
    }

    @Override
    protected long bytesOverhead() {
        return 0;
    }
}
