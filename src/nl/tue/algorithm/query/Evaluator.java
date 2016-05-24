package nl.tue.algorithm.query;

import java.util.List;

/**
 * Created by dennis on 24-5-16.
 */
public interface Evaluator {
    /**
     * Estimates the number of expected unique start and end tuples of a query.
     *
     * @param query the path query, with all the indices of each label
     * @return expected number of tuples in end result
     */
    int query(List<Long> query);
}
