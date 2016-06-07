package nl.tue.algorithm.histogram;

/**
 * Created by Dennis on 4-6-2016.
 */
public abstract class Joiner<Es> {
    protected boolean joinLeft, joinRight;

    /**
     * Sets joinLeft, joinRight
     * @param leftTuples
     * @param leftEstimate
     * @param newEstimate
     * @param rightTuples
     * @param rightEstimate
     * @return
     */
    public abstract Es calcJoin(int leftTuples, Es leftEstimate, Es newEstimate, int rightTuples, Es rightEstimate);

    public boolean isJoinLeft() {
        return joinLeft;
    }

    public boolean isJoinRight() {
        return joinRight;
    }
}
