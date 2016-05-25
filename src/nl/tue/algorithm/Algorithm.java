package nl.tue.algorithm;

import java.util.List;

/**
 * This class stores all the data that is available in memory.
 * It should also be able to perform an query
 */
public interface Algorithm extends MemoryConstrained {

    /**
     * Estimates the number of expected unique start and end tuples of a query.
     *
     * @param query the path query, with all the indices of each label
     * @return expected number of tuples in end result
     */
    int query(List<Long> query);

}
