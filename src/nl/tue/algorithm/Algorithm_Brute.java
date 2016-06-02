package nl.tue.algorithm;

import nl.tue.algorithm.brute.BruteTree;
import nl.tue.io.Parser;

/**
 * Algorithm based on Path Summaries
 * <ol>
 *     <li>Stores PathInfo for each subQuery until some memory limit is reached</li>
 * </ol>
 * @author dennis
 */
public class Algorithm_Brute extends Algorithm<BruteTree> {

    @Override
    BruteTree build(Parser p, int maximalPathLength, long budget) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long query(int[] query) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getBytesUsed() {
        throw new UnsupportedOperationException();
    }
}
