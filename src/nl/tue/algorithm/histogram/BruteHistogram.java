package nl.tue.algorithm.histogram;

import nl.tue.Utils;
import nl.tue.algorithm.PathResult;
import nl.tue.algorithm.paths.PathsOrdering;

import java.util.ArrayList;

/**
 * Created by Dennis on 4-6-2016.
 */
public class BruteHistogram extends AbstractHistogram<PathResult> {

    private BruteHistogram(PathsOrdering pathsOrdering, ArrayList<Integer> startRanges, PathResult[] estimations) {
        super(pathsOrdering, Utils.toArray(startRanges), estimations);
    }

    @Override
    public long getBytesUsed() {
        return super.estimations.length * PathResult.BYTES + super.startRanges.length * Integer.BYTES;
    }

    public static class BruteHistogramBuilder extends AbstractHistogramBuilder<PathResult, BruteHistogram> {
        public BruteHistogramBuilder() {
        }

        @Override
        public BruteHistogram createH(PathsOrdering pathsOrdering, ArrayList<Integer> startRanges, PathResult[] estimations) {
            return new BruteHistogram(pathsOrdering, startRanges, estimations);
        }

        @Override
        protected PathResult[] createArray(int length) {
            return new PathResult[length];
        }
    }
}
