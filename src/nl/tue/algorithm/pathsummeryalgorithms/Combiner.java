package nl.tue.algorithm.pathsummeryalgorithms;

/**
 * Created by dennis on 2-6-16.
 */
public interface Combiner<E extends Estimate, P extends Summery<E>> {
    E combine(E left, E right);
}
