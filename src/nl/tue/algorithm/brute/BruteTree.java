package nl.tue.algorithm.brute;

import nl.tue.algorithm.astar.AStart;
import nl.tue.algorithm.pathindex.PathSummary;
import nl.tue.algorithm.paths.LabelSequence;
import nl.tue.depricated.Estimator;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;
import nl.tue.io.graph.DirectedBackEdgeGraph;

import java.util.*;

/**
 * Created by dennis on 2-6-16.
 */
public class BruteTree implements Estimator<PathResult> {
    public int[] indexedResults;
    LabelSequence sequence;

    public int exact(int[] path){
        int index = sequence.get(path);
        int exact = indexedResults[index];
        return exact;
    }

    @Override
    public PathResult concatEstimations(PathResult left, PathResult right) {
        return null;
    }

    @Override
    public int combineEstimations(List<PathResult> sortedEs) {
        double avg = 0;
        for (PathResult p : sortedEs){
            avg += p.getTuples() / sortedEs.size();
        }
        return (int) avg;
    }

    @Override
    public Collection<PathResult> retrieveAllExactEstimations() {
        ArrayList<PathResult> copy = new ArrayList<>(indexedResults.length);
        Iterator<int[]> iterator = sequence.iterator();
        for (int i = 0; i < indexedResults.length; i++) {
            int[] path = iterator.next();
            copy.add(new PathResult(indexedResults[i], path));
        }
        return copy;
    }

    @Override
    public void buildSummary(Parser p, int k, double b) {
        int labels = p.getNLabels();
        AStart aStar = new AStart(labels, k);
        sequence = new LabelSequence(labels, k);
        indexedResults = new int[sequence.getMaxIndex()];

        DirectedBackEdgeGraph graph = new AdjacencyList(p);
        for (int[] path : aStar) {
            int index = sequence.get(path);
            indexedResults[index] = graph.solvePathQuery(path).size();
            aStar.setHeuristic(0);
        }
    }

    @Override
    public long getBytesUsed() {
        return indexedResults.length * Integer.SIZE + Integer.SIZE;
    }
}
