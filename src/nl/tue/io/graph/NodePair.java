package nl.tue.io.graph;

/**
 * Represent a pair of two nodes as the result of a path getEstimation.
 *
 * Created by Nathan on 5/20/2016.
 */
public class NodePair implements Comparable{
    private final int left, right;
    private final int hash;

    public NodePair(int left, int right) {
        this.left = left;
        this.right = right;
        this.hash =  (left * 0x1f1f1f1f) ^ right;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodePair nodePair = (NodePair) o;

        if (left != nodePair.left) return false;
        return right == nodePair.right;

    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof NodePair) {
            NodePair other = (NodePair) o;
            int compareLeft = left - other.left;

            if(compareLeft == 0) {
                return right - other.right;
            } else {
                return compareLeft;
            }
        }

        throw new RuntimeException();
    }
}
