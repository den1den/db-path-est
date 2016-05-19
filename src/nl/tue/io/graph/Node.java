package nl.tue.io.graph;

import java.util.Map;
import java.util.Set;

/**
 * NOT USED ATM BUT WHO DOESN'T LIKE SOME DEAD CODE.
 *
 * Created by Nathan on 5/19/2016.
 */
public class Node {
    private final int id;

    private final Map<Integer, Set<Node>> edges;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return id == node.id;

    }

    public int getId() {
        return id;
    }

    public Map<Integer, Set<Node>> getEdges() {
        return edges;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public Node(int id, Map<Integer, Set<Node>> edges) {

        this.id = id;
        this.edges = edges;
    }
}
