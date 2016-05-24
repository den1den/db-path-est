package nl.tue.algorithm.pathindex;

import nl.tue.algorithm.Algorithm;
import nl.tue.io.Parser;

import java.util.List;
import java.util.Queue;

/**
 * Created by Nathan on 5/24/2016.
 */
public class IndexQueryEstimator implements Algorithm {

    /**
     * 2 Bytes for a path index, one byte for start, stop and end.
     * Be aware that this is a rough overestimation.
     */
    private static final double STORAGE_PER_PATH_ESTIMATE = 2 + 1 + 1 + 1;

    /**
     * Overhead of the array + this class.
     */
    private static final double OVERHEAD = (12 + 4) + 16;

    private byte[] optimizedGraph;


    @Override
    public void buildSummary(Parser p, int k, double b) {

        int maxNoOfPaths = (int)Math.floor((b - OVERHEAD) / STORAGE_PER_PATH_ESTIMATE);

        Queue<PathSummary> fullSummary = IndexQueryEstimatorFactory.construct(p, k, maxNoOfPaths);

        /**
         * Serialize the fuck out of this.
         */
    }

    @Override
    public int query(List<Long> query) {
        return 0;
    }

    @Override
    public long getBytesUsed() {
        return 0;
    }
}
