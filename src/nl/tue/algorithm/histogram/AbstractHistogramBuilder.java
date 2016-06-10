package nl.tue.algorithm.histogram;

import nl.tue.Utils;
import nl.tue.algorithm.Estimator;
import nl.tue.algorithm.paths.PathsOrdering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @param <E> Intermediate guessing class
 * @param <H> Histogram output
 * @param <JR> JoinResult class
 * @param <J> Joiner class
 */
public abstract class AbstractHistogramBuilder<E, H, JR extends JoinResult<E>, J extends Joiner<E, JR>> {

    protected J joiner;
    protected JR result;
    int maxIndex = Integer.MAX_VALUE;
    TreeSet<HistogramRange<E>> ranges = new TreeSet<>();
    HistogramRange<E> lower = null, higher = null;
    public AbstractHistogramBuilder(J joiner) {
        this.joiner = joiner;
        ranges.add(new HistogramRange<>(null, 0, maxIndex));
    }

    HistogramRange<E> prepareInsert(int newIndex){
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

        int leftTuples;
        E leftEstimate;
        if (lower != null) {
            leftTuples = lower.size();
            leftEstimate = lower.estimation;
        } else {
            leftTuples = 0;
            leftEstimate = null;
        }

        int rightTuples;
        E rightEstimate;
        if (higher != null) {
            rightTuples = higher.size();
            rightEstimate = higher.estimation;
        } else {
            rightTuples = 0;
            rightEstimate = null;
        }

        joiner.calcJoint(result, leftTuples, leftEstimate, estimation, rightTuples, rightEstimate);
        E newEstimation = result.newEstimate;
        if(result.isJoinLeft() && result.isJoinRight()){
            if(higher != null) {
                ranges.remove(higher);
                lower.endIndex = higher.endIndex;
            } else {
                lower.endIndex = maxIndex;
            }
            lower.estimation = newEstimation;
        } else if (result.isJoinLeft()){
            lower.endIndex = index;
            lower.estimation = newEstimation;
        } else if (result.isJoinRight()){
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
        return toHistogram();
    }

    public H toHistogram() {
        check(ranges);
        ArrayList<HistogramRange<E>> estimatedRanges = new ArrayList<>(ranges.size() / 2);
        estimatedRanges.addAll(ranges.stream().filter(r -> r.estimation != null).collect(Collectors.toList()));
        return createH(estimatedRanges);
    }

    private void check(TreeSet<HistogramRange<E>> ranges) {
        Iterator<HistogramRange<E>> i = ranges.iterator();
        assert i.hasNext();

        HistogramRange<E> a = i.next();
        assert a.startIndex == 0;
        boolean lastWasEmty = a.estimation == null;

        while (i.hasNext()) {
            HistogramRange<E> b = i.next();
            assert a.endIndex + 1 == b.startIndex; // assert consecutive ranges
            if (lastWasEmty) {
                assert b.estimation != null; // assert no two consecutive empty ranges
            }
            lastWasEmty = b.estimation == null;

            a = b;
        }
    }

    protected abstract H createH(ArrayList<HistogramRange<E>> estimatedRanges);

    protected abstract E[] createArray(int length);

    public abstract long estMemUsage();

    /**
     * Histogram builder
     * You add Double estimations with `addEstimation` to this histogram builder
     * It then builds `toHistogram` a Histogram of shorts
     * @see AbstractHistogramBuilder#build(Estimator, PathsOrdering)
     */
    public static class Short extends AbstractHistogramBuilder<Double, Histogram, JoinResult.NumberJoinResult, Joiner<Double, JoinResult.NumberJoinResult>>{

        public Short(Joiner<Double, JoinResult.NumberJoinResult> joiner) {
            super(joiner);
            this.result = new JoinResult.NumberJoinResult();
        }

        /**
         * Goes from Double ranges to short Histogram
         * @param estimatedRanges
         * @return
         */
        @Override
        protected Histogram createH(ArrayList<HistogramRange<java.lang.Double>> estimatedRanges) {
            ArrayList<Integer> startRanges = new ArrayList<>(estimatedRanges.size());
            ArrayList<java.lang.Short> estimations = new ArrayList<>(estimatedRanges.size());
            ArrayList<java.lang.Short> estimationLengths = new ArrayList<>(estimatedRanges.size());

            int index = 0;
            HistogramRange<Double> range;
            int a, b;
            while (index <= estimatedRanges.size() - 1) {
                range = estimatedRanges.get(index);
                a = range.startIndex;
                b = range.endIndex;
                int diff = b - a;
                do {
                    short actRange = (short) Math.min(diff, java.lang.Short.MAX_VALUE);
                    startRanges.add(a);
                    estimations.add(compressEstimation(range.estimation));
                    estimationLengths.add(actRange);
                    a += actRange;

                    diff = b - a;
                } while (diff > 0);
                index++;
            }
            return new Histogram(Utils.toArray(startRanges), Utils.toArrayS(estimations), Utils.toArrayS(estimationLengths));
        }

        private java.lang.Short compressEstimation(Double estimation) {
            return estimation.shortValue();
        }

        @Override
        protected Double[] createArray(int length) {
            return new Double[length];
        }

        @Override
        public long estMemUsage() {
            return this.ranges.size() * (Integer.BYTES + java.lang.Short.BYTES * 2);
        }
    }
}