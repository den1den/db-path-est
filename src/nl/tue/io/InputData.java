package nl.tue.io;

import java.util.LinkedList;

/**
 * De unprocessed input data set
 *
 * Created by dennis on 17-5-16.
 */
public class InputData {
    public int[] src;
    public int[] label;
    public int[] dest;

    /**
     * The number of labels in the set
     */
    public int LABELS;

    public int size(){
        return this.src.length;
    }

    public class DirectedGraph{
    }
}
