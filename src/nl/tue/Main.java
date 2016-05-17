package nl.tue;

import nl.tue.io.Parser;
import nl.tue.io.datatypes.DGHashMap;
import nl.tue.io.datatypes.OrderedEdgeArray;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
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
}
