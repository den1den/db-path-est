package nl.tue.io.converters;

import nl.tue.Utils;
import nl.tue.io.TupleList;

import java.util.*;

/**
 * Created by Dennis on 10-6-2016.
 */
public class LabelConverter {
    TupleList original;

    public LabelConverter(TupleList original) {
        this.original = original;
    }

    /**
     * Combine labels
     * @param mapping mapping of labels i to j, mapping[i] = j
     * @return
     */
    public TupleList convert(int[] mapping){
        TupleList.Meta m = original.new Meta();
        assert m.lowestLabel > 0;
        assert m.highestLabel <= mapping.length - 1;
        return doMapping(mapping);
    }

    private TupleList doMapping(int[] mapping) {
        TupleList newRT = new TupleList(original.size());
        for (int[] tuple : original){
            int src = tuple[0];
            int oldL = tuple[1];
            int dst = tuple[2];
            int newL = mapping[oldL];
            newRT.add(src, newL, dst);
        }
        return newRT;
    }

    public TupleList half() {
        return devide(2);
    }

    public TupleList devide(int devide){
        TupleList.Meta m = original.new Meta();
        assert m.lowestLabel > 0;
        int[] mapping = new int[(int) (m.highestLabel + 1)];
        for (int label = 0; label < m.highestLabel; label++) {
            mapping[label] = label / devide;
        }
        return doMapping(mapping);
    }

    public TupleList shuffle() {
        return shuffle(new Random(312321L));
    }

    public TupleList shuffle(Random rnd){
        TupleList.Meta m = original.new Meta();
        ArrayList<Integer> mapping = new ArrayList<>();
        assert m.lowestLabel == 0;
        for (int L = 0; L <= m.highestLabel; L++) {
            mapping.add(L);
        }
        Collections.shuffle(mapping, rnd);
        return doMapping(Utils.toArray(mapping));
    }
}
