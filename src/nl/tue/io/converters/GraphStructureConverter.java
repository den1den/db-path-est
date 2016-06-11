package nl.tue.io.converters;

import nl.tue.io.TupleList;
import java.util.*;

/**
 * Created by Dennis on 11-6-2016.
 */
public class GraphStructureConverter {
    TupleList original;
    private HashMap<Integer, ArrayList<Tuple>> incoming = new HashMap<>();
    private HashMap<Integer, ArrayList<Tuple>> outgoing = new HashMap<>();
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
    /**
     * Keeps on merging nodes until they are reduced to @number
     */
    public void MergeZwei(int number) {
        ArrayList<Tuple> nodesAndEdges = new ArrayList<>();
        ArrayList<Integer> uniqueNodes = new ArrayList<>();

        for (int[] input : this) {
            if (!uniqueNodes.contains(input[0])) {
                uniqueNodes.add(input[0]);
            }
            if (!uniqueNodes.contains(input[2])) {
                uniqueNodes.add(input[2]);
            }

            nodesAndEdges.add(new Tuple(input[0], input[1], input[2]));

        }

        Iterator<Integer> keySetIterator = uniqueNodes.iterator();

        while (keySetIterator.hasNext()) {
            int key = keySetIterator.next();
            ArrayList<Tuple> outg = new ArrayList<>();
            ArrayList<Tuple> incom = new ArrayList<>();
            for (Tuple ds : nodesAndEdges) {
                if (ds.source == key) {
                    outg.add(ds);
                }
                if (ds.target == key) {
                    incom.add(ds);
                }
            }
            outgoing.put(key, outg);
            incoming.put(key, incom);

        }


        Random rnd = new Random();

        while (uniqueNodes.size() > number) {
            int firstIndex = rnd.nextInt(uniqueNodes.size() - 1);
            int secondIndex = rnd.nextInt(uniqueNodes.size() - 1);

            if (firstIndex != secondIndex) {

                int one = uniqueNodes.get(firstIndex);
                int two = uniqueNodes.get(secondIndex);
                ArrayList<Tuple> nodeIncoming = incoming.get(one);
                ArrayList<Tuple> nodeOutgoing = outgoing.get(one);
                for (Tuple temp : nodeIncoming) {
                    temp.target = two;
                    incoming.get(two).add(temp);
                }
                for (Tuple temp : nodeOutgoing) {
                    temp.source = two;
                    outgoing.get(two).add(temp);
                }

                incoming.remove(one);
                outgoing.remove(one);
                uniqueNodes.remove(new Integer(one));

            }

        }
    }

}
