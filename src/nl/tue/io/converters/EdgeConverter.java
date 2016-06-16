package nl.tue.io.converters;

import nl.tue.io.TupleList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Dennis on 10-6-2016.
 */
public class EdgeConverter {
    TupleList original;

    public EdgeConverter(TupleList original) {
        this.original = original;
    }

    public List<TupleList> devide(int parts) {
        double size = original.size();
        int subsize = (int) (size / parts);
        TupleList shuffled = new TupleList(original);
        Collections.shuffle(original);

        List<TupleList> result = new ArrayList<>(parts);
        for (int i = 0; i < parts; i++) {
            List<int[]> subList = shuffled.subList(i * subsize, i * subsize + subsize);
            result.add(new TupleList(subList));
        }
        return result;
    }

    public TupleList combine(List<TupleList> subList) {
        TupleList result = new TupleList(subList.stream().mapToInt(s -> s.size()).sum());
        for (TupleList part : subList) {
            result.addAll(part);
        }
        return result;
    }
}
