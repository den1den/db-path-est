package nl.tue.io.datatypes;

import nl.tue.io.Parser;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Directed Graph
 * Created by Dennis on 17-5-2016.
 */
@Deprecated
public abstract class DGHashMap implements DirectedGraph {

    /**
     * Directed Graph with an array of hashmaps, with the label as index
     */
    public static class LabelArray extends DGHashMap {
        ArrayList<HashMap<Long, Set<Long>>> m;

        public LabelArray(Parser p) {
            if (!p.labelFitsInArrayLength()) {
                throw new OutOfMemoryError("Label does not fit in array");
            }
            m = new ArrayList<>((int) (p.highestLabel - 1));
            for (int i = 0; i < p.highestLabel - 1; i++) {
                m.set(i, new HashMap<>());
            }
            for (long[] tuple : p.tuples) {
                long src = tuple[0];
                int label = (int) tuple[1];
                long dst = tuple[2];
                HashMap<Long, Set<Long>> labelHM = m.get(label);
                Set<Long> dsts = labelHM.get(src);
                if(dsts == null){
                    dsts = new HashSet<>();
                    labelHM.put(src, dsts);
                }
                dsts.add(dst);
            }
        }

        public Set<Long> getAll(int label) {
            HashMap<Long, Set<Long>> labelHM = m.get(label);
            return labelHM.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
        }

        @Override
        public Set<Long> getAll(long label) {
            return getAll((int) label);
        }

        public Set<Long> get(int label, Set<Long> sources) {
            final HashMap<Long, Set<Long>> labelHM = m.get(label);
            return sources.stream()
                    .map(labelHM::get).flatMap(Collection::stream)
                    .collect(Collectors.toSet());
        }

        @Override
        public Set<Long> get(long label, Set<Long> sources) {
            return get((int) label, sources);
        }
    }

    /**
     * DirectedGraph that uses an double index as keys
     */
    public static class DoubleIndexedHashMap extends DGHashMap {
        HashMap<IndexLong, Set<Long>> m;

        public DoubleIndexedHashMap(Parser p) {
            this.m = new HashMap<>();
            for (long[] tuple : p.tuples) {
                long src = tuple[0];
                long label = tuple[1];
                IndexLong i = new IndexLong(src, label);
                long dst = tuple[2];

                Set<Long> dsts = m.get(i);
                if(dsts == null){
                    dsts = new HashSet<>();
                    m.put(i, dsts);
                }
                dsts.add(dst);
            }
        }

        @Override
        public Set<Long> getAll(long label) {
            Set<Long> set = new HashSet<>();
            for (Map.Entry<IndexLong, Set<Long>> entry : this.m.entrySet()) {
                if (entry.getKey().label == label) {
                    set.addAll(entry.getValue());
                }
            }
            return set;
        }

        @Override
        public Set<Long> get(long label, Set<Long> sources) {
            Set<Long> set = new HashSet<>();
            for (Long s : set) {
                IndexLong i = new IndexLong(s, label);
                set.addAll(m.get(i));
            }
            return set;
        }

        private static class IndexLong {
            long src, label;

            public IndexLong(long src, long label) {
                this.src = src;
                this.label = label;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof IndexLong)) return false;

                IndexLong indexLong = (IndexLong) o;

                if (src != indexLong.src) return false;
                return label == indexLong.label;

            }

            @Override
            public int hashCode() {
                int result = (int) (src ^ (src >>> 32));
                result = 31 * result + (int) (label ^ (label >>> 32));
                return result;
            }
        }
    }
}
