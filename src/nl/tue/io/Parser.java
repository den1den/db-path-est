package nl.tue.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Parses a file to a LinkedList of tuples (keeps duplicates)
 * Created by dennis on 17-5-16.
 */
public class Parser implements Iterable<int[]> {
    /**
     * Unprocessed original edge list
     */
    final private TupleList tuples = new TupleList();

    /**
     * Map that contains the edge mapping which maps tuples from the world to the application domain. Edges in the
     * application domain start at 0, are consecutive and go up to n - 1. Where n is the number of unique labels. Back
     * tuples are assigned from n to 2n - 1. Where the back edge for a label l can be found by doing l + n.
     */
    private Map<String, Integer> edgeMappings = new HashMap<>();

    public List<int[]> invertedTuples = new ArrayList<>();

    public void parse(String file) throws IOException {
        parse(new File(file));
    }

    public void parse(File file) throws IOException {
        parse(new TupleList(new FileReader(file)));
    }

    /**
     * Used for reading in a graph to this Parser
     *
     * @param edges
     */
    public void parse(TupleList edges) {
        tuples.addAll(edges);

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
            }
        } catch (NoSuchElementException e) {
            throw new IOException("Expected an long in input file", e);
        }
    }

    public void writePathToFile(String filename, String inputPath, int times) throws IOException {
        LinkedList<long[]> pathList = new LinkedList<>();
        char[] path = inputPath.toCharArray();
        FileWriter fw = new FileWriter(filename, true);

        int k = 0;
        do {
            int charIndex = 0;
            for (int i = k * path.length; i < (k + 1) * path.length; i++) {
                int num1 = i + 1;
                int num2 = Character.getNumericValue((path[charIndex]));
                charIndex++;
                int num3 = i + 2;
                long[] longs = {num1, num2, num3};
                pathList.add(longs);

            }
            k++;
        } while (k < times);

        for (long[] longArray : pathList) {
            for (int i = 0; i < longArray.length; i++) {
                fw.append(String.valueOf(longArray[i]) + " ");
            }
            fw.append(System.lineSeparator());

        }
        fw.close();
    }

    public void writeToFile(String filename) throws IOException {
        FileWriter fw = new FileWriter(filename);
        for (int[] longArray : tuples) {
            for (int i = 0; i < longArray.length; i++) {
                fw.append(String.valueOf(longArray[i]) + " ");
            }
            fw.append(System.lineSeparator());

        }
        for (int[] longArray : invertedTuples) {
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

    public int getNLabels() {
        return this.edgeMappings.size();
    }

    public boolean labelFitsInByte() {
        return this.edgeMappings.size() <= Byte.MAX_VALUE;
    }

    public boolean labelFitsInArrayLength() {
        return this.edgeMappings.size() < Integer.MAX_VALUE - 8; // See ArrayList
    }

    public Map<String, Integer> getEdgeMappings() {
        return edgeMappings;
    }

    public int size() {
        return tuples.size();
    }

    @Override
    public Iterator<int[]> iterator() {
        // No remove or add in the parser
        final Iterator<int[]> delegate = tuples.iterator();
        return new Iterator<int[]>() {
            @Override
            public boolean hasNext() {
                return delegate.hasNext();
            }

            @Override
            public int[] next() {
                return delegate.next();
            }
        };
    }

    public int[] getTuple(int i) {
        return tuples.get(i);
    }
}
