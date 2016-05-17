package nl.tue.io.datatypes;

import nl.tue.io.Parser;

import java.util.ArrayList;

/**
 * An ordered list of all edges with all duplicates removed
 * Created by Dennis on 17-5-2016.
 */
public interface OrderedEdgeArray {

    int getSrc(int index);
    int getLabel(int index);
    int getDst(int index);
    int size();

    class Integers implements OrderedEdgeArray{
        public final int[] srcs;
        public final int[] labels;
        public final int[] dests;

        public Integers(Parser p) {
            this.srcs = new int[p.tuples.size()];
            this.labels = new int[p.tuples.size()];
            this.dests = new int[p.tuples.size()];

            ArrayList<int[]> ordered = p.getOrdered();

            int i = 0;
            for (int[] tuple : ordered){
                srcs[i] = tuple[0];
                labels[i] = tuple[1];
                dests[i] = tuple[2];
            }
        }

        @Override
        public int getSrc(int index) {
            return srcs[index];
        }

        @Override
        public int getLabel(int index) {
            return labels[index];
        }

        @Override
        public int getDst(int index) {
            return dests[index];
        }

        @Override
        public int size() {
            return srcs.length;
        }
    }

    class LabelBytes implements OrderedEdgeArray{
        public final int[] srcs;
        public final byte[] labels;
        public final int[] dests;

        public LabelBytes(Parser p) {
            this.srcs = new int[p.tuples.size()];
            this.labels = new byte[p.tuples.size()];
            this.dests = new int[p.tuples.size()];

            ArrayList<int[]> ordered = p.getOrdered();

            int i = 0;
            for (int[] tuple : ordered){
                srcs[i] = tuple[0];
                labels[i] = (byte) tuple[1];
                dests[i] = tuple[2];
            }
        }

        @Override
        public int getSrc(int index) {
            return srcs[index];
        }

        @Override
        public int getLabel(int index) {
            return labels[index];
        }

        @Override
        public int getDst(int index) {
            return dests[index];
        }

        @Override
        public int size() {
            return srcs.length;
        }
    }
}
