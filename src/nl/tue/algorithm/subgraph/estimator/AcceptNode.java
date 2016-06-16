package nl.tue.algorithm.subgraph.estimator;

/**
 * Created by Nathan on 6/9/2016.
 */
public interface AcceptNode {
    boolean acceptNode(int node);

    static AcceptNode everySecondOrThird() {
        return node -> node % 2 == 0 || node % 3 == 0;
    }
}
