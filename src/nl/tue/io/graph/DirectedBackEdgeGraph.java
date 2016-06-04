package nl.tue.io.graph;

import java.util.Collection;
import java.util.Set;

/**
 * Class that represents the parsed input file, is used by the algorithm to
 *
 * Created by Nathan on 5/21/2016.
 */
public interface DirectedBackEdgeGraph {

    Collection<NodePair> solvePathQuery(int[] path);
}
