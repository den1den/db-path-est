package nl.tue.io;

import java.io.*;
import java.util.*;

/**
 * Parses a file to a LinkedList of tuples (keeps duplicates)
 * Created by dennis on 17-5-16.
 */
public class Parser {

    public long lowestSrc = -1;
    public long lowestLabel = -1;
    public long lowestDest = -1;
    public long highestSrc = -1;
    public long highestDest = -1;

    /**
     * Map that contains the edge mapping which maps edges from the world to the application domain. Edges in the
     * application domain start at 0, are consecutive and go up to n - 1. Where n is the number of unique labels. Back
     * edges are assigned from n to 2n - 1. Where the back edge for a label l can be found by doing l + n.
     */
    private Map<String, Integer> edgeMappings = new HashMap<>();

    public List<int[]> tuples = new ArrayList<>();
    public List<int[]> invertedTuples = new ArrayList<>();
    public List<int[]> combinedList = new ArrayList<>();
    public LinkedList<long[]> pathList = new LinkedList<>();

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

        List<int[]> edges = new LinkedList<>();

        try {
            while (scanner.hasNextLong()) {
                int src, label, dest;

                src = scanner.nextInt();
                label = scanner.nextInt();
                dest = scanner.nextInt();
                edges.add(new int[]{src, label, dest});
            }

        } catch (NoSuchElementException e) {
            throw new IOException("Expected an long in input file", e);
        }

        parse(edges);
    }

    public void parse(List<int[]> edges) {

        for(int[] edge : edges) {
            foundTuple(edge[0], edge[1], edge[2]);
        }

        Map<String, Integer> reversedMappings = new HashMap<>();

        for (String mappingKey : this.edgeMappings.keySet()) {
            int mappedTo = this.edgeMappings.get(mappingKey);

            reversedMappings.put(mappingKey.replace("+", "-"), mappedTo + this.edgeMappings.size());
        }

        this.edgeMappings.putAll(reversedMappings);
    }

    public void inverse(File file) throws IOException {
        inverse(new FileReader(file));
    }

    public void inverse(Readable source) throws IOException {
        Scanner scanner = new Scanner(source);

        try {
            while (scanner.hasNextLong()) {
                int src, label, dest;

                src = scanner.nextInt();
                label = scanner.nextInt();
                label = -label;
                dest = scanner.nextInt();
                invertedTuples.add(new int[]{dest, label, src});
                ;
            }
        } catch (NoSuchElementException e) {
            throw new IOException("Expected an long in input file", e);
        }
    }

    Random rand = new Random(423098423L);
public void GenerateGraph(int maxLabels, int nrOfNodes, boolean addMoreRandom)
    {
        tuples.clear();

            for (int i = 0; i < nrOfNodes; i++) {
                int num0 = i + 1;
                int num1 = rand.nextInt(maxLabels) + 1;
                int num2 = rand.nextInt(nrOfNodes) + 1;
                int temp[] = {num0, num1, num2};
                tuples.add(temp);
            }
        if (addMoreRandom)
        {
            for (int i = 0; i < nrOfNodes; i++) {
                int num0 = rand.nextInt(nrOfNodes) + 1;
                int num1 = rand.nextInt(maxLabels) + 1;
                int num2 = rand.nextInt(nrOfNodes) + 1;
                int temp[] = {num0, num1, num2};
                tuples.add(temp);
            }
        }
    }
    public void writePathToFile(String  filename, String inputPath, int times) throws IOException {
       char[] path = inputPath.toCharArray();
        FileWriter fw = new FileWriter(filename,true);

        int k = 0;
        do {
            int charIndex = 0;
            for (int i = k*path.length; i <(k+1)*path.length; i++) {
                int num1 = i + 1;
                int num2 = Character.getNumericValue((path[charIndex]));
                charIndex++;
                int num3 = i + 2;
                long[] longs = {num1, num2, num3};
                pathList.add(longs);

            }
            k++;
        } while (k < times);

        for (long[] longArray: pathList
                ) {
            for (int i =0;i < longArray.length;i++)
            {
                fw.append(String.valueOf(longArray[i]) + " ");
            }
            fw.append(System.lineSeparator());

        }
        fw.close();
    }

    public void writeToFile(String filename) throws IOException {
        FileWriter fw = new FileWriter(filename);
        for (int[] longArray : tuples
                ) {
            for (int i = 0; i < longArray.length; i++) {
                fw.append(String.valueOf(longArray[i]) + " ");
            }
            fw.append(System.lineSeparator());

        }
        for (int[] longArray : invertedTuples
                ) {
            for (int i = 0; i < longArray.length; i++) {
                if (longArray[i] != 0) {
                    fw.append(String.valueOf(longArray[i]) + " ");
                } else {
                    fw.append("-" + String.valueOf(longArray[i]) + " ");
                }
            }
            fw.append(System.lineSeparator());

        }

        fw.close();
    }
     public void Merge() {
        int source,label, target = 0;
        int listCounter = 0;
        List<int[]> newTobeAdded = new ArrayList<>();
        ArrayList<Integer> indexesTobeRemoved = new ArrayList<Integer>();
        for (int[] input:
                tuples ) {
            source = input[0];
            label = input[1];
            target = input[2];

            int counter = 0;
            for (int[] comparison :
                    tuples) {
                if (target == comparison[0] && label == comparison[1] && comparison[0] != comparison[2]) {
                    int[] temp = {source,label,comparison[2]};
                    newTobeAdded.add(temp);
                    indexesTobeRemoved.add(counter);
                    indexesTobeRemoved.add(listCounter);
                }

                counter++;
            }
            listCounter++;
        }

        int cpt = 0;
        Iterator<int[]> it = tuples.iterator();
        while(it.hasNext()){
            it.next();
            if(indexesTobeRemoved.contains(cpt)){
                it.remove();
            }
            cpt++;
        }
        tuples.addAll(newTobeAdded);
    }
    public int getNLabels() {
        return this.edgeMappings.size();
    }

    private void foundTuple(int src, int label, int dest) {
        if (lowestSrc == -1 || src < lowestSrc) {
            lowestSrc = src;
        }
        if (highestSrc == -1 || src > highestSrc) {
            highestSrc = src;
        }
        if (lowestLabel == -1 || label < lowestLabel) {
            lowestLabel = label;
        }
        if (lowestDest == -1 || dest < lowestDest) {
            lowestDest = dest;
        }
        if (highestDest == -1 || dest > highestDest) {
            highestDest = dest;
        }

        if (!edgeMappings.containsKey("+" + label)) {
            edgeMappings.put("+" + label, this.edgeMappings.size());
        }

        tuples.add(new int[]{src, label, dest});
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

    public ArrayList<int[]> getOrderedUnique(Comparator<int[]> comparator) {
        TreeSet<int[]> ordered = new TreeSet<>(comparator);
        ordered.addAll(tuples);

        return new ArrayList<>(ordered);
    }

    public boolean labelFitsInByte() {
        return this.edgeMappings.size() <= Byte.MAX_VALUE;
    }

    public boolean labelFitsInArrayLength() {
        return this.edgeMappings.size() < Integer.MAX_VALUE - 8; // See ArrayList
    }

    public int getNEdges() {
        return tuples.size();
    }

    public Map<String, Integer> getEdgeMappings() {
        return edgeMappings;
    }
}
