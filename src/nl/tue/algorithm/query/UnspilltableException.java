package nl.tue.algorithm.query;

import java.util.Arrays;

/**
 * Created by Dennis on 31-5-2016.
 */
public class UnspilltableException extends RuntimeException {
    public UnspilltableException(int[] query) {
        super("Cannot split query, to small: " + Arrays.toString(query));
    }
}
