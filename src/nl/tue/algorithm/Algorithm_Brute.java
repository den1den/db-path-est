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
        BruteTree bt = new BruteTree();
        bt.buildSummary(p, maximalPathLength, budget);
        return bt;
    }

    @Override
    public int query(int[] query) {
        return inMemory.exact(query);
    }

    @Override
    public long getBytesUsed() {
        throw new UnsupportedOperationException();
    }
}
