package nl.tue.comparison;

import nl.tue.Main;
import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.pathindex.PathIndex;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;
import nl.tue.io.graph.DirectedBackEdgeGraph;

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

    public TestEnvironment(List<String> queries, File file, String name) {
        this.queries = queries;
        this.file = file;
        this.name = name;
    }

    public List<ComparisonResult> execute(Algorithm algo) {
        Parser parser = new Parser();

        try {
            parser.parse(this.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<int[]> intArrQueries = this.queries.stream().map(s -> Main.translateTextQueryToDomainQuery(s, parser)).
                collect(Collectors.toList());

        AdjacencyList graph = new AdjacencyList(parser);

        algo.buildSummary(parser, 5, 800 * 8);

        return executeComparisonsForPaths(intArrQueries, algo, graph);
    }

    public String getName() {
        return name;
    }

    private static List<ComparisonResult>
    executeComparisonsForPaths(List<int[]> paths, Algorithm method,
                               DirectedBackEdgeGraph graph) {
        List<ComparisonResult> res = new ArrayList<>();

        for(int[] path : paths) {
            int estimation = method.query(path);

            int result = graph.solvePathQuery(path).size();

            res.add(new ComparisonResult(new PathIndex(path), result, estimation));
        }

        return res;
    }
}
