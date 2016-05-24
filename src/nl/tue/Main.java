package nl.tue;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.Algorithm_1;
import nl.tue.io.Parser;
import nl.tue.io.datatypes.DGHashMap;
import nl.tue.io.datatypes.OrderedEdgeArray;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        String file = args[0];
        long maximalPathLength = Long.parseLong(args[1]);
        long budget = Long.parseLong(args[2]);

        Parser p = new Parser();
        p.parse(file);

        // Create OG
        Algorithm algorithm = new Algorithm_1();

        // Write actual bytes used to System.out
        long bytes = algorithm.getBytesUsed();
        System.out.println(bytes);

        Scanner s = new Scanner(System.in);
        while (s.hasNextLine()) {
            String nextLine = s.nextLine();
            Scanner numberScanner = new Scanner(nextLine);
            LinkedList<Long> input = new LinkedList<>();
            while (numberScanner.hasNextLong()) {
                input.add(s.nextLong());
            }
            //Execute
            long result = algorithm.query(input);
            System.out.println(result);
        }
    }

    public static void test(String[] args) throws IOException {
        //Try to read all files from first argument
        String baseFolder = args[0];
        for (Parser p : readAll(baseFolder)){
            //TODO: Test creation of graphs in testsuite
            DGHashMap dg0 = new DGHashMap.LabelArray(p);
            DGHashMap dg1 = new DGHashMap.DoubleIndexedHashMap(p);

            OrderedEdgeArray oea0 = OrderedEdgeArray.construct(p);
        }
    }

    /**
     * Read and parse all files from some folder
     * @param folder
     * @return
     */
    private static Iterable<Parser> readAll(String folder){
        File baseFolder = new File(folder);
        if(!baseFolder.isDirectory()){
            throw new RuntimeException("Expected first argument to be a directory, instead found: "+baseFolder.toString());
        }
        File[] files = baseFolder.listFiles((File dir, String name)->name.endsWith(".txt"));
        Iterator<Parser> it = Arrays.stream(files).map(file -> {
            Parser p = new Parser();
            try {
                p.parse(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return p;
        }).iterator();
        return () -> it;
    }
}
