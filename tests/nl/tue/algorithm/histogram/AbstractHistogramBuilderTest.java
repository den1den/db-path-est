package nl.tue.algorithm.histogram;//import static org.junit.Assert.*;

import junit.framework.TestCase;
import nl.tue.algorithm.Estimator;
import nl.tue.algorithm.paths.PathsOrdering;
import nl.tue.algorithm.paths.PathsOrderingLexicographical;
import org.junit.Test;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by Dennis on 7-6-2016.
 */
public class AbstractHistogramBuilderTest extends TestCase {
    static private final PathsOrdering pathsOrdering = new PathsOrderingLexicographical(15, 5);
    private final Joiner.AbstractJoiner<E> noJoiner = new Joiner.AbstractJoiner<E>() {
        @Override
        public E join(E estimate) {
            this.joinLeft = false;
            this.joinRight = false;
            return estimate;
        }
    };

    AbstractHistogramBuilder<E, H> builder;
    private Estimator<E> graph;

    public AbstractHistogramBuilderTest() {
        builder = new AbstractHistogramBuilder<E, H>(noJoiner) {
            @Override
            protected H createH(ArrayList<Integer> startRanges, E[] estimations) {
                return null;
            }

            @Override
            protected E[] createArray(int length) {
                return new E[length];
            }
        };
        graph = new Estimator<E>() {
            @Override
            public E getEstimation(int[] path) {
                return new E(path);
            }

            @Override
            public long getBytesUsed() {
                return 0;
            }
        };
    }

    @Test
    public void testBuildRanges() throws Exception {
        H builded = builder.build(graph, pathsOrdering);
        System.out.println(builded);
    }

    private static class E {
        int[] p;

        public E(int[] p) {
            this.p = p;
        }
    }

    private class H extends AbstractHistogram<E> {
        public H(int[] startRanges, E[] estimations) {
            super(startRanges, estimations);
        }

        @Override
        public long getBytesUsed() {
            return 0;
        }
    }
}