package nl.tue.algorithm.query;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Dennis on 31-5-2016.
 */
public class QuerySplitter implements Iterator<int[][]> {
    final int[] QUERY;
    int index;
    final int HALF;

    public QuerySplitter(int[] query) {
        if(query.length <= 1){
            throw new UnspilltableException(query);
        }
        this.QUERY = query;
        index = 0;
        HALF = query.length / 2 + query.length % 2;
    }

    @Override
    public boolean hasNext() {
        return index < QUERY.length - 1;
    }

    @Override
    public int[][] next() {
        int splitIndex = getSplitIndex();

        int[] head = new int[splitIndex];
        System.arraycopy(QUERY, 0, head, 0, head.length);
        int[] tail = new int[QUERY.length - head.length];
        System.arraycopy(QUERY, head.length, tail, 0, tail.length);

        index++;

        return new int[][]{ head, tail };
    }

    int getSplitIndex() {
        int offset;
        if (index == 0) {
            offset = 0;
        } else if (index % 2 == 0) {
            if (QUERY.length % 2 == 0) {
                offset = -(index + 1) / 2;
            } else {
                offset = (index + 1) / 2;
            }
        } else {
            if (QUERY.length % 2 == 0) {
                offset = (index + 1) / 2;
            } else {
                offset = -(index + 1) / 2;
            }
        }
        return HALF + offset;
    }
}
