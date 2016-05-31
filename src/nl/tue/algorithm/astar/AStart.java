package nl.tue.algorithm.astar;

import nl.tue.Utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by dennis on 19-5-16.
 */
public class AStart implements Iterable<int[]> {
    private final int LABELS;
    private double heuristic = Double.NaN;

    /**
     * Iterates between 0 and {@code labels - 1}
     *
     * @param labels
     * @param maxDepth
     */
    public AStart(int labels, int maxDepth) {
        this.LABELS = labels;
        assert labels > 0;
    }

    @Override
    public AStartIterator iterator() {
        return new AStartIterator();
    }

    double getHeuristic() {
        double heuristic = this.heuristic;
        this.heuristic = Double.NaN;
        return heuristic;
    }

    /**
     * Set the heuristic value of the previously returned subQuery, the higher the better
     *
     * @param heuristic
     */
    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }

    class AStartIterator implements Iterator<int[]>, Comparator<AStartIterator.Node> {
        final AStartIterator.Node ROOT = new AStartIterator.Node(null, new int[]{}, Double.MAX_VALUE);
        TreeSet<Node> queue;
        int[] interLabelPriorities;
        double[] interLabelHeuristics;
        Node currentParent;
        int[] currentQuery = null;

        AStartIterator() {
            currentParent = ROOT;
            interLabelPriorities = new int[LABELS];
            interLabelHeuristics = new double[LABELS];
            for (int i = 0; i < LABELS; i++) {
                interLabelPriorities[i] = i;
            }
            queue = new TreeSet<>(this);
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public int[] next() {
            if (currentQuery == null) {
                // Do not store the first returned value
                currentQuery = currentParent.next();
                return currentQuery;
            }

            // Add a new child for the previous value
            double prevHeuristic = AStart.this.getHeuristic();
            Node prevNode = new Node(currentParent, currentQuery, prevHeuristic);
            queue.add(prevNode);

            // Set the new parent node
            if (currentParent == ROOT) {
                // First deplete 1st level
                if (currentParent.hasNext()) {
                    currentQuery = currentParent.next();
                    return currentQuery;
                }
                // First level is depleted
                storeInterLabelPriorities();

                currentParent = queue.pollLast();
            } else if (!currentParent.hasNext()) {
                queue.remove(currentParent);
                currentParent = queue.pollLast();
            } else if (prevHeuristic > currentParent.heuristic) {
                // Newly added node has more potential
                currentParent = prevNode;
            }

            currentQuery = currentParent.next();
            return currentQuery;
        }

        private void storeInterLabelPriorities() {
            // Set all the label ordering with respect to the first nodes
            int i = 0;
            for (Iterator<Node> iterator = queue.descendingIterator(); iterator.hasNext(); ) {
                Node n = iterator.next();
                int label = n.subQuery[0];
                double heuristic = n.heuristic;
                interLabelPriorities[i++] = label;
                interLabelHeuristics[label] = heuristic;
            }
            assert Utils.toSet(interLabelPriorities).size() == LABELS;
        }

        /**
         * Compare two nodes in this iterator, based on:
         * internal heuristic, nesting level, interLabelPriorities
         *
         * @param o1 left
         * @param o2 right
         * @return o1 - o2
         */
        @Override
        public int compare(Node o1, Node o2) {
            int cmp = Double.compare(o1.heuristic, o2.heuristic);
            if (cmp != 0) {
                return cmp;
            }
            cmp = -Integer.compare(o1.getLevel(), o2.getLevel());
            if (cmp != 0) {
                return cmp;
            }
            for (int i = 0; i < o1.subQuery.length; i++) {
                int lastLabelA = o1.subQuery[i];
                int valA = interLabelPriorities[lastLabelA];

                int lastLabelB = o2.subQuery[i];
                int valB = interLabelPriorities[lastLabelB];

                cmp = -Integer.compare(valA, valB);
                if (cmp != 0) {
                    return cmp;
                }
            }
            return cmp;
        }

        /**
         * Already traversed query
         */
        class Node implements Iterator<int[]> {
            final Node parent;
            final int[] subQuery;
            final double heuristic;
            private int nxtLabelIndex = 0;

            private Node(Node parent, int[] subQuery, double heuristic) {
                this.parent = parent;
                this.subQuery = subQuery;
                this.heuristic = heuristic;
                if (Double.isNaN(heuristic)) {
                    throw new AssertionError("parentHeuristic not set for: " + this);
                }
            }

            @Override
            public boolean hasNext() {
                return nxtLabelIndex < interLabelPriorities.length;
            }

            @Override
            public int[] next() {
                int nextLabel = interLabelPriorities[nxtLabelIndex++];
                int[] newQuery = new int[subQuery.length + 1];
                System.arraycopy(subQuery, 0, newQuery, 0, subQuery.length);
                newQuery[subQuery.length] = nextLabel;
                return newQuery;
            }

            int getLevel() {
                return subQuery.length;
            }

            @Override
            public String toString() {
                return Arrays.toString(subQuery);
            }

        }
    }
}
