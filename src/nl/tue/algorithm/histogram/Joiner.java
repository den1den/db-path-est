package nl.tue.algorithm.histogram;

import nl.tue.MemoryConstrained;

/**
 * Decides on when to join two entries of a histogram
 * @param <E> The class used for estimations of a query
 */
public interface Joiner<E, R extends JoinResult<E>> extends MemoryConstrained{

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
            if(leftEstimate != null){
                result.joinLeft = Math.abs(leftEstimate - estimate) < 10;
            }
            if(rightEstimate != null){
                result.joinRight = Math.abs(rightEstimate - estimate) < 10;
            }

            result.setWeightedAverage(leftTuples, leftEstimate, estimate, rightTuples, rightEstimate);
        }
    }
}
