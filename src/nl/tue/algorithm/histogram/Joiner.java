package nl.tue.algorithm.histogram;

import nl.tue.MemoryConstrained;

/**
 * Decides on when to join two entries of a histogram
 *
 * @param <E> The class used for estimations of a query
 */
public interface Joiner<E, R extends JoinResult<E>> extends MemoryConstrained {

    void calcJoint(R result, int leftTuples, E leftEstimate, E estimate, int rightTuples, E rightEstimate);

    @Override
    default long getBytesUsed() {
        return 16L;
    }

    /**
     * Created by Dennis on 8-6-2016.
     */
    class BasicJoiner implements Joiner<Double, JoinResult.NumberJoinResult> {

        @Override
        public void calcJoint(JoinResult.NumberJoinResult result, int leftTuples, Double leftEstimate, Double estimate, int rightTuples, Double rightEstimate) {
            if (leftEstimate == null && rightEstimate == null) {
                // Nothing to join
                result.joinLeft = false;
                result.joinRight = false;
                result.newEstimate = estimate;
                return;
            }
            Join left = canJoin(estimate, leftEstimate);
            if (left == Join.POSITIVE) {
                result.joinLeft = Math.abs(leftEstimate - estimate) < 10;
            } else if (left == Join.NEGATIVE) {
                result.joinLeft = Math.abs(leftEstimate - estimate) < 2;
            } else {
                result.joinLeft = left == Join.ZERO;
            }
            Join right = canJoin(estimate, rightEstimate);
            if (right == Join.POSITIVE) {
                result.joinRight = Math.abs(rightEstimate - estimate) < 10;
            } else if (right == Join.NEGATIVE) {
                result.joinRight = Math.abs(rightEstimate - estimate) < 2;
            } else {
                result.joinRight = right == Join.ZERO;
            }
            result.setWeightedAverage(leftTuples, leftEstimate, estimate, rightTuples, rightEstimate);
        }

        private Join canJoin(Double centerEstimate, Double edgeEstimate) {
            Join result = Join.NO;
            if (edgeEstimate != null) {
                if (edgeEstimate > 0 && centerEstimate > 0) {
                    result = Join.POSITIVE;
                } else if (edgeEstimate < 0 && centerEstimate < 0) {
                    result = Join.NEGATIVE;
                } else if (edgeEstimate == 0 && centerEstimate == 0) {
                    result = Join.ZERO;
                }
            }
            return result;
        }

        private enum Join {
            NO, NEGATIVE, ZERO, POSITIVE
        }
    }
}
