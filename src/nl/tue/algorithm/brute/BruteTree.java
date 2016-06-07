package nl.tue.algorithm.brute;

import nl.tue.MemoryConstrained;
import nl.tue.algorithm.PathResult;
import nl.tue.algorithm.paths.LabelSequence;

import java.util.List;

/**
 * Created by dennis on 2-6-16.
 */
public class BruteTree implements MemoryConstrained {
    int[] indexedResults;
    LabelSequence sequence;

    public BruteTree(int[] indexedResults, LabelSequence sequence) {
        this.indexedResults = indexedResults;
        this.sequence = sequence;
    }

    public int exact(int[] path) {
        int index = sequence.get(path);
        int exact = indexedResults[index];
        return exact;
    }

    public int combineEstimations(List<PathResult> sortedEs) {
        double avg = 0;
        for (PathResult p : sortedEs) {
            avg += p.getTuples() / sortedEs.size();
        }
        return (int) avg;
    }

    @Override
    public long getBytesUsed() {
        return indexedResults.length * Integer.SIZE + Integer.SIZE;
    }
}
