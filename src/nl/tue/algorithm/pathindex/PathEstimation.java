package nl.tue.algorithm.pathindex;

/**
 * Created by Nathan on 5/24/2016.
 */
public class PathEstimation {

    private final PathIndex index;
    private final Estimation estimation;

    public PathEstimation(PathIndex index, Estimation estimation) {
        this.index = index;
        this.estimation = estimation;
    }

    public PathIndex getIndex() {
        return index;
    }

    public Estimation getEstimation() {
        return estimation;
    }
}
