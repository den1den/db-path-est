package nl.tue.algorithm.brute;

import nl.tue.algorithm.Estimator;
import nl.tue.io.Parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dennis on 2-6-16.
 */
public class BruteTree implements Estimator<PathResult> {
    ArrayList<Integer> indexMap;

    @Override
    public PathResult concatEstimations(PathResult left, PathResult right) {
        return null;
    }

    @Override
    public int combineEstimations(List<PathResult> sortedEs) {
        return 0;
    }

    @Override
    public Collection<PathResult> retrieveAllExactEstimations() {
        return null;
    }

    @Override
    public void buildSummary(Parser p, int k, double b) {

    }

    @Override
    public long getBytesUsed() {
        return 0;
    }
}
