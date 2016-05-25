package nl.tue.algorithm.query;

import java.util.List;

/**
 * Finds the best order of evaluating a query
 */
public interface Optimizer {
    Plan getExecutionOrder(List<Long> query);

    class Plan extends SubResult {
        public Plan(SubResult left, SubResult right) {
            super(null, left, right);
        }
    }

    class SubResult {
        private SubResult parent;
        private SubResult left;
        private SubResult right;

        public SubResult(SubResult parent, SubResult left, SubResult right) {
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public boolean hasParent() {
            return parent != null;
        }

        public boolean leftIsLeaf() {
            return left instanceof Result;
        }

        public boolean rightIsLeaf() {
            return right instanceof Result;
        }

        public SubResult parent() {
            return parent;
        }

        public SubResult left() {
            return left;
        }

        public SubResult right() {
            return right;
        }
    }

    class Result extends SubResult {
        private List<Long> subQuery;

        public Result(SubResult parent, SubResult left, SubResult right, List<Long> subQuery) {
            super(parent, left, right);
            assert left == null;
            assert right == null;
            this.subQuery = subQuery;
        }

        public List<Long> getSubQuery() {
            return subQuery;
        }
    }
}

