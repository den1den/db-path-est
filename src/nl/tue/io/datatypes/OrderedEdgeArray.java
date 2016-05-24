package nl.tue.io.datatypes;

import nl.tue.io.Parser;

import java.util.*;

/**
 * An ordered list of all edges with all duplicates removed
 * Created by Dennis on 17-5-2016.
 */
@Deprecated
public class OrderedEdgeArray implements DirectedGraph {

    public static final List<Integer> NO = Collections.emptyList();
    public static final Set<Long> EMPTY = Collections.emptySet();

    final ArrayList<Long> srcs;
    final ArrayList<Long> labels;
    final ArrayList<Long> dests;

    public static OrderedEdgeArray construct(Parser p) {
        ArrayList<long[]> ordered = p.getOrderedUnique(Parser.CompLabelSourceDestination);
        int size = ordered.size();

        ArrayList<Long> srcs = new ArrayList<>(size);
        ArrayList<Long> labels = new ArrayList<>(size);
        ArrayList<Long> dests = new ArrayList<>(size);
        for (long[] tuple : ordered) {
            srcs.add(tuple[0]);
            labels.add(tuple[1]);
            dests.add(tuple[2]);
        }
        return new OrderedEdgeArray(srcs, labels, dests);
    }

    private OrderedEdgeArray(ArrayList<Long> srcs, ArrayList<Long> labels, ArrayList<Long> dests) {
        this.srcs = srcs;
        this.labels = labels;
        this.dests = dests;
    }

    public long getSrc(int index) {
        return srcs.get(index);
    }

    public long getLabel(int index) {
        return labels.get(index);
    }

    public long getDst(int index) {
        return dests.get(index);
    }

    int lowestLabelIndex(long label) {
        int b = Collections.binarySearch(labels, label);
        long labelB = labels.get(b);
        if (labelB != label) {
            return -1;
        }
        while (--b > 0) {
            labelB = labels.get(b);
            if (labelB != label) {
                break;
            }
        }
        return b + 1;
    }

    @Override
    public Set<Long> getAll(long label) {
        int index = lowestLabelIndex(label);
        if (index == -1) {
            return EMPTY;
        }
        return getDestinations(filterLinear(index, label));
    }

    Set<Long> getDestinations(List<Integer> incides) {
        Set<Long> result = new HashSet<>();
        for (Integer index : incides) {
            result.add(dests.get(index));
        }
        return result;
    }

    List<Integer> filterLinear(int startIndex, long whileLabel) {
        List<Integer> indices = new ArrayList<>();
        int index = startIndex;
        long l;
        do {
            indices.add(index);

            index++;
            if (index >= getSize()) {
                break;
            }

            l = labels.get(index);
        } while (l == whileLabel);
        return indices;
    }

    List<Integer> filterLinear(int startIndex, long whileLabel, long source) {
        int index = startIndex;
        long l;
        long src;
        // First look for destination
        do {
            src = srcs.get(index);
            if (src == source) {
                break;
            }

            index++;
            if (index >= getSize()) {
                return NO;
            }
            if (labels.get(index) != whileLabel) {
                return NO;
            }
        } while (true);
        // Found it
        List<Integer> indices = new ArrayList<>();
        indices.add(index);
        do {
            index++;
            if (index >= getSize()) {
                break;
            }
            if (labels.get(index) != whileLabel) {
                break;
            }
            src = srcs.get(index);
            if (src != source) {
                break;
            }
        } while (true);
        return indices;
    }

    @Override
    public Set<Long> get(long label, Set<Long> sources) {
        // TODO: Might be a good idea to also accept sorted `sources`
        int index = lowestLabelIndex(label);
        if (index == -1) {
            return EMPTY;
        }
        List<Integer> indices = new ArrayList<>();
        for (long src : sources) {
            indices.addAll(filterLinear(index, label, src));
        }
        return getDestinations(indices);
    }

    public long getSize() {
        return srcs.size();
    }
}
