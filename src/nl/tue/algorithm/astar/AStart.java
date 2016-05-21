package nl.tue.algorithm.astar;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by dennis on 19-5-16.
 */
public class AStart implements Iterable<int[]> {
    private final int labels;

    public AStart(int labels, int maxDepth) {
        this.labels = labels;
        assert labels > 0;
        //TODO: do something with the maxDepth
    }

    @Override
    public AStartIterator iterator() {
        return new AStartIterator();
    }

    private double heuristic = Double.NaN;

    /**
     * Set the heuristic value of the previously returned subQuery, the higer the better
     *
     * @param heuristic
     */
    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }

    private double getHeuristic() {
        double heuristic = this.heuristic;
        this.heuristic = Double.NaN;
        return heuristic;
    }

    public class AStartIterator implements Iterator<int[]> {
        TreeSet<Node> nexts;
        int[] labelOrder;
        double[] labelHeuristic;

        private AStartIterator() {
            currentParent = ROOT;
            nexts = new TreeSet<>(cmpByHeuristic);
            labelOrder = new int[labels];
            labelHeuristic = new double[labels];
            for (int l = 0; l < labels; l++) {
                labelOrder[l] = l;
            }
        }

        private final AStartIterator.Node ROOT = new AStartIterator.Node(null, new int[]{}, Double.MAX_VALUE);
        Node currentParent;
        int[] currentQuery = null;

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

            // Add a new child for previous value
            Node newNode = new Node(currentParent, currentQuery, AStart.this.getHeuristic());
            nexts.add(newNode);

            // Set the new parent node
            if (currentParent == ROOT) {
                // Parent node does not change in the first level
                if (currentParent.hasNext()) {
                    currentQuery = currentParent.next();
                    return currentQuery;
                }
                // First level is depleted
                // Set all the label ordering with respect to the first nodes
                int i = 0;
                for (Iterator<Node> iterator = nexts.descendingIterator(); iterator.hasNext(); ) {
                    Node n = iterator.next();
                    int label = n.subQuery[0];
                    double heuristic = n.heuristic;
                    labelOrder[i++] = label;
                    labelHeuristic[label] = heuristic;
                }
                // Also set the heuristic mapping
                currentParent = nexts.pollLast();
            } else if (!currentParent.hasNext()) {
                nexts.remove(currentParent);
                currentParent = nexts.pollLast();
            } else if (newNode.heuristic > currentParent.heuristic) {
                // Newly added node has more potential
                currentParent = newNode;
            }

            currentQuery = currentParent.next();
            return currentQuery;
        }

        private double getHeuristic(int labelIndex) {
            return labelHeuristic[labelIndex];
        }

        class Node implements Iterator<int[]> {
            final Node parent;
            final int[] subQuery;
            final double heuristic;
            private int nxtLabelIndex = 0;

            private Node(Node parent, int[] subQuery, double heuristic) {
                if (Double.isNaN(heuristic)) {
                    throw new AssertionError("parentHeuristic not set for query: " + Arrays.toString(subQuery));
                }
                this.parent = parent;
                this.subQuery = subQuery;
                this.heuristic = heuristic;
            }

            @Override
            public boolean hasNext() {
                return nxtLabelIndex < labelOrder.length;
            }

            @Override
            public int[] next() {
                int nextLabel = labelOrder[nxtLabelIndex++];
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

        Comparator<AStartIterator.Node> cmpByHeuristic = (o1, o2) -> {
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
                int valA = labelOrder[lastLabelA];

                int lastLabelB = o2.subQuery[i];
                int valB = labelOrder[lastLabelB];

                cmp = -Integer.compare(valA, valB);
                if (cmp != 0) {
                    return cmp;
                }
            }
            return cmp;
        };
    }


}
