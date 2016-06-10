package nl.tue.io.graph;

import nl.tue.io.Parser;
import nl.tue.io.TupleList;
import nl.tue.io.TupleList.Meta;
import nl.tue.io.converters.GraphStructureConverter;
import nl.tue.io.generators.GraphGenerators;
import org.junit.Assert;
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
    private Random r;

    @Before
    public void before() throws IOException {
        p = new Parser();
        r = new Random(53453L);
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

    @Test
    public void MergeZwei() throws IOException {
        File newFile = new File(fileLocation);
        p.parse(newFile);
        p.MergeZwei(1000);
        p.WriteHashmapToFile(fileLocation);
    }

    @Test
    public void Combined() throws IOException {
        TupleList g = GraphGenerators.generateUniform(r, 5, 200, false);
        p.parse(g);
        p.writePathToFile(fileLocation, "2104", 860);
        p.writePathToFile(fileLocation, "2142", 20000);

    }

    @Test
    public void NumberOfNodes() throws IOException {
        TupleList g = new TupleList(new File(fileLocation));
        System.out.println(g.new Meta());
    }

    @Test
    public void testPathProps() throws IOException {
        p.parse(fileLocation);

        AdjacencyList graph = new AdjacencyList(p);

        Assert.assertTrue(graph.getNodes().size() <= 1000);
        System.out.println(graph.solvePathQuery(new int[]{3}).size());
        System.out.println(graph.solvePathQuery(new int[]{2, 1, 0, 4}).size());
        System.out.println(graph.solvePathQuery(new int[]{2, 1, 4, 2}).size());
    }

}
