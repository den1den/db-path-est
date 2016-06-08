package nl.tue.algorithm.histogram;

/**
 * Created by Dennis on 8-6-2016.
 */
public interface Joiner<E> {
    void setRightEstimate(E rightEstimate);

    void setLeftTuples(int leftTuples);

    void setLeftEstimate(E leftEstimate);

    void setRightTuples(int rightTuples);

    boolean isJoinLeft();

    boolean isJoinRight();

    E join(E estimate);

    /**
     * Created by Dennis on 8-6-2016.
     */
    abstract class AbstractJoiner<E> implements Joiner<E> {
        protected int leftTuples;
        protected E leftEstimate;
        protected int rightTuples;
        protected E rightEstimate;

        protected boolean joinLeft;
        protected boolean joinRight;


        @Override
        public void setRightEstimate(E rightEstimate) {
            this.rightEstimate = rightEstimate;
        }


        @Override
        public void setLeftTuples(int leftTuples) {
            this.leftTuples = leftTuples;
        }


        @Override
        public void setLeftEstimate(E leftEstimate) {
            this.leftEstimate = leftEstimate;
        }


        @Override
        public void setRightTuples(int rightTuples) {
            this.rightTuples = rightTuples;
        }

        @Override
        public boolean isJoinLeft() {
            return joinLeft;
        }


        @Override
        public boolean isJoinRight() {
            return joinRight;
        }

    }

    /**
     * Created by Dennis on 8-6-2016.
     */
    abstract class SingleNumberJoiner extends AbstractJoiner<Double> {
        protected double calcWeightedAverage(double estimate) {
            double result;
            if(joinLeft && joinRight){
                result = (leftEstimate + estimate + rightEstimate) / (leftTuples + 1 + rightTuples);
            } else if (joinLeft){
                result = (leftEstimate + estimate) / (leftTuples + 1);
            } else if (joinRight){
                result = (rightEstimate + estimate) / (rightTuples + 1);
            } else {
                result = estimate;
            }
            return result;
        }
    }

    class Basic extends SingleNumberJoiner {
        @Override
        public Double join(Double estimate) {
            joinLeft = false;
            joinRight = false;
            if(leftEstimate != null){
                joinLeft = Math.abs(leftEstimate - estimate) < 10;
            }
            if(rightEstimate != null){
                joinRight = Math.abs(rightEstimate - estimate) < 10;
            }
            return calcWeightedAverage(estimate);
        }
    }
}
