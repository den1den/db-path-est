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

    public static <E extends Estimation, A extends Estimator<E>> List<ComparisonResult> executeComparisonsForPaths(List<PathIndex> paths,
                                                                           Algorithm<E, A> method,
                                                                    DirectedBackEdgeGraph graph) {
        List<ComparisonResult> res = new ArrayList<>();

        for(PathIndex path : paths) {
            int estimation = method.query(path.getPathAsIntArray());

            int result = graph.solvePathQuery(path.getPathAsIntArray()).size();

            res.add(new ComparisonResult(path, result, estimation));
        }

        return res;
    }
}
