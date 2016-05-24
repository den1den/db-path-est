package nl.tue.algorithm.query;

import java.util.List;

/**
 * Created by dennis on 24-5-16.
 */
public interface Estimator {
    double precision(List<Long> preQuery, List<Long> postQuery);
}
