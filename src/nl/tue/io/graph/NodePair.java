package nl.tue.io.graph;

/**
 * Represent a pair of two nodes as the result of a path getEstimation.
 *
 * Created by Nathan on 5/20/2016.
 */
public class NodePair {
    private final int left, right;

    public NodePair(int left, int right) {
        this.left = left;
        this.right = right;
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
        int result = left;
        result = 31 * result + right;
        return result;
    }
}
