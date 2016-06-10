package nl.tue.io;

import java.io.*;
import java.util.Collection;

/**
 * Created by Dennis on 10-6-2016.
 */
public class CSVWriter {
    static void write(String filename, Iterable<int[]> edgeList){
        try {
            write(new BufferedWriter(new FileWriter(filename)), edgeList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void write(BufferedWriter writer, Iterable<int[]> edgeList) throws IOException {
        writer.write("Source,Label,Target");
        writer.newLine();
        for (int[] i : edgeList){
            writer.append(String.format("%d,%d,%d", i[0], i[1], i[2]));
            writer.newLine();
        }
        writer.close();
    }
}
