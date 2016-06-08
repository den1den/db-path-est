package nl.tue.algorithm;

import nl.tue.MemoryConstrained;
import nl.tue.io.graph.DirectedBackEdgeGraph;

import java.util.function.ToIntFunction;

/**
 * Created by Dennis on 4-6-2016.
 */
public interface Estimator<E> extends MemoryConstrained {
    E getEstimation(int[] path);

    static Estimator<Double> map(Estimator<PathResult> graph) {
        return new Estimator<Double>() {
            @Override
            public Double getEstimation(int[] path) {
                return (double) graph.getEstimation(path).tuples;
            }

            @Override
            public long getBytesUsed() {
                return graph.getBytesUsed();
            }
        };
    }
}
