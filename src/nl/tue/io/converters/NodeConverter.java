package nl.tue.io.converters;

import nl.tue.algorithm.paths.IntSet;
import nl.tue.io.TupleList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by Dennis on 14-6-2016.
 */
public class NodeConverter {
    TupleList original;

    public NodeConverter(TupleList original) {
        this.original = original;
    }

    public TupleList filterNodes(double factor){
        List<Integer> nodes = new ArrayList<>(original.new Meta().nodes);
        return filterNodes(original, nodes, factor);
    }

    public TupleList filterNodes(TupleList original, List<Integer> nodes, double factor) {
        IntSet newNodes = shrinkNodes(nodes, factor);
        TupleList tupleList = new TupleList(original.size());
        for (int[] tuple : original){
            int src = tuple[0];
            int dst = tuple[2];
            if(newNodes.contains(src) && newNodes.contains(dst)){
                tupleList.add(tuple);
            }
        }
        return tupleList;
    }

    private static IntSet shrinkNodes(List<Integer> nodes, double factor) {
        Collections.shuffle(nodes);
        nodes = nodes.subList(0, (int) (nodes.size() * factor));
        IntSet newNodes = new IntSet();
        newNodes.addAll(nodes);
        return newNodes;
    }
}
