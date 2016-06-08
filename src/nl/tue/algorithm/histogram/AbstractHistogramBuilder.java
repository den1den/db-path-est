package nl.tue.algorithm.histogram;

import nl.tue.Utils;
import nl.tue.algorithm.Estimator;
import nl.tue.algorithm.paths.PathsOrdering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * @param <E> Intermediate guessing class
 * @param <H> Actual output
 */
public abstract class AbstractHistogramBuilder<E, H> {

    private Joiner.AbstractJoiner<E> joiner;
    private int maxIndex = Integer.MAX_VALUE;

    public AbstractHistogramBuilder(Joiner.AbstractJoiner<E> joiner) {
        this.joiner = joiner;
        ranges.add(new HistogramRange<>(null, 0, maxIndex));
    }

    private TreeSet<HistogramRange<E>> ranges = new TreeSet<>();
    private HistogramRange<E> lower = null, higher = null;

    private HistogramRange<E> prepareInsert(int newIndex){
        HistogramRange<E> newRange = new HistogramRange<>(null, newIndex, newIndex);
        HistogramRange<E> floor = ranges.floor(newRange);

        assert floor != null;
        assert floor.estimation == null; // Assume not already occupied
        assert floor.has(newIndex);

        if(newIndex == floor.startIndex){
            // Move floor to right
            ranges.remove(floor);
            floor.startIndex++;
            if(floor.startIndex <= floor.endIndex){
                ranges.add(floor);
                higher = floor;
            } else {
                higher = null;
            }

            lower = ranges.lower(floor);
        } else {
            assert floor.size() >= 2;
            if (newIndex == floor.endIndex) {
                // Move floor to left
                floor.endIndex--;
                assert floor.startIndex <= floor.endIndex;
                higher = ranges.higher(floor);

                lower = floor;
            } else {
                // In between, so split up
                assert floor.size() >= 3;
                assert floor.estimation == null;

                higher = new HistogramRange<>(null, newIndex + 1, floor.endIndex);
                ranges.add(higher);

                floor.endIndex = newIndex - 1;
                lower = floor;
            }
        }

        return newRange;
    }

    public void addEstimation(E estimation, int index){
        // Create new range tuple
        HistogramRange<E> newR = prepareInsert(index);

        if (lower != null) {
            joiner.setLeftTuples(lower.size());
            joiner.setLeftEstimate(lower.estimation);
        } else {
            joiner.setLeftTuples(0);
            joiner.setLeftEstimate(null);
        }

        if (higher != null) {
            joiner.setRightTuples(higher.size());
            joiner.setRightEstimate(higher.estimation);
        } else {
            joiner.setRightTuples(0);
            joiner.setRightEstimate(null);
        }

        E newEstimation = joiner.join(estimation);
        if(joiner.isJoinLeft() && joiner.isJoinRight()){
            if(higher != null) {
                ranges.remove(higher);
                lower.endIndex = higher.endIndex;
            } else {
                lower.endIndex = maxIndex;
            }
            lower.estimation = newEstimation;
        } else if (joiner.isJoinLeft()){
            lower.endIndex = index;
            lower.estimation = newEstimation;
        } else if (joiner.isJoinRight()){
            if(higher != null){
                ranges.remove(higher);
                higher.startIndex = index;
                higher.estimation = newEstimation;
            } else {
                // not possible?
                higher = new HistogramRange<>(estimation, index, maxIndex);
            }
            ranges.add(higher);
        } else {
            newR.estimation = newEstimation;
            ranges.add(newR);
        }
    }

    /**
     * Example of usage of this class
     * @param graph
     * @param serializer
     * @return
     */
    public H build(Estimator<E> graph, PathsOrdering serializer){
        Iterator<int[]> pathsIterator = serializer.iterator();
        while (pathsIterator.hasNext()){
            int[] next = pathsIterator.next();
            int nextIndex = serializer.get(next);
            E estimation = graph.getEstimation(next);
            addEstimation(estimation, nextIndex);
        }
        return build();
    }

    public H build() {
        check(ranges);

        ArrayList<Integer> startRanges = new ArrayList<>(ranges.size() - 1);
        E[] estimations = createArray(ranges.size());

        int ix = 0;
        Iterator<HistogramRange<E>> i = ranges.iterator();
        HistogramRange<E> a = i.next();
        estimations[ix] = a.estimation;

        ix++;
        while (i.hasNext()) {
            a = i.next();
            estimations[ix] = a.estimation;
            startRanges.add(a.startIndex);

            ix++;
        }

        return createH(startRanges, estimations);
    }

    private void check(TreeSet<HistogramRange<E>> ranges) {
        Iterator<HistogramRange<E>> i = ranges.iterator();
        assert i.hasNext();

        HistogramRange<E> a = i.next();
        assert a.startIndex == 0;
        boolean lastWasEmty = a.estimation == null;

        while (i.hasNext()) {
            HistogramRange<E> b = i.next();
            assert a.endIndex + 1 == b.startIndex;
            if (lastWasEmty) {
                assert b.estimation != null;
            }
            lastWasEmty = b.estimation == null;

            a = b;
        }
    }

    protected abstract H createH(ArrayList<Integer> startRanges, E[] estimations);

    protected abstract E[] createArray(int length);

    public static class Short extends AbstractHistogramBuilder<Double, AbstractHistogram.Short>{
        public Short(Joiner.SingleNumberJoiner joiner) {
            super(joiner);
        }

        @Override
        protected AbstractHistogram.Short createH(ArrayList<Integer> startRanges, Double[] estimations) {
            short[] estimations2 = new short[estimations.length];
            for (int i = 0; i < estimations.length; i++) {
                double est = estimations[i];
                if(est < java.lang.Short.MIN_VALUE || est > java.lang.Short.MAX_VALUE){
                    throw new ArithmeticException();
                }
                estimations2[i] = (short) est;
            }
            return new AbstractHistogram.Short(Utils.toArray(startRanges), estimations2);
        }

        @Override
        protected Double[] createArray(int length) {
            return new Double[length];
        }
    }
}