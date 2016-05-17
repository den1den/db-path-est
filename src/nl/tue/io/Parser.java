package nl.tue.io;

import java.io.*;
import java.util.*;

/**
 * Parses a file to a LinkedList of tuples (keeps duplicates)
 * Created by dennis on 17-5-16.
 */
public class Parser {

    public int lowestSrc = -1;
    public int lowestLabel = -1;
    public int lowestDest = -1;
    public int highestSrc = -1;
    public int highestLabel = -1;
    public int highestDest = -1;
    public LinkedList<int[]> tuples = new LinkedList<>();

    public void parse(File file) {
        try {
            parse(new FileReader(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void parse(Readable source) throws IOException {
        Scanner scanner = new Scanner(source);

        try {
            while (scanner.hasNextInt()) {
                int src, label, dest;

                src = scanner.nextInt();
                label = scanner.nextInt();
                dest = scanner.nextInt();
                foundTuple(src, label, dest);
            }
        } catch (NoSuchElementException e) {
            throw new IOException("Expected an integer in input file", e);
        }
    }

    private void foundTuple(int src, int label, int dest){
        if(lowestSrc == -1 || src < lowestSrc){
            lowestSrc = src;
        }
        if(highestSrc == -1 || src > highestSrc){
            highestSrc = src;
        }
        if(lowestLabel == -1 || label < lowestLabel){
            lowestLabel = label;
        }
        if(highestLabel == -1 || label > highestLabel){
            highestLabel = label;
        }
        if(lowestDest == -1 || dest < lowestDest){
            lowestDest = dest;
        }
        if(highestDest == -1 || dest > highestDest){
            highestDest = dest;
        }
        tuples.add(new int[]{src, label, dest});
    }

    static final Comparator<int[]> tupleLexiComparator = new Comparator<int[]>() {
        @Override
        public int compare(int[] o1, int[] o2) {
            int cmp = Integer.compare(o1[0], o2[0]);
            if (cmp != 0) return cmp;
            cmp = Integer.compare(o1[1], o2[1]);
            if (cmp != 0) return cmp;
            cmp = Integer.compare(o1[2], o2[2]);
            return cmp;
        }
    };

    public ArrayList<int[]> getOrdered() {
        ArrayList<int[]> ordered = new ArrayList<>(tuples);
        Collections.sort(ordered, tupleLexiComparator);
        return ordered;
    }

    public ArrayList<int[]> getOrderedUnique(){
        TreeSet<int[]> ordered = new TreeSet<>(tupleLexiComparator);
        ordered.addAll(tuples);
        if(true) throw new UnsupportedOperationException("arrays cannot be used to find duplicates");
        return new ArrayList<>(ordered);
    }

    public boolean labelFitsInByte(){
        return this.highestLabel <= Byte.MAX_VALUE;
    }
}
