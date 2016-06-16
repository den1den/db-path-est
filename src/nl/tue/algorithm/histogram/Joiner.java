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
            result.joinLeft = false;
            result.joinRight = false;
            boolean leftCandiate;
            leftCandiate = isCandiate(leftEstimate, estimate);
            if (leftCandiate) {
                result.joinLeft = Math.abs(leftEstimate - estimate) < 10;
            }
            boolean rightCandiate;
            rightCandiate = isCandiate(rightEstimate, estimate);
            if (rightCandiate) {
                result.joinRight = Math.abs(rightEstimate - estimate) < 10;
            }
            if (rightEstimate != null && leftEstimate != null) {
                if (rightEstimate == 0 && leftEstimate == 0) {
                    boolean join = estimate == 0;
                    result.joinLeft = join;
                    result.joinRight = join;
                    result.newEstimate = estimate;
                }
            }
            result.setWeightedAverage(leftTuples, leftEstimate, estimate, rightTuples, rightEstimate);
        }

        private boolean isCandiate(Double canEstimate, Double middleEstimate) {
            boolean candidate = false;
            if (canEstimate != null) {
                if (canEstimate > 0 && middleEstimate > 0) {
                    candidate = true;
                } else if (canEstimate < 0 && middleEstimate < 0) {
                    candidate = true;
                }
            }
            return candidate;
        }
    }
}
