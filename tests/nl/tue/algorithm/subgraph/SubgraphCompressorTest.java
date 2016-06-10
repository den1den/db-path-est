package nl.tue.algorithm.subgraph;

import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Nathan on 6/5/2016.
 */
public class SubgraphCompressorTest {

    @Test
    public void testIntToAndFromThreeBytes_1() {
        int testVal = 34;

        Assert.assertEquals(testVal, SubgraphCompressor.threeBytesToInt(SubgraphCompressor.intToThreeBytes(testVal)));
    }

    @Test
    public void testIntToAndFromThreeBytes_2() {
        int testVal = 2450;

        Assert.assertEquals(testVal, SubgraphCompressor.threeBytesToInt(SubgraphCompressor.intToThreeBytes(testVal)));
    }

    @Test
    public void testIntToAndFromThreeBytes_3() {
        int testVal = 120678;

        Assert.assertEquals(testVal, SubgraphCompressor.threeBytesToInt(SubgraphCompressor.intToThreeBytes(testVal)));
    }

    @Test
    public void testEdgeToAndFromByteArray_1() {
        int[] edge = new int[]{573, 5, 23};

        Assert.assertArrayEquals(edge, SubgraphCompressor.decompressEdge(SubgraphCompressor.compressEdge(edge)));
    }

    @Test
    public void testEdgeToAndFromByteArray_2() {
        int[] edge = new int[]{23489, 9, 1328};

        Assert.assertArrayEquals(edge, SubgraphCompressor.decompressEdge(SubgraphCompressor.compressEdge(edge)));
    }

    @Test
    public void testCompressDecompressEdges() throws IOException {
        Parser parser = new Parser();

        parser.parse(AdjacencyList.class.getClassLoader().getResource("generatedmusicdata.txt").getFile());

        System.out.println(new SimpleDateFormat("mm-ss").format(Calendar.getInstance().getTime()) + " Started");

        byte[] byteArr = new byte[parser.size() * 7];

        for (int i = 0; i < parser.size(); i++) {
            byte[] edge = SubgraphCompressor.compressEdge(parser.getTuple(i));

            System.arraycopy(edge, 0, byteArr, i * 7, edge.length);
        }

        System.out.println(new SimpleDateFormat("mm-ss").format(Calendar.getInstance().getTime()) + " Converted edges to byte array");

        byte[] compressed = SubgraphCompressor.compress(byteArr);

        System.out.println(new SimpleDateFormat("mm-ss").format(Calendar.getInstance().getTime()) + " Compressed");

        byte[] decompressed = SubgraphCompressor.decompress(compressed);

        System.out.println(new SimpleDateFormat("mm-ss").format(Calendar.getInstance().getTime()) + " Decompressed");

        Assert.assertArrayEquals(byteArr, decompressed);

        List<int[]> newEdges = new ArrayList<>();

        for (int i = 0; i < decompressed.length; i += 7) {
            byte[] edge = new byte[7];
            System.arraycopy(decompressed, i, edge, 0, 7);
            newEdges.add(SubgraphCompressor.decompressEdge(edge));
        }

        Assert.assertEquals(parser.size(), newEdges.size());

        for (int i = 0; i < parser.size(); i++) {
            Assert.assertArrayEquals(parser.getTuple(i), newEdges.get(i));
        }

    }

    @Test
    public void testCompressWithLimit() throws IOException {
        final Parser parser = new Parser();

        parser.parse(AdjacencyList.class.getClassLoader().getResource("generatedmusicdata.txt").getFile());

        AdjacencyList graph = new AdjacencyList(parser);

        byte[] compressed = SubgraphCompressor.compressWithLimit(parser, graph.getNodes().keySet().size() * 8);

        Assert.assertTrue(compressed.length <= graph.getNodes().keySet().size() * 8);
    }

    @Test
    public void testCompressDecompressDoesntChange() throws IOException {
        Parser parser = new Parser();

        parser.parse(AdjacencyList.class.getClassLoader().getResource("generatedmusicdata.txt").getFile());

        AdjacencyList graph = new AdjacencyList(parser);

        byte[] compressed = SubgraphCompressor.compressWithLimit(parser, graph.getNodes().size() * 8);

        byte[] decompressed = SubgraphCompressor.decompress(compressed);

        byte[] preCompress = new byte[parser.size() * 7];

        int added = 0;

        for (int[] tuple : parser) {
            byte[] comprEdge = SubgraphCompressor.compressEdge(tuple);

            System.arraycopy(comprEdge, 0, preCompress, added * 7, comprEdge.length);

            added++;
        }

        Assert.assertArrayEquals(preCompress, decompressed);
    }

    @Test
    public void testFullCompressDecompressCycle() throws IOException {
        Parser parser = new Parser();

        parser.parse(AdjacencyList.class.getClassLoader().getResource("generatedmusicdata.txt").getFile());

        AdjacencyList graph = new AdjacencyList(parser);

        byte[] compressed = SubgraphCompressor.compressWithLimit(graphToEdgeList(graph), graph.getNodes().size() * 8);

        Parser decompressedParser = new Parser();

        decompressedParser.parse(SubgraphCompressor.decompressSubgraph(compressed));

        AdjacencyList decompressedGraph = new AdjacencyList(decompressedParser, false, parser.getNLabels());

        for (int node : graph.getNodes().keySet()) {
            for (int label : graph.getNodes().get(node).keySet()) {
                Assert.assertTrue(decompressedGraph.getNodes().containsKey(node));
                Assert.assertTrue(decompressedGraph.getNodes().get(node).containsKey(label));

                Assert.assertEquals(graph.getNodes().size(), decompressedGraph.getNodes().size());
                Assert.assertEquals(graph.getNodes().get(node).size(), decompressedGraph.getNodes().get(node).size());

                Assert.assertTrue(graph.getNodes().get(node).get(label).equals(decompressedGraph.getNodes().get(node).get(label)));
            }
        }
    }

    @Test
    public void testDoubleCompression_1() {
        List<Double> vals = new ArrayList<>();
        vals.add(3.4);

        Assert.assertEquals(vals, SubgraphCompressor.byteArrayToDoubles(SubgraphCompressor.doublesToByteArray(vals)));
    }

    @Test
    public void testDoubleCompression_2() {
        List<Double> vals = new ArrayList<>();
        vals.add(3.4);
        vals.add(32d);
        vals.add(32.352);
        vals.add(-56.2);

        Assert.assertEquals(vals, SubgraphCompressor.byteArrayToDoubles(SubgraphCompressor.doublesToByteArray(vals)));
    }

    private List<int[]> graphToEdgeList(AdjacencyList graph) {
        List<int[]> out = new ArrayList<>();

        outerloop:
        for (int node : graph.getNodes().keySet()) {
            Map<Integer, Set<Integer>> integerSetMap = graph.getNodes().get(node);
            for (int label : integerSetMap.keySet()) {
                if (label < integerSetMap.keySet().size() / 2) {
                    for (int rightNode : integerSetMap.get(label)) {
                        out.add(new int[]{node, label, rightNode});
                    }
                }

            }
        }

        return out;
    }
}