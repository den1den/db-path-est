package nl.tue.comparison;

import nl.tue.Main;
import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.pathindex.PathIndex;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;
import nl.tue.io.graph.DirectedBackEdgeGraph;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nathan on 5/29/2016.
 */
public class TestEnvironment {
    private final List<String> queries;
    private final File file;
    private final String name;

    private final int parts;
    private final int partsUsed;

    private int nodes;
    private int labels;
    private int summaryTime;
    private long memUsage;

    /**
     * True when it's to big for a brute force
     */
    private boolean big;

    public TestEnvironment(List<String> queries, File file, String name, boolean big) {
        this(queries, file, name, big, 1, 1);
    }

    public TestEnvironment(List<String> queries, File file, String name, boolean big, int parts, int partUsed) {
        this.queries = queries;
        this.file = file;
        this.name = name;
        this.big = big;
        this.parts = parts;
        this.partsUsed = partUsed;
    }

    private static List<ComparisonResult>
    executeComparisonsForPaths(List<int[]> paths, Algorithm method,
                               DirectedBackEdgeGraph graph) {
        List<ComparisonResult> res = new ArrayList<>();

        for(int[] path : paths) {
            long startTime = System.currentTimeMillis();

            long estimation = method.query(path);

            int estimationTime = (int) (System.currentTimeMillis() - startTime);

            int result = graph.solvePathQuery(path).size();

            res.add(new ComparisonResult(new PathIndex(path), result, estimation, estimationTime));
        }

        return res;
    }

    public String getName() {
        return name;
    }

    public int getNodes() {
        return nodes;
    }

    public int getLabels() {
        return labels;
    }

    public int getSummaryTime() {
        return summaryTime;
    }

    public long getMemUsage() {
        return memUsage;
    }

    public long fileLength() {
        return this.file.length();
    }

    public List<ComparisonResult> execute(Algorithm algo) {
        Parser parser = new Parser();

        try {
            parser.parse(this.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Map String query to Int query
        List<int[]> intArrQueries = this.queries.stream().map(s -> Main.translateTextQueryToDomainQuery(s, parser)).
                collect(Collectors.toList());

        AdjacencyList graph = new AdjacencyList(parser);

        this.nodes = graph.getNodes().size();
        this.labels = parser.getNLabels()/2;

        long startSummary = System.currentTimeMillis();

        algo.buildSummary(parser, 5, graph.getNodes().keySet().size() * 8);

        this.summaryTime = (int) (System.currentTimeMillis() - startSummary);

        this.memUsage = algo.getBytesUsed();

        return executeComparisonsForPaths(intArrQueries, algo, graph);
    }

    public boolean isBig() {
        return big;
    }

    /**
     * Fails when test enviroment is to large
     */
    public void checkBig() {
        if(isBig()){
            Assert.fail("Trying to execute a to large file");
        }
    }
}
