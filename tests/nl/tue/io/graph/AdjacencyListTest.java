package nl.tue.io.graph;

import nl.tue.io.Parser;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Nathan on 5/19/2016.
 */
public class AdjacencyListTest {

    @Rule
    public TemporaryFolder folder  = new TemporaryFolder();

    File file;

    @Before
    public void before() throws IOException {
        file = folder.newFile();
    }


    @Test
    public void testOneLabelTwoNodesAllNodesRead() throws IOException {

        PrintWriter out = new PrintWriter(file);

        out.println("1 1 2");
        out.flush();

        out.close();

        Parser p = new Parser();

        p.parse(file);

        AdjacencyList list = new AdjacencyList(p);



        Assert.assertTrue(list.getNodes().containsKey(1));
        Assert.assertTrue(list.getNodes().containsKey(2));
    }

    @Test
    public void testOneLabelTwoNodesEdgeIsRead() throws  IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 1 2");
        out.flush();
        out.close();

        Parser p = new Parser();

        p.parse(file);

        AdjacencyList list = new AdjacencyList(p);

        Assert.assertTrue(list.getNodes().get(1).get(1).contains(2));
        Assert.assertTrue(list.getNodes().get(1).get(1).size() == 1);
    }

    @Test
    public void testTwoOutgoingOneLabel() throws IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 1 2");
        out.println("1 1 3");
        out.flush();
        out.close();

        Parser p = new Parser();

        p.parse(file);

        AdjacencyList list = new AdjacencyList(p);

        Assert.assertTrue(list.getNodes().get(1).get(1).contains(2));
        Assert.assertTrue(list.getNodes().get(1).get(1).contains(3));
        Assert.assertTrue(list.getNodes().get(1).get(1).size() == 2);
    }

    @Test
    public void testBackEdgeIntroduced() throws IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 1 2");
        out.flush();
        out.close();

        Parser p = new Parser();

        p.parse(file);

        AdjacencyList list = new AdjacencyList(p);

        Assert.assertTrue(list.getNodes().get(1).get(1).contains(2));
        Assert.assertTrue(list.getNodes().get(1).get(1).size() == 1);
        Assert.assertTrue(list.getNodes().get(2).get(-1).size() == 1);
        Assert.assertTrue(list.getNodes().get(2).get(-1).contains(1));
    }
}
