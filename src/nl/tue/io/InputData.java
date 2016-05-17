package nl.tue.io;

/**
 * De unprocessed input data set
 *
 * Created by dennis on 17-5-16.
 */
public class InputData {
    InputTuple[] tuples;

    InputData(InputTuple[] tuples) {
        this.tuples = tuples;
    }

    public int size(){
        return this.tuples.length;
    }

    class InputTuple{
        int src;
        int label;
        int dest;
    }
}
