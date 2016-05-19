package nl.tue.algorithm;

import java.util.*;

/**
 * Created by dennis on 19-5-16.
 */
public class AStart implements Iterable<int[]> {

    private int[] labels;
    private int maxDepth;
    private HeuristicEvaluator heuristicEvaluator;

    public AStart(int[] labels, int maxDepth, HeuristicEvaluator heuristicEvaluator) {
        this.labels = labels;
        this.maxDepth = maxDepth;
        this.heuristicEvaluator = heuristicEvaluator;
    }

    @Override
    public AStartIterator iterator() {
        return new AStartIterator();
    }

    public static class HeuristicEvaluator {
        private double heuristic = Double.NaN;

        public void setHeuristic(double heuristic) {
            this.heuristic = heuristic;
        }
    }

    public class AStartIterator implements Iterator<int[]> {

        AStartIterator() {
            ROOT = new Node(null, new int[]{}, Double.MAX_VALUE);
            lastParent = ROOT;
            ordering = new ArrayList<>(labels.length);
            for (int i = 0; i < labels.length; i++) {
                ordering.add(labels[i]);
            }
        }

        List<Integer> ordering;
        final Node ROOT;

        TreeSet<Node> nexts = new TreeSet<>(cmpByHeuristic);

        int maxLevel = 0;

        Node lastParent;
        int[] lastQuery = null;

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public int[] next() {
            // First fill first level
            if (maxLevel == 0) {
                if (lastQuery == null) {
                    // First node
                    lastQuery = lastParent.next();
                    return lastQuery;
                }

                nexts.add(new Node(lastParent, lastQuery, heuristicEvaluator.heuristic));
                heuristicEvaluator.heuristic = Double.NaN;

                if (lastParent.hasNext()) {
                    lastQuery = lastParent.next();
                    return lastQuery;
                }

                // First level complete
                ordering.clear();
                for (Node ordered : nexts) {
                    ordering.add(ordered.subQuery[0]);
                }

                maxLevel++;
            } else {
                nexts.add(new Node(lastParent, lastQuery, heuristicEvaluator.heuristic));
                heuristicEvaluator.heuristic = Double.NaN;
            }

            Node next = nexts.first();

            while (!next.hasNext()) {
                nexts.pollFirst();
                next = nexts.first();
            }

            lastParent = next.parent;
            lastQuery = next.next();

            return lastQuery;
        }

        public int getLevel() {
            return maxLevel;
        }

        public int getMaxLevel() {
            return maxLevel;
        }

        public void setMaxLevel(int maxLevel) {
            this.maxLevel = maxLevel;
        }

        private class Node implements Iterator<int[]> {
            final Node parent;
            final int[] subQuery;
            final double heuristic;
            int nxtLabelIndex = 0;

            public Node(Node parent, int[] subQuery, double heuristic) {
                if (Double.isNaN(heuristic)) {
                    throw new AssertionError("parentHeuristic not set for query: " + Arrays.toString(subQuery));
                }
                this.parent = parent;
                this.subQuery = subQuery;
                this.heuristic = heuristic;
            }

            @Override
            public boolean hasNext() {
                return nxtLabelIndex < ordering.size();
            }

            @Override
            public int[] next() {
                int nextLabel = ordering.get(nxtLabelIndex++);
                int[] newQuery = new int[subQuery.length + 1];
                System.arraycopy(subQuery, 0, newQuery, 0, subQuery.length);
                newQuery[subQuery.length] = nextLabel;
                return newQuery;
            }
        }
    }

    private static Comparator<AStartIterator.Node> cmpByHeuristic = new Comparator<AStartIterator.Node>() {
        @Override
        public int compare(AStartIterator.Node o1, AStartIterator.Node o2) {
            return Double.compare(o1.heuristic, o2.heuristic);
        }
    };
}
