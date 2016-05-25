package nl.tue.algorithm.query;

/**
 * Finds the best order of evaluating a getEstimation
 */
public class QueryTree {

    private QueryTree left;
    private QueryTree right;

    public QueryTree(QueryTree left, QueryTree right) {
        this.left = left;
        this.right = right;
    }

    public boolean leftIsLeaf() {
        return left instanceof Leaf;
    }

    public boolean rightIsLeaf() {
        return right instanceof Leaf;
    }

    public QueryTree left() {
        return left;
    }

    public QueryTree right() {
        return right;
    }

    public static class Leaf extends QueryTree {
        private int[] subQuery;

        Leaf(QueryTree left, QueryTree right, int[] subQuery) {
            super(left, right);
            assert left == null;
            assert right == null;
            assert subQuery != null;
            this.subQuery = subQuery;
        }

        public Leaf(int[] subQuery) {
            this(null, null, subQuery);
        }

        public int[] getSubQuery() {
            return subQuery;
        }
    }
}

