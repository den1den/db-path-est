package nl.tue.algorithm.histogram;

import nl.tue.Utils;
import nl.tue.algorithm.PathResult;
import nl.tue.algorithm.paths.PathsOrdering;

import java.util.ArrayList;

/**
 * Created by Dennis on 4-6-2016.
 */
public class BruteHistogram extends AbstractHistogram<PathResult> {

    public BruteHistogram(int[] startRanges, PathResult[] estimations) {
        super(startRanges, estimations);
    }

    @Override
    public long getBytesUsed() {
        return super.estimations.length * PathResult.BYTES + super.startRanges.length * Integer.BYTES;
    }

    public static class BruteHistogramBuilder extends AbstractHistogramBuilder<PathResult, BruteHistogram> {

        public BruteHistogramBuilder(Joiner.AbstractJoiner<PathResult> joiner) {
            super(joiner);
        }

        @Override
        protected BruteHistogram createH(ArrayList<Integer> startRanges, PathResult[] estimations) {
            return null;
        }

        @Override
        protected PathResult[] createArray(int length) {
            return new PathResult[length];
        }
    }
}
