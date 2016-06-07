package nl.tue.io.graph;

import nl.tue.algorithm.Estimator;
import nl.tue.algorithm.PathResult;

import java.util.Set;

/**
 * Class that represents the parsed input file, is used by the algorithm to
 *
 * Created by Nathan on 5/21/2016.
 */
public interface DirectedBackEdgeGraph extends Estimator<PathResult>{

    Set<NodePair> solvePathQuery(int[] path);
}
