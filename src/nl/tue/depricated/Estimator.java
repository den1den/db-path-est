package nl.tue.depricated;

import nl.tue.io.Parser;

import java.util.Collection;
import java.util.List;

/**
 * Created by Dennis on 5-6-2016.
 */
@Deprecated
public class Estimator<E> {
    public <E extends Estimation> E concatEstimations(E headEstimation, E tailEstimation) {
        return null;
    }

    public <E extends Estimation> Collection<E> retrieveAllExactEstimations() {
        return null;
    }

    public void buildSummary(Parser p, int k, double b) {

    }

    public int combineEstimations(List<E> sortedEs){
        return 0;
    }
    public long getBytesUsed() {
        return 0;
    }
}
