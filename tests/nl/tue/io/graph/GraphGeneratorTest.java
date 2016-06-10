package nl.tue.io.graph;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import com.sun.org.apache.xpath.internal.operations.Number;
import nl.tue.io.Parser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by OttkO on 6/2/2016.
 */
public class GraphGeneratorTest {

    Parser p;
    String fileLocation = "D:\\test.txt";

    @Before
    public void before() throws IOException {
        p = new Parser();
    }

    @Test
    public void SaveGeneratedGraph() throws IOException {
        p.GenerateGraph(5, 10, false);
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
        p.WritePathToFile("D:\\test.txt", "4567890", 2);
    }

    @Test
    public void MergeEdges() throws IOException {
        File newFile = new File(fileLocation);
        p.parse(newFile);
        p.Merge();
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
        p.GenerateGraph(5, 200, false);
        p.WritePathToFile(fileLocation, "2104", 860);
        p.WritePathToFile(fileLocation, "2142", 20000);

    }

    @Test
    public void NumberOfNodes() throws IOException {
        p.parse(fileLocation);
        System.out.println(p.CountUniqueNodes());
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
