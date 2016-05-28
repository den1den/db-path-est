package nl.tue.comparison;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.Estimation;
import nl.tue.algorithm.Estimator;
import nl.tue.algorithm.pathindex.PathIndex;
import nl.tue.io.graph.DirectedBackEdgeGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nathan on 5/25/2016.
 */
public class ComparisonExecutor {

    public static <E extends Estimation, A extends Estimator<E>> List<ComparisonResult>
        executeComparisonsForPaths(List<int[]> paths,
                                                                           Algorithm<E, A> method,
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
