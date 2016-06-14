package nl.tue.io;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The rawest form of a edge list
 * A wrapper of <code>ArrayList<int[]> tuples</code>
 */
public class TupleList extends AbstractList<int[]>{

    final ArrayList<int[]> tuples;

    public TupleList() {
        tuples = new ArrayList<>();
    }

    public TupleList(int initialCapacity) {
        tuples = new ArrayList<>(initialCapacity);
    }

    public TupleList(Collection<? extends int[]> c) {
        checkAll(c);
        tuples = new ArrayList<>(c);
    }

    public TupleList(Readable source) {
        this();
        readFromReadable(source);
    }

    public TupleList(File file) {
        this();
        try {
            readFromReadable(new BufferedReader(new FileReader(file)));
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    private static void checkAll(Collection<? extends int[]> cs) {
        for (int[] c : cs){
            assert c.length == 3;
        }
    }

    @Override
    public void add(int index, int[] element) {
        if(element.length != 3){
            throw new IllegalArgumentException();
        }
        tuples.add(index, element);
    }

    @Override
    public boolean add(int[] element) {
        if(element.length != 3){
            throw new IllegalArgumentException();
        }
        return tuples.add(element);
    }

    public boolean add(int src, int label, int dest) {
        return tuples.add(new int[]{src, label, dest});
    }

    @Override
    public int[] set(int index, int[] element) {
        assert element.length == 3;
        return tuples.set(index, element);
    }

    @Override
    public int[] get(int index) {
        return tuples.get(index);
    }

    public Tuple getObj(int index){
        return new Tuple(get(index));
    }

    @Override
    public int[] remove(int index) {
        return tuples.remove(index);
    }

    @Override
    public void clear() {
        tuples.clear();
    }

    /**
     * Read from some readable object
     * @param readable
     */
    public void readFromReadable(Readable readable) {
        Scanner scanner = new Scanner(readable);
        while (scanner.hasNextLong()) {
            int src, label, dest;

            src = scanner.nextInt();
            label = scanner.nextInt();
            dest = scanner.nextInt();
            add(src, label, dest);
        }
    }

    public void writeToCSVFile(String filename){
        try {
            writeToCSV(new BufferedWriter(new FileWriter(filename)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeToCSVFile(File filename){
        try {
            writeToCSV(new BufferedWriter(new FileWriter(filename)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeToCSV(BufferedWriter writer) throws IOException {
        writer.write("Source,Label,Target");
        writer.newLine();
        Iterator<int[]> iterator = this.iterator();
        do {
            int[] i = iterator.next();
            writer.append(String.format("%d,%d,%d", i[0], i[1], i[2]));
            if(iterator.hasNext()){
                writer.newLine();
            } else {
                break;
            }
        } while (true);
        writer.close();
    }

    @Override
    public int size() {
        return tuples.size();
    }

    public TupleList deepCopy(){
        TupleList copy = new TupleList(tuples.size());
        copy.tuples.addAll(tuples.stream().map(t -> Arrays.copyOf(t, 3)).collect(Collectors.toList()));
        return copy;
    }

    /**
     * Linear meta data
     */
    public class Meta {
        public long lowestSrc = -1;
        public long lowestLabel = -1;
        public long lowestDest = -1;
        public long highestSrc = -1;
        public long highestLabel = -1;
        public long highestDest = -1;
        public Set<Integer> labels;
        public Set<Integer> nodes;

        public Meta() {
            labels = new TreeSet<>();
            nodes = new HashSet<>();
            for (int[] tuple : TupleList.this){
                int src = tuple[0], label = tuple[1], dest = tuple[2];
                limits(src, label, dest);
                nodes.add(src);
                labels.add(label);
                nodes.add(dest);
            }
        }

        private void limits(int src, int label, int dest) {
            if (lowestSrc == -1 || src < lowestSrc) {
                lowestSrc = src;
            }
            if (highestSrc == -1 || src > highestSrc) {
                highestSrc = src;
            }
            if (lowestLabel == -1 || label < lowestLabel) {
                lowestLabel = label;
            }
            if (highestLabel == -1 || label > highestLabel) {
                highestLabel = label;
            }
            if (lowestDest == -1 || dest < lowestDest) {
                lowestDest = dest;
            }
            if (highestDest == -1 || dest > highestDest) {
                highestDest = dest;
            }
        }

        @Override
        public String toString() {
            return String.format("Tuple List {%n" +
                            " src from id = %d to %d%n" +
                            " labels = %s%n" +
                            " dst from id = %d to %d%n" +
                            " nodes nodes: %s%n" +
                            "}%n",
                    lowestSrc, highestSrc,
                    labels.size() <= 10 ? labels.toString() : "total = " + labels.size(),
                    lowestDest, highestDest,
                    nodes.size() <= 10 ? nodes.toString() : "total = " + nodes.size()
            );
        }
    }

    /**
     * Get some fast linear meta data for this edge list
     * @return
     */
    public Meta getMetaData(){
        return new Meta();
    }
}
