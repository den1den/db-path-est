package nl.tue.io;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.*;
import java.util.function.LongToIntFunction;

/**
 * Parses a file to a LinkedList of tuples (keeps duplicates)
 * Created by dennis on 17-5-16.
 */
public class Parser {

    public long lowestSrc = -1;
    public long lowestLabel = -1;
    public long lowestDest = -1;
    public long highestSrc = -1;
    public long highestLabel = -1;
    public long highestDest = -1;

    /**
     * Map that contains the edge mapping which maps edges from the world to the application domain. Edges in the
     * application domain start at 0, are consecutive and go up to n - 1. Where n is the number of unique labels. Back
     * edges are assigned from n to 2n - 1. Where the back edge for a label l can be found by doing l + n.
     */
    private Map<String, Integer> edgeMappings = new HashMap<>();


    private Set<Long> labels = new HashSet<>();

    public LinkedList<long[]> tuples = new LinkedList<>();
    public LinkedList<long[]> invertedTuples = new LinkedList<>();
    public LinkedList<long[]> combinedList = new LinkedList<>();

    public void parse(String pathname) throws IOException {
        File f = new File(pathname);
        if (!f.isFile()) {
            throw new FileNotFoundException(f.getAbsolutePath());
        }
        parse(f);
    }

    public void parse(File file) throws IOException {
        parse(new FileReader(file));
    }

    public void parse(Readable source) throws IOException {
        Scanner scanner = new Scanner(source);

        try {
            while (scanner.hasNextLong()) {
                long src, label, dest;

                src = scanner.nextLong();
                label = scanner.nextLong();
                dest = scanner.nextLong();
                foundTuple(src, label, dest);
            }

            Map<String, Integer> reversedMappings = new HashMap<>();

            for(String mappingKey : this.edgeMappings.keySet()) {
                int mappedTo = this.edgeMappings.get(mappingKey);

                reversedMappings.put(mappingKey.replace("+", "-"), mappedTo + this.labels.size());
            }

            this.edgeMappings.putAll(reversedMappings);

        } catch (NoSuchElementException e) {
            throw new IOException("Expected an long in input file", e);
        }
    }
    public void inverse(File file) throws IOException {
        inverse(new FileReader(file));
    }
    public void inverse(Readable source) throws IOException {
        Scanner scanner = new Scanner(source);

        try {
            while (scanner.hasNextLong()) {
                long src, label, dest;

                src = scanner.nextLong();
                label = scanner.nextLong();
                label = -label; //TODO fix this
                dest = scanner.nextLong();
                invertedTuples.add(new long[]{dest, label, src});;
            }
        } catch (NoSuchElementException e) {
            throw new IOException("Expected an long in input file", e);
        }
    }
    public void writeToFile(String  filename) throws IOException {
        FileWriter fw = new FileWriter(filename);
        combinedList.addAll(tuples);
        combinedList.addAll(invertedTuples);
        for (long[] longArray: combinedList
             ) {
            for (int i =0;i < longArray.length;i++)
            {
                fw.append(String.valueOf(longArray[i]) + " ");
            }
            fw.append(System.lineSeparator());

        }

        fw.close();
    }
    private void assertConsecutiveLabels() {
        assert lowestLabel == 0;
        assert highestLabel == this.labels.size() - 1;
    }

    public int getNLabels() {
        assertConsecutiveLabels();
        return this.labels.size();
    }

    private void foundTuple(long src, long label, long dest) {
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

        if(!edgeMappings.containsKey(label)) {
            edgeMappings.put("+" + label, this.labels.size());
        }


        labels.add(label);

        tuples.add(new long[]{src, label, dest});
    }

    public static final Comparator<long[]> CompSourceLabelDestination = new Comparator<long[]>() {
        @Override
        public int compare(long[] o1, long[] o2) {
            int cmp = Long.compare(o1[0], o2[0]);
            if (cmp != 0) return cmp;
            cmp = Long.compare(o1[1], o2[1]);
            if (cmp != 0) return cmp;
            cmp = Long.compare(o1[2], o2[2]);
            return cmp;
        }
    };

    public static final Comparator<long[]> CompLabelSourceDestination = new Comparator<long[]>() {
        @Override
        public int compare(long[] o1, long[] o2) {
            int cmp = Long.compare(o1[1], o2[1]);
            if (cmp != 0) return cmp;
            cmp = Long.compare(o1[0], o2[0]);
            if (cmp != 0) return cmp;
            cmp = Long.compare(o1[2], o2[2]);
            return cmp;
        }
    };

    public ArrayList<long[]> getOrderedUnique(Comparator<long[]> comparator) {
        TreeSet<long[]> ordered = new TreeSet<>(comparator);
        ordered.addAll(tuples);

        return new ArrayList<>(ordered);
    }

    public boolean labelFitsInByte(){
        return this.highestLabel <= Byte.MAX_VALUE;
    }

    public boolean labelFitsInArrayLength() {
        return this.highestLabel < Integer.MAX_VALUE - 8; // See ArrayList
    }

    public int getNEdges() {
        return tuples.size();
    }

    public Map<String, Integer> getEdgeMappings() {
        return edgeMappings;
    }
}
