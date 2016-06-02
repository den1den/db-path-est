package nl.tue.io.graph;

import nl.tue.algorithm.pathindex.PathIndex;
import nl.tue.io.Parser;

import javax.xml.soap.Node;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Nathan on 5/19/2016.
 */
public class AdjacencyList implements DirectedBackEdgeGraph {

    private final static int ZERO_LENGTH_STORE = 300;

    private final Map<Integer, Map<Integer, Set<Integer>>> nodes;

    private final Map<Integer, Set<Integer>> outgoingIndex;

    /**
     * This should be easily queryable in the trace path method.
     */
    private final Map<PathIndex, Map<Integer, Set<Integer>>> shortPathIndex;

    private final ZeroLengthPathStore zeroStore;

    public AdjacencyList(Parser parser) {

        nodes = new HashMap<>();
        this.zeroStore = new ZeroLengthPathStore(ZERO_LENGTH_STORE);
        this.outgoingIndex = new HashMap<>();
        this.shortPathIndex = new HashMap<>();

        for (String rawLabel : parser.getEdgeMappings().keySet()) {
            outgoingIndex.put(parser.getEdgeMappings().get(rawLabel), new HashSet<>());
        }

        for (long[] tuple : parser.tuples) {
            if (tuple.length != 3) {
                System.err.println("While reading results from parser an unexpected tuple has been encountered");
                continue;
            }

            if (!nodes.containsKey((int) tuple[0])) {
                nodes.put((int) tuple[0], emptyEdges(0, parser.getNLabels() - 1));
            }
            if (!nodes.containsKey((int) tuple[2])) {
                nodes.put((int) tuple[2], emptyEdges(0, parser.getNLabels() - 1));
            }

            Set<Integer> edgesForLabel = nodes.get((int) tuple[0]).get(parser.getEdgeMappings().get("+" + tuple[1]));
            Set<Integer> backEdgesForLabel = nodes.get((int) tuple[2]).get(parser.getEdgeMappings().get("-" + tuple[1]));

            this.outgoingIndex.get(parser.getEdgeMappings().get("+" + tuple[1])).add((int) tuple[0]);
            this.outgoingIndex.get(parser.getEdgeMappings().get("-" + tuple[1])).add((int) tuple[2]);

            edgesForLabel.add((int) tuple[2]);
            backEdgesForLabel.add((int) tuple[0]);
        }
    }

    /**
     * Adds empty hashsets for both all the edges in between lowestlabel and highestlabel and addesd empty hashsets for
     * all the empty hashsets between 0 - lowestlabel and 0 - highesetlabel. Operates under the assumption that all
     * lables in between and including those two numbers are utilized.
     *
     * @param lowestLabel
     * @param highestLabel
     * @return
     */
    private Map<Integer, Set<Integer>> emptyEdges(long lowestLabel, long highestLabel) {
        Map<Integer, Set<Integer>> edges = new HashMap<>();
        for (long i = lowestLabel; i <= highestLabel; i++) {
            edges.put((int) i, new HashSet<>());
        }

        return edges;
    }

    public Set<NodePair> solvePathQuery(int[] path) {
        if (path.length <= 0) {
            throw new IllegalArgumentException(String.format("A path length of %d does not make any sense", path.length));
        }

        Set<NodePair> out = new TreeSet<>();

        if (this.zeroStore.isZeroPath(new PathIndex(path))) {
            return out;
        }

        for (int nodeStart : this.outgoingIndex.get(path[0])) {
            List<Integer> ends = tracePath(nodeStart, path);

            for(int nodeEnd : ends) {
                out.add(new NodePair(nodeStart, nodeEnd));
            }
        }

        if (out.size() == 0) {
            this.zeroStore.attemptAdd(new PathIndex(path));
        } else if (path.length <= 2) {

            Map<Integer, Set<Integer>> indexedPath = new HashMap<>();

            for (NodePair pair : out) {
                if (indexedPath.containsKey(pair.getLeft())) {
                    indexedPath.get(pair.getLeft()).add(pair.getRight());
                } else {
                    Set<Integer> rightNodes = new HashSet<>();
                    rightNodes.add(pair.getRight());
                    indexedPath.put(pair.getLeft(), rightNodes);
                }
            }
            this.shortPathIndex.put(new PathIndex(path), indexedPath);
        }

        return out;
    }

    private List<Integer> tracePath(int node, int[] path) {
        if (path.length <= 0) {
            throw new IllegalArgumentException(String.format("A path length of %d does not make any sense", path.length));
        }
        List<Integer> out = new ArrayList<>();

        int start = path[0];

        if (path.length >= 2 && this.shortPathIndex.containsKey(new PathIndex(new int[]{path[0], path[1]}))) {
            Set<Integer> nodesAfterTwoLabels = this.shortPathIndex.get(new PathIndex(new int[]{path[0], path[1]})).get(node);

            if(path.length == 2) {
                out = nodesAfterTwoLabels == null ? new ArrayList<>() : new ArrayList<>(nodesAfterTwoLabels);

                return out;
            } else if (nodesAfterTwoLabels != null) {
                for (int nodeAfterTwo : nodesAfterTwoLabels) {
                    out.addAll(tracePath(nodeAfterTwo, Arrays.copyOfRange(path, 2, path.length)));
                }

                return out;
            } else {
                return out;
            }
        } else if (path.length > 1) {
            Set<Integer> outgoingOverStart = nodes.get(node).get(start);

            for (int nodeAfterStart : outgoingOverStart) {
                out.addAll(tracePath(nodeAfterStart, Arrays.copyOfRange(path, 1, path.length)));
            }

            return out;
        } else {
            out = new ArrayList<>(nodes.get(node).get(start));

            return out;
        }
    }

    public Map<Integer, Map<Integer, Set<Integer>>> getNodes() {
        return nodes;
    }

    private static class ZeroLengthPathStore {
        private final int size;
        private final int longestLengthStored;

        private final Set<PathIndex> index;

        public ZeroLengthPathStore(int size) {
            this.size = size;
            this.longestLengthStored = 0;
            this.index = new LinkedHashSet<>(this.size);
        }

        public void attemptAdd(PathIndex path) {
            if (this.index.size() >= this.size && path.getLength() >= longestLengthStored) {
                return;
            } else if (this.index.size() >= this.size) {
                PathIndex longer = null;

                for (PathIndex storedPath : this.index) {
                    if (storedPath.getLength() > path.getLength()) {
                        longer = storedPath;
                    }
                }

                this.index.remove(longer);
                this.index.add(path);
            } else {
                this.index.add(path);
            }
        }

        public boolean isZeroPath(PathIndex toTest) {
            int[] toTestArr = toTest.getPathAsIntArray();

            for (PathIndex index : this.index) {
                if (Collections.indexOfSubList(Arrays.asList(toTestArr), Arrays.asList(index.getPathAsIntArray())) != -1) {
                    return true;
                }
            }

            return false;
        }
    }
}
