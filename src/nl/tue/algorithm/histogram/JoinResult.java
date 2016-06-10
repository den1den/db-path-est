package nl.tue.algorithm.histogram;

/**
 * Created by Dennis on 10-6-2016.
 */
public class JoinResult<E> {
    boolean joinLeft, joinRight;
    E newEstimate;

    public boolean isJoinLeft() {
        return joinLeft;
    }

    public boolean isJoinRight() {
        return joinRight;
    }

    public E getNewEstimate() {
        return newEstimate;
    }

    public static class NumberJoinResult extends JoinResult<Double>{
        public void setWeightedAverage(int leftTuples, Double leftEstimate, Double estimate, int rightTuples, Double rightEstimate) {
            this.newEstimate = 0d;
            if(this.joinLeft && this.joinRight){
                this.newEstimate = (leftEstimate + estimate + rightEstimate) / (leftTuples + 1 + rightTuples);
            } else if (this.joinLeft){
                this.newEstimate = (leftEstimate + estimate) / (leftTuples + 1);
            } else if (this.joinRight){
                this.newEstimate = (rightEstimate + estimate) / (rightTuples + 1);
            } else {
                this.newEstimate = estimate;
            }
        }
    }
}
