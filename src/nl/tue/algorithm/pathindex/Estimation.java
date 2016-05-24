package nl.tue.algorithm.pathindex;

/**
 * Created by Nathan on 5/24/2016.
 */
public class Estimation {

    private final int start;
    private final int tuples;
    private final int end;

    public Estimation(int start, int tuples, int end) {
        this.start = start;
        this.tuples = tuples;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getTuples() {
        return tuples;
    }

    public int getEnd() {
        return end;
    }
}
