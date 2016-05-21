package nl.tue.io.graph;

import nl.tue.io.Parser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

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

    @Test
    public void testSimplePathQuery() throws IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 1 2");
        out.flush();
        out.close();

        Parser p = new Parser();

        p.parse(file);

        AdjacencyList list = new AdjacencyList(p);

        Set<NodePair> res = list.solvePathQuery(new int[] {1});

        Assert.assertTrue(res.size() == 1);
        Assert.assertTrue(res.contains(new NodePair(1, 2)));
    }

    @Test
    public void testQueryOfLengthThree() throws  IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 1 2");
        out.println("2 1 4");
        out.println("4 2 3");
        out.flush();
        out.close();

        Parser p = new Parser();

        p.parse(file);

        AdjacencyList list = new AdjacencyList(p);

        Set<NodePair> res = list.solvePathQuery(new int[] {1, 1, 2});

        Assert.assertTrue(res.size() == 1);
        Assert.assertTrue(res.contains(new NodePair(1, 3)));
    }

    @Test
    public void testQueryWithOneStartAndTwoEndNodes() throws IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 1 2");
        out.println("1 1 4");
        out.flush();
        out.close();

        Parser p = new Parser();

        p.parse(file);

        AdjacencyList list = new AdjacencyList(p);

        Set<NodePair> res = list.solvePathQuery(new int[]{1});

        Assert.assertTrue(res.size() == 2);

        Assert.assertTrue(res.contains(new NodePair(1, 2)));
        Assert.assertTrue(res.contains(new NodePair(1, 4)));
    }

    @Test
    public void testLongExponentialPathQuery() throws IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 1 2");
        out.println("2 2 8");
        out.println("8 1 5");
        out.println("8 1 4");
        out.println("8 1 3");
        out.flush();
        out.close();

        Parser p = new Parser();

        p.parse(file);

        AdjacencyList list = new AdjacencyList(p);

        Set<NodePair> res = list.solvePathQuery(new int[]{1, 2, 1});

        Assert.assertTrue(res.size() == 3);

        Assert.assertTrue(res.contains(new NodePair(1, 5)));
        Assert.assertTrue(res.contains(new NodePair(1, 4)));
        Assert.assertTrue(res.contains(new NodePair(1, 3)));
    }

    @Test
    public void testCycle() throws IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 1 2");
        out.println("2 1 3");
        out.println("3 1 1");
        out.flush();
        out.close();

        Parser p = new Parser();

        p.parse(file);

        AdjacencyList list = new AdjacencyList(p);

        Set<NodePair> res = list.solvePathQuery(new int[]{1, 1});

        Assert.assertTrue(res.size() == 3);

        Assert.assertTrue(res.contains(new NodePair(1, 3)));
        Assert.assertTrue(res.contains(new NodePair(2, 1)));
        Assert.assertTrue(res.contains(new NodePair(3, 2)));
    }

    @Test
    public void testEmptyResultSet() throws IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 1 2");
        out.println("2 2 3");
        out.println("3 1 4");
        out.flush();
        out.close();

        Parser p = new Parser();

        p.parse(file);

        AdjacencyList list = new AdjacencyList(p);

        Set<NodePair> res = list.solvePathQuery(new int[]{2, 1, 1});

        Assert.assertTrue(res.size() == 0);
    }

    @Test
    public void doBackEdgeQuery() throws IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 1 2");
        out.println("3 1 2");
        out.flush();
        out.close();

        Parser p = new Parser();

        p.parse(file);

        AdjacencyList list = new AdjacencyList(p);

        Set<NodePair> res = list.solvePathQuery(new int[]{1, -1});

        Assert.assertTrue(res.size() == 4);

        Assert.assertTrue(res.contains(new NodePair(3, 3)));
        Assert.assertTrue(res.contains(new NodePair(1, 1)));
        Assert.assertTrue(res.contains(new NodePair(1, 3)));
        Assert.assertTrue(res.contains(new NodePair(3, 1)));
    }
}
