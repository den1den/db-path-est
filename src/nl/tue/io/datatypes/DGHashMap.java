package nl.tue.io.datatypes;

import nl.tue.io.Parser;

import java.util.*;

/**
 * Directed Graph
 * Created by Dennis on 17-5-2016.
 */
public interface DGHashMap {
    /**
     * Get the destinations for a given source and label
     * @param src
     * @param label
     * @return Set of destinations, or the empty set if the (src, label) is not present
     */
    Set<Integer> getDestinations(int src, int label);

    /**
     * Directed Graph with an array of hashmaps, with the label as index
     */
    class LabelArray implements DGHashMap{
        HashMap<Integer, Set<Integer>>[] m;

        public LabelArray(Parser p) {
            m = new HashMap[p.highestLabel + 1];
            for (int i = 0; i < m.length; i++) {
                m[i] = new HashMap<>();
            }
            for (int[] tuple : p.tuples){
                int src = tuple[0];
                int label = tuple[1];
                int dst = tuple[2];
                Set<Integer> dsts = m[label].get(src);
                if(dsts == null){
                    dsts = new HashSet<>();
                    m[label].put(src, dsts);
                }
                dsts.add(dst);
            }
        }

        @Override
        public Set<Integer> getDestinations(int src, int label) {
            HashMap<Integer, Set<Integer>> dg = m[label];
            return dg.getOrDefault(src, Collections.EMPTY_SET);
        }
    }

    @Deprecated
    class LabelHashMap implements DGHashMap{
        HashMap<Integer, HashMap<Integer, Set<Integer>>> m;

        public LabelHashMap(Parser p) {
            m = new HashMap<>();
            for (int[] tuple : p.tuples){
                int src = tuple[0];
                int label = tuple[1];
                int dst = tuple[2];

                HashMap<Integer, Set<Integer>> stod = m.get(label);
                if(stod == null){
                    stod = new HashMap<>();
                    m.put(label, stod);
                }

                Set<Integer> dsts = stod.get(src);
                if(dsts == null){
                    dsts = new HashSet<>();
                    stod.put(src, dsts);
                }
                dsts.add(dst);
            }
        }

        @Override
        public Set<Integer> getDestinations(int src, int label) {
            HashMap<Integer, Set<Integer>> stod = m.get(label);
            if(stod == null){
                return Collections.EMPTY_SET;
            }
            return stod.getOrDefault(src, Collections.EMPTY_SET);
        }
    }

    @Deprecated
    class SrcHashMap implements DGHashMap{
        HashMap<Integer, HashMap<Integer, Set<Integer>>> m;

        public SrcHashMap(Parser p) {
            m = new HashMap<>();
            for (int[] tuple : p.tuples){
                int src = tuple[0];
                int label = tuple[1];
                int dst = tuple[2];

                HashMap<Integer, Set<Integer>> stod = m.get(label);
                if(stod == null){
                    stod = new HashMap<>();
                    m.put(label, stod);
                }

                Set<Integer> dsts = stod.get(src);
                if(dsts == null){
                    dsts = new HashSet<>();
                    stod.put(src, dsts);
                }
                dsts.add(dst);
            }
        }

        @Override
        public Set<Integer> getDestinations(int src, int label) {
            HashMap<Integer, Set<Integer>> stod = m.get(label);
            if(stod == null){
                return Collections.EMPTY_SET;
            }
            return stod.getOrDefault(src, Collections.EMPTY_SET);
        }
    }

    /**
     * DirectedGraph that uses an double index as keys
     */
    class DoubleIndexedHashMap implements DGHashMap{
        HashMap<Index, Set<Integer>> m;

        public DoubleIndexedHashMap(Parser p) {
            this.m = new HashMap<>();
            for (int[] tuple : p.tuples) {
                int src = tuple[0];
                int label = tuple[1];
                Index i = new Index(src, label);
                int dst = tuple[2];

                Set<Integer> dsts = m.get(i);
                if(dsts == null){
                    dsts = new HashSet<>();
                    m.put(i, dsts);
                }
                dsts.add(dst);
            }
        }

        @Override
        public Set<Integer> getDestinations(int src, int label) {
            return m.getOrDefault(new Index(src, label), Collections.EMPTY_SET);
        }

        private static class Index{
            int a, b;

            private Index(int a, int b) {
                this.a = a;
                this.b = b;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Index index = (Index) o;

                if (a != index.a) return false;
                return b == index.b;

            }

            @Override
            public int hashCode() {
                int result = a;
                result = 31 * result + b;
                return result;
            }
        }
    }
}
