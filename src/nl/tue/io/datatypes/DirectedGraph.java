package nl.tue.io.datatypes;

import java.util.Set;

/**
 * Created by dennis on 19-5-16.
 */
public interface DirectedGraph {
    /**
     * Get all destination nodes of src given label
     *
     * @param label
     * @return Set of all t with: s -> t
     */
    Set<Long> getAll(long label);

    /**
     * Get all destination nodes of src given label, from src starting set
     *
     * @param label
     * @param sources all s with: s -> t
     * @return Set of all t with: s -> t
     */
    Set<Long> get(long label, Set<Long> sources);
}
