package nl.tue.io.converters;

import nl.tue.io.TupleList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dennis on 11-6-2016.
 */
public class GraphStructureConverter {
    TupleList original;

    public GraphStructureConverter(TupleList original) {
        this.original = original;
    }

    /**
     * Perform a merge on this edge list
     */
    public TupleList merge() {
        TupleList newTL = original.deepCopy();

        int source, label, target;
        int listCounter = 0;
        List<int[]> newTobeAdded = new ArrayList<>();
        ArrayList<Integer> indexesTobeRemoved = new ArrayList<>();
        for (int[] input : newTL) {
            source = input[0];
            label = input[1];
            target = input[2];

            int counter = 0;
            for (int[] comparison : newTL) {
                if (target == comparison[0] && label == comparison[1] && comparison[0] != comparison[2]) {
                    int[] temp = {source, label, comparison[2]};
                    newTobeAdded.add(temp);
                    indexesTobeRemoved.add(counter);
                    indexesTobeRemoved.add(listCounter);
                }
                counter++;
            }
            listCounter++;
        }

        int cpt = 0;
        Iterator<int[]> it = newTL.iterator();
        while (it.hasNext()) {
            it.next();
            if (indexesTobeRemoved.contains(cpt)) {
                it.remove();
            }
            cpt++;
        }
        newTL.addAll(newTobeAdded);
        return newTL;
    }
}
