package nl.tue.algorithm.query;

import java.util.List;

/**
 * Finds the best order of evaluating a query
 */
public interface Optimizer {
    List<List<Long>> getExecutionOrder(List<Long> query);
}
