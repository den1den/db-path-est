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

        Assert.assertTrue(list.getNodes().get(1).get(p.getEdgeMappings().get("+1")).contains(2));
        Assert.assertTrue(list.getNodes().get(1).get(p.getEdgeMappings().get("+1")).size() == 1);
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

        Assert.assertTrue(list.getNodes().get(1).get(p.getEdgeMappings().get("+1")).contains(2));
        Assert.assertTrue(list.getNodes().get(1).get(p.getEdgeMappings().get("+1")).contains(3));
        Assert.assertTrue(list.getNodes().get(1).get(p.getEdgeMappings().get("+1")).size() == 2);
    }
    @Test
    public void testThreeOutgoingOneLabel() throws IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 1 2");
        out.println("1 1 3");
        out.println("1 1 4");
        out.flush();
        out.close();

        Parser p = new Parser();

        p.parse(file);

        AdjacencyList list = new AdjacencyList(p);

        Assert.assertTrue(list.getNodes().get(1).get(p.getEdgeMappings().get("+1")).contains(2));
        Assert.assertTrue(list.getNodes().get(1).get(p.getEdgeMappings().get("+1")).contains(3));
        Assert.assertTrue(list.getNodes().get(1).get(p.getEdgeMappings().get("+1")).contains(4));
        Assert.assertTrue(list.getNodes().get(1).get(p.getEdgeMappings().get("+1")).size() == 3);
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

        Assert.assertTrue(list.getNodes().get(1).get(p.getEdgeMappings().get("+1")).contains(2));
        Assert.assertTrue(list.getNodes().get(1).get(p.getEdgeMappings().get("+1")).size() == 1);
        Assert.assertTrue(list.getNodes().get(2).get(p.getEdgeMappings().get("-1")).size() == 1);
        Assert.assertTrue(list.getNodes().get(2).get(p.getEdgeMappings().get("-1")).contains(1));
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

        Set<NodePair> res = list.solvePathQuery(new int[] {p.getEdgeMappings().get("+1")});

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

        Set<NodePair> res = list.solvePathQuery(new int[] {p.getEdgeMappings().get("+1"),
                p.getEdgeMappings().get("+1"), p.getEdgeMappings().get("+2")});

        Assert.assertTrue(res.size() == 1);
        Assert.assertTrue(res.contains(new NodePair(1, 3)));
    }
    @Test
    public void testQueryDistinctPathLengthTwo() throws  IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 3 2");
        out.println("1 3 8");
        out.println("2 2 4");
        out.println("4 2 5");
        out.println("4 2 3");
        out.println("8 2 3");
        out.println("8 3 4");
        out.flush();
        out.close();

        Parser p = new Parser();

        p.parse(file);

        AdjacencyList list = new AdjacencyList(p);

        Set<NodePair> res = list.solvePathQuery(new int[] {p.getEdgeMappings().get("+3"),
                p.getEdgeMappings().get("+2"), p.getEdgeMappings().get("+2")});

        Assert.assertTrue(res.size() == 2);
        Assert.assertTrue(res.contains(new NodePair(1,3)));
        Assert.assertTrue(res.contains(new NodePair(1,5)));


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

        Set<NodePair> res = list.solvePathQuery(new int[]{p.getEdgeMappings().get("+1")});

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

        Set<NodePair> res = list.solvePathQuery(new int[]{p.getEdgeMappings().get("+1"),
                p.getEdgeMappings().get("+2"), p.getEdgeMappings().get("+1")});

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

        Set<NodePair> res = list.solvePathQuery(new int[]{p.getEdgeMappings().get("+1"), p.getEdgeMappings().get("+1")});

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

        Set<NodePair> res = list.solvePathQuery(new int[]{p.getEdgeMappings().get("+2"),
                p.getEdgeMappings().get("+1"), p.getEdgeMappings().get("+1")});

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

        Set<NodePair> res = list.solvePathQuery(new int[]{p.getEdgeMappings().get("+1"), p.getEdgeMappings().get("-1")});

        Assert.assertTrue(res.size() == 4);

        Assert.assertTrue(res.contains(new NodePair(3, 3)));
        Assert.assertTrue(res.contains(new NodePair(1, 1)));
        Assert.assertTrue(res.contains(new NodePair(1, 3)));
        Assert.assertTrue(res.contains(new NodePair(3, 1)));
    }

    @Test
    public void testQueryLengthFiveWithCycleQuery() throws IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 1 2");
        out.println("2 1 3");
        out.println("3 1 4");
        out.println("4 1 5");
        out.println("5 1 1");
        out.flush();
        out.close();

        Parser p = new Parser();

        p.parse(file);

        AdjacencyList list = new AdjacencyList(p);

        Set<NodePair> res = list.solvePathQuery(new int[]{p.getEdgeMappings().get("+1"), p.getEdgeMappings().get("+1"),
                p.getEdgeMappings().get("+1"), p.getEdgeMappings().get("+1"), p.getEdgeMappings().get("+1")});

        Assert.assertTrue(res.size() == 5);
    }

    @Test
    public void doRandomGraphLengthTwoWithCycleQuery() throws IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 2 2");
        out.println("1 1 3");
        out.println("2 1 2");
        out.println("3 1 4");
        out.println("4 2 3");
        out.flush();
        out.close();

        Parser p = new Parser();

        p.parse(file);

        AdjacencyList list = new AdjacencyList(p);

        Set<NodePair> res = list.solvePathQuery(new int[]{p.getEdgeMappings().get("+1"), p.getEdgeMappings().get("+1")});

        Assert.assertTrue(res.size() == 2);

        Assert.assertTrue(res.contains(new NodePair(2, 2)));
        Assert.assertTrue(res.contains(new NodePair(1, 4)));
    }

    @Test
    public void testBackQueryLengthThreeWithCycle() throws IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 1 2");
        out.println("2 1 3");
        out.println("3 1 1");
        out.flush();
        out.close();

        Parser p = new Parser();

        p.parse(file);

        AdjacencyList list = new AdjacencyList(p);

        Set<NodePair> res = list.solvePathQuery(new int[]{p.getEdgeMappings().get("-1"), p.getEdgeMappings().get("-1"),
                p.getEdgeMappings().get("-1")});

        Assert.assertTrue(res.size() == 3);
    }
}
