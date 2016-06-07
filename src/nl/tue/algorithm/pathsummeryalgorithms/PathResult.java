package nl.tue.algorithm.pathsummeryalgorithms;

import nl.tue.algorithm.pathsummeryalgorithms.pathresult.PathResultEstimate;

/**
 * Created by dennis on 2-6-16.
 */
class PathResult implements Summery<PathResultEstimate> {
    final static int BYTES = Integer.BYTES;

    final int tuples;

    PathResult(int tuples) {
        this.tuples = tuples;
    }

    @Override
    public PathResultEstimate makeEstimate() {
        throw new UnsupportedOperationException();
    }

    static class SimpleCombiner {
        long n_squared;

        public SimpleCombiner(int NODES) {
            n_squared = (long) NODES * NODES;
        }

        //@Override
        public PathResult combine(PathResult left, PathResult right) {
            double pLeft = (double) left.tuples / n_squared;
            double pRight = (double) right.tuples / n_squared;
            double p = pLeft * pRight;
            throw new UnsupportedOperationException();
            //return new PathResult();
        }
    }

    static class PRE {
        double percentage;

        public PRE(double percentage) {
            this.percentage = percentage;
        }
    }
}
