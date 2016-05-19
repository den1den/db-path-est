package nl.tue;

import nl.tue.algorithm.Algorithm;
import nl.tue.io.Parser;
import nl.tue.io.datatypes.DGHashMap;
import nl.tue.io.datatypes.OrderedEdgeArray;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        String file = args[0];
        long maximalPathLength = Long.parseLong(args[1]);
        long budget = Long.parseLong(args[2]);

        // Create OG

        Algorithm algorithm = null;

        // Write actual bytes used to System.out

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

            OrderedEdgeArray oea0 = new OrderedEdgeArray.Integers(p);
            if(p.labelFitsInByte()){
                OrderedEdgeArray oea1 = new OrderedEdgeArray.LabelBytes(p);
            }
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
            p.parse(file);
            return p;
        }).iterator();
        return () -> it;
    }

    public static class Tester {
        public static void main(String[] args) throws IOException {

        }
    }
}
