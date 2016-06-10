package nl.tue.io.graph;

import nl.tue.io.Parser;
import nl.tue.io.TupleList;
import nl.tue.io.converters.GraphStructureConverter;
import nl.tue.io.generators.GraphGenerators;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by OttkO on 6/2/2016.
 */
public class GraphGeneratorTest {

    Parser p;
    String fileLocation = "D:\\test.txt";
    private Random r = new Random(53453L);

    @Before
    public void before() throws IOException {
        p = new Parser();
    }
    @Test
    public void SaveGeneratedGraph() throws IOException {
        p.parse(GraphGenerators.generateUniform(r, 5, 10, false));
        p.writeToFile(fileLocation);
    }
    @Test
    public void InvertGeneratedGraph() throws IOException {
       File newFile = new File(fileLocation);
        p.parse(newFile);
        p.inverse(newFile);
        p.writeToFile(fileLocation);
    }
    @Test
        public void WritePathToFile() throws IOException {
        p.writePathToFile("D:\\test.txt","4567890",2);
    }
    @Test
    public void MergeEdges() throws IOException {
        File newFile = new File(fileLocation);
        TupleList tupleList = new TupleList(newFile);
        TupleList merged = new GraphStructureConverter(tupleList).merge();
        p.parse(merged);
        p.writeToFile(fileLocation);
    }


}
